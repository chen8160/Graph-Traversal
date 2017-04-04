import java.io.*;
import java.net.URL;
import java.util.*;
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

        Hashtable<Integer, String> hashTable = new Hashtable<Integer, String>();

        boolean findP = false;

        ArrayList<String> ret = new ArrayList<>();

        Scanner scanner = new Scanner(doc);

        while (scanner.hasNextLine()) {

            String s = scanner.nextLine();

            if (!findP) {
                if (Pattern.matches(".*<p>.*", s)) {
                    findP = true;
                } else {
                    continue;
                }
            }

            Pattern p = Pattern.compile("<a href=\"/wiki/([^:#\\s])+\"");
            Matcher m = p.matcher(s);

            while (m.find()) {
                String output = m.group();
//                    System.out.println(output);
                output = output.substring(9, output.length() - 1);

                if (hashTable.contains(output)) {
                    continue;
                } else {
                    hashTable.put(output.hashCode(), output);
                    ret.add(output);
//                    System.out.println(output);
                }
            }

        }

        return ret;
    }

    public void crawl() {

        Queue<String> queue = new LinkedList<String>();
        queue.add(seedUrl);

        try {

            StringBuilder sb = new StringBuilder();
            sb.append(max + "\n");

            int count = 1;
            HashSet<String> visited = new HashSet<>();

            while (!queue.isEmpty()) {

                String urlString = queue.remove();
                URL url = new URL(BASE_URL + urlString);
                InputStream is = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String doc = ReadBigStringIn(br);
                ArrayList<String> urls = extractLinks(doc);

                for (String newUrl : urls) {

                    if (visited.contains(newUrl)) {

                        sb.append(urlString + " " + newUrl + '\n');

                    } else if (!visited.contains(newUrl) && count < max) {
                        visited.add(newUrl);
                        sb.append(urlString + " " + newUrl + '\n');

                        queue.add(newUrl);
                        count++;
                    }
                }
            }

            PrintWriter printWriter = new PrintWriter(fileName);
            printWriter.write(sb.toString());
            printWriter.close();

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
