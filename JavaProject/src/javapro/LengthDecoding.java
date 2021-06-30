package javapro;


import java.io.*;
import java.util.ArrayList;

public class LengthDecoding {

    ArrayList<Integer> repetition = new ArrayList<>();
    ArrayList<Integer> repeatedValue = new ArrayList<>();
    ArrayList<Integer> textFile = new ArrayList<>();
    private ArrayList<Integer> unzip = new ArrayList<>();
    FileWriter myWriter = new FileWriter("unzipped.txt");

    LengthDecoding() throws IOException {
        File file = new File("D:\\forthYear\\MultiMedia\\Project-Final\\JavaProject\\zipped.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String s[] = new String[0];
        while ((st = br.readLine()) != null){
            System.out.println(st);
            s = st.split(" ");
        }

        for (int i = 0; i < s.length; i++) {
            textFile.add(Integer.parseInt(s[i]));
        }
    }

    public void decoding() throws IOException {
        for (int i = 0; i < textFile.size(); i += 2) {
            repetition.add(textFile.get(i));
        }
        for (int i = 1; i < textFile.size(); i += 2) {
            repeatedValue.add(textFile.get(i));
        }

        for (int i = 0; i < repetition.size(); i++) {
            int repeatedTime = repetition.get(i);
            int value = repeatedValue.get(i);

            for (int j = 0; j < repeatedTime; j++) {
                unzip.add(value);
            }
        }

        System.out.println();
        for (int x : unzip
        ) {
            System.out.print(x + " ");
        }

        writeInFolder();
    }

    public void writeInFolder() throws IOException {
        for (int i = 0; i < unzip.size(); i++) {
            String convertedChar = Character.toString(unzip.get(i));
            System.out.print(unzip.get(i) + " ");
            myWriter.write(convertedChar);
        }
        myWriter.close();
    }
}
