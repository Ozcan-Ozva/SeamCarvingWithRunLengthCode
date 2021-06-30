package javapro;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LengthEnCoding {

    ArrayList<Integer> unzip = new ArrayList<>();
    FileWriter myWriter = new FileWriter("zipped.txt");
    ArrayList<Integer> zip = new ArrayList<>();

    LengthEnCoding(String fileName) throws IOException {
        FileReader fr =
                new FileReader(fileName);
        int k;
        while ((k = fr.read()) != -1) {
            unzip.add((int) k);
        }
    }

    public void zipArray() throws IOException {
        int c = 1;

        for (int i = 0; i < unzip.size() - 1; i++) {
            if (unzip.get(i) == unzip.get(i+1)) {
                c++;
            } else {
                zip.add(c);
                zip.add(unzip.get(i));
                c = 1;
            }
        }
        zip.add(c);
        zip.add(unzip.get(unzip.size() - 1));
        writeInFolder();
    }

    public void writeInFolder() throws IOException {
        for (int i = 0; i < zip.size(); i++) {
            System.out.print(zip.get(i) + " ");
            myWriter.write(zip.get(i).toString() + " ");
        }
        myWriter.close();
    }


}
