package javapro;

import java.util.ArrayList;

public class SeamCarver {

    private Picture pic; // the picture
    private int width, height; // height and weight of picture
    private double[][] energy; // energy map of the picture

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        validate(picture); // check if picture != null
        pic = new Picture(picture); // copy the picture
        width = pic.width();
        height = pic.height();
        energy = new double[width][height];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                energy[x][y] = calcEnergy(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[width]; // seam length is same as the width of pic
        double distance = Double.POSITIVE_INFINITY;
        double[][] distTo = new double[width][height];
        Point[][] edgeTo = new Point[width][height]; //from each point i know the latest point


        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                if (col == 0) distTo[col][row] = 0;
                else distTo[col][row] = Double.POSITIVE_INFINITY;



        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Point p = new Point(col, row); // initialize the point.

                for (Point adj : getAdjHoriz(p)) {
                    if (distTo[adj.x][adj.y] > distTo[p.x][p.y] + energy[p.x][p.y]) {
                        distTo[adj.x][adj.y] = distTo[p.x][p.y] + energy[p.x][p.y]; // the first row of point is distto array is 0 ( 0 + energy)
                        edgeTo[adj.x][adj.y] = p;
                        if (adj.x == width - 1 && distTo[adj.x][adj.y] < distance) { // i pick the best seam from the distance
                            distance = distTo[adj.x][adj.y];
                            for (int count = width; adj != null; adj = edgeTo[adj.x][adj.y])
                                seam[--count] = adj.y;
                        }
                    }
                }

            }
        }
        return seam;
    }

    private class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<Point> getAdjHoriz(Point p) {
        ArrayList<Point> adj = new ArrayList<>(3);
        if (p.x < width - 1) { // don't take last one in the row
            if (p.y > 0) adj.add(new Point(p.x + 1, p.y - 1)); // don't take the first column
            if (p.y < height - 1) adj.add(new Point(p.x + 1, p.y + 1)); // don't take the last column
            adj.add(new Point(p.x + 1, p.y));
        }
        return adj;
    }

    private ArrayList<Point> getAdjVert(Point p) {
        ArrayList<Point> adj = new ArrayList<>(3);
        if (p.y < height - 1) {
            if (p.x > 0) adj.add(new Point(p.x - 1, p.y + 1));
            if (p.x < width - 1) adj.add(new Point(p.x + 1, p.y + 1));
            adj.add(new Point(p.x, p.y + 1));
        }
        return adj;
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validate(x, y);
        return energy[x][y];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateHorizSeam(seam);
        Picture newPic = new Picture(width, --height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (row < seam[col]) {
                    newPic.set(col, row, pic.get(col, row));
                    energy[col][row] = energy[col][row];
                }
                else {
                    newPic.set(col, row, pic.get(col, row + 1));
                    energy[col][row] = energy[col][row + 1];
                }
            }
        }

        pic = newPic;

        for (int col = 0; col < width; col++) {
            if (seam[col] > 0) energy[col][seam[col] - 1] = calcEnergy(col, seam[col] - 1);
            if (seam[col] < height - 1) energy[col][seam[col]] = calcEnergy(col, seam[col]);
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        validateVertSeam(seam);
        Picture newPic = new Picture(--width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (col < seam[row]) {
                    newPic.set(col, row, pic.get(col, row));
                    energy[col][row] = energy[col][row];
                }
                else {
                    newPic.set(col, row, pic.get(col + 1, row));
                    energy[col][row] = energy[col + 1][row];
                }
            }
        }

        pic = newPic;

        for (int row = 0; row < height; row++) {
            if (seam[row] > 0) energy[seam[row] - 1][row] = calcEnergy(seam[row] - 1, row);
            if (seam[row] < width - 1) energy[seam[row]][row] = calcEnergy(seam[row], row);
        }

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double distance = Double.POSITIVE_INFINITY;
        double[][] distTo = new double[width][height];
        Point[][] edgeTo = new Point[width][height];

        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                if (row == 0) distTo[col][row] = 0;
                else distTo[col][row] = Double.POSITIVE_INFINITY;


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Point p = new Point(col, row);

                for (Point adj : getAdjVert(p)) {
                    if (distTo[adj.x][adj.y] > distTo[p.x][p.y] + energy[p.x][p.y]) {
                        distTo[adj.x][adj.y] = distTo[p.x][p.y] + energy[p.x][p.y];
                        edgeTo[adj.x][adj.y] = p;
                        if (adj.y == height - 1 && distTo[adj.x][adj.y] < distance) {
                            distance = distTo[adj.x][adj.y];
                            for (int count = height; adj != null; adj = edgeTo[adj.x][adj.y])
                                seam[--count] = adj.x;
                        }
                    }
                }

            }
        }
        return seam;

    }

    private double calcEnergy(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) return 1000;
        /*
         * ############################
         * #                          #
         * #                          #
         * #                          #
         * #                          #
         * #                          #
         * #                          #
         * #                          #
         * ############################
         * the # is represent the 1000*/

        int xRight = pic.getRGB(x + 1, y);
        int xLeft = pic.getRGB(x - 1, y);
        int yUp = pic.getRGB(x, y + 1); // this is down
        int yDown = pic.getRGB(x, y - 1); // this is up


        double xRed = (xRight >> 16) - (xLeft >> 16); // divided in 2 sixteen time
        double xGreen = ((xRight >> 8) & 255) - ((xLeft >> 8) & 255);
        // the & operator
        //As a Bitwise AND: & operator is used for adding Bitwise numbers in Java. Bitwise numbers are binary numbers stored in the form of integers. Some people will ask what is the use of these Bitwise numbers anyway? Why not store every number in its decimal form and perform the normal operations using our traditional operators: +, -, /, %, *. Its because all our encoding and decoding of data is done in bits, as they allow packing a huge amount of information into a tiny space.*/
        double xBlue = (xRight & 255) - (xLeft & 255);

        double yRed = (yUp >> 16) - (yDown >> 16);
        double yGreen = ((yUp >> 8) & 255) - ((yDown >> 8) & 255);
        double yBlue = (yUp & 255) - (yDown & 255);

        double xGrad = xRed * xRed + xGreen * xGreen + xBlue * xBlue;
        double yGrad = yRed * yRed + yGreen * yGreen + yBlue * yBlue;

        double energy = Math.sqrt(xGrad + yGrad);

        return energy;
    }


    private void validate(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
    }

    private void validate(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) throw new IllegalArgumentException();
    }

    private void validateHorizSeam(int[] seam) {
        if (seam == null || height < 2 || seam.length != width || seam[0] < 0 || seam[0] >= height)
            throw new IllegalArgumentException();

        for (int i = 1; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= height || Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();
    }

    private void validateVertSeam(int[] seam) {
        if (seam == null || width < 2 || seam.length != height || seam[0] < 0 || seam[0] >= width)
            throw new IllegalArgumentException();

        for (int i = 1; i < seam.length; i++)
            if (seam[i] < 0 || seam[i] >= width || Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();
    }
}
