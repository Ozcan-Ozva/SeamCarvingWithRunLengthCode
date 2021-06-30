package javapro;

import java.io.IOException;

public class TestEncode {
    public static void main(String[] args) throws IOException {
        LengthEnCoding lengthDeEnCoding =
                new LengthEnCoding("D:\\forthYear\\MultiMedia\\Project-Final\\JavaProject\\src\\javapro\\Zip.txt");

        lengthDeEnCoding.zipArray();

        LengthDecoding lengthDecoding = new LengthDecoding();
        lengthDecoding.decoding();
    }
}
