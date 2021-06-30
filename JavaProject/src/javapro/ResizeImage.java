package javapro;

public class ResizeImage {
    Picture inputPicture;
    int removeColumns;
    int removeRows;

    ResizeImage(String pathOfPicture, int removeColumns, int removeRows){
        this.inputPicture = new Picture(pathOfPicture);
        this.removeColumns = removeColumns;
        this.removeRows = removeRows;
    }

    ResizeImage(Picture picture, int removeColumns, int removeRows){
        this.inputPicture = new Picture(picture);
        this.removeColumns = removeColumns;
        this.removeRows = removeRows;
    }




    public Picture doTheResize() {
        SeamCarver seamCarver = new SeamCarver(this.inputPicture);

        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = seamCarver.findHorizontalSeam();
            seamCarver.removeHorizontalSeam(horizontalSeam);
        }
        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = seamCarver.findVerticalSeam();
            seamCarver.removeVerticalSeam(verticalSeam);
        }

        Picture outputPicture = seamCarver.picture();
        inputPicture.show();
        outputPicture.show();
        return outputPicture;
    }
}
