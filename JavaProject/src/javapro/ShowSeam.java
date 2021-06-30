package javapro;

class ShowSeams {

    public static Picture showHorizontalSeam(Picture loadPicture) {
        SeamCarver sc = new SeamCarver(loadPicture);
        Picture picture = SCUtility.toEnergyPicture(sc);
        int[] horizontalSeam = sc.findHorizontalSeam();
        Picture overlay = SCUtility.seamOverlay(picture, true, horizontalSeam);
        return overlay;
    }


    public static Picture showVerticalSeam(Picture loadPicture) {
        SeamCarver sc = new SeamCarver(loadPicture);
        Picture picture = SCUtility.toEnergyPicture(sc);
        int[] verticalSeam = sc.findVerticalSeam();
        Picture overlay = SCUtility.seamOverlay(picture, false, verticalSeam);
        return overlay;
    }

}

