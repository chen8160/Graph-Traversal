import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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

            URL url = new URL(BASE_URL + doc);
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



                System.out.println(s);

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
