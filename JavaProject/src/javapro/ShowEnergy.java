package javapro;

public class ShowEnergy {

    public static Picture EnergyPicture(Picture picture) {
        SeamCarver sc = new SeamCarver(picture);
        return SCUtility.getEnergyPicture(sc);
    }
}
