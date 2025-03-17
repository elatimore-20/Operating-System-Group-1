import java.io.*;

public class fcfc {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter text file path: ");
        String path = br.readLine();
        FileReader fr = new FileReader(path);

        int i;
        while ((i=fr.read()) != -1) {
            System.out.print((char)i);
        }
        fr.close();
    }
}