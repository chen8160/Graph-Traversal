import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 3/20/2017.
 */
public class WikiCrawler {

    public static final String BASE_URL = "https://en.wikipedia.org";

    private String seedUrl;
    private int max;
    private String fileName;

    public WikiCrawler(String seedUrl, int max, String fileName) {

        this.seedUrl = seedUrl;
        this.max = max;
        this.fileName = fileName;

    }

    public ArrayList<String> extractLinks(String doc) {
        //TODO

        Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();

        boolean findP = false;

        ArrayList<String> ret = new ArrayList<>();

        Scanner scanner = new Scanner(doc);

        while (scanner.hasNextLine()) {

            String s = scanner.nextLine();

            if (!findP) {
                if (Pattern.matches("^<p>.*", s)) {
                    findP = true;
                } else {
                    continue;
                }
            }

            Pattern p = Pattern.compile("<a href=\"/wiki/\\w+\"");
            Matcher m = p.matcher(s);
            while (m.find()) {
                String output = m.group();
                output = output.substring(9, output.length() - 1);

                if (hashtable.contains(output)) {
                    continue;
                } else {
                    hashtable.put(output.hashCode(), output);
                    ret.add(output);
                    System.out.println(output);
                }
            }
        }


        return ret;
    }

    public void crawl() {

        try {

            URL url = new URL(BASE_URL + seedUrl);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String doc = ReadBigStringIn(br);
            extractLinks(doc);

            //TODO

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String ReadBigStringIn(BufferedReader buffIn) throws IOException {
        StringBuilder everything = new StringBuilder();
        String line;
        while ((line = buffIn.readLine()) != null) {
            everything.append(line + '\n');
        }
        return everything.toString();
    }


}
