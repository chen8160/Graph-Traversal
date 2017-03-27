import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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

        boolean findP = false;

        try {

            URL url = new URL(BASE_URL + seedUrl);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String s = br.readLine();

            while (s != null) {

                if (!findP) {
                    if (Pattern.matches("^<p>.*", s)) {
                        findP = true;
                    } else {
                        s = br.readLine();
                        continue;
                    }
                }

                Pattern p = Pattern.compile("<a href=\"/wiki/\\w+\"");
                Matcher m = p.matcher(s);
                while (m.find())
                    System.out.println(m.group());

                s = br.readLine();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void crawl() {
        //TODO
    }


}
