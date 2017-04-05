import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Yuxiang Chen on 2017/4/1.
 */
public class GraphProcessor {

    private int max;
    private HashMap<String, ArrayList<String>> graph;
    private HashMap<String, ArrayList<String>> graphR;
    private HashMap<String, HashSet<String>> components;

    public GraphProcessor(String graphData) {
        File f = new File(graphData);
        graph = new HashMap<>();
        graphR = new HashMap<>();
        components = new HashMap<>();

        try {
            //Build the graph from file to hashMap
            Scanner scanner = new Scanner(f);

            if (scanner.hasNextInt()) {
                max = scanner.nextInt();
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScan = new Scanner(line);

                String v = lineScan.next();
                String u = lineScan.next();

                ArrayList<String> out = graph.get(v);
                if (out == null){
                    out = new ArrayList<>();
                    graph.put(v, out);
                }
                out.add(u);

                ArrayList<String> outR = graphR.get(u);
                if (outR == null){
                    outR = new ArrayList<>();
                    graphR.put(u, outR);
                }
                outR.add(v);

            }
            //DEBUG
            SCC();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int outDegree(String v) {
        ArrayList<String> out = graph.get(v);
        if (out != null){
            return out.size();
        }
        return 0;
    }

    public boolean sameComponent(String u, String v) {
        return false;
    }

    public ArrayList<String> componentVertices(String v) {
        return null;
    }

    public int lagestComponent() {
        return 0;
    }

    public int numComponents() {
        return 0;
    }

    public ArrayList<String> bfsPath(String u, String v) {
        return null;
    }


    //Private Methods
    private void SCC(){

        HashSet<String> mark = new HashSet<>();
        String[] finishTime = new String[max];
        computeOrder(mark, finishTime);
//        System.out.print("");
        mark.clear();

        for (int i = max - 1; i >= 0; i--){
            String v = finishTime[i];
            if (!mark.contains(v)){
                HashSet<String> component = new HashSet<>();
            }
        }


    }

    private void computeOrder(HashSet<String> mark, String[] finishTime){

        mark.clear();
        int counter = 0;
        for (String v : graphR.keySet()) {
            if (!mark.contains(v)) {
                counter = finishDFS(mark, v, counter, finishTime);
            }
        }
    }

    private int finishDFS(HashSet<String> mark, String v, int counter, String[] finishTime){

        mark.add(v);
        for (String u : graphR.get(v)){
            if (!mark.contains(u)){
                counter = finishDFS(mark, u, counter, finishTime);
            }
        }

        finishTime[counter] = v;
        counter++;
        return counter;
    }

}
