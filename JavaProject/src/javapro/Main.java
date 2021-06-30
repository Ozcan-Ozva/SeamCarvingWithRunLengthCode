package javapro;


public class Main {
    public static void main(String[] args) {
        int [][] x = new int[2][2];
        x[0][0] = 1;
        x[0][1] = 2;
        x[1][0] = 3;
        x[1][1] = 4;

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(x[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                if (i == 0) x[i][j] =0;
                else x[i][j] = 100;
            }
        }

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(x[i][j] + " ");
            }
            System.out.println();
        }

    }
}
