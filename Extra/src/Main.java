import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by inventionsbyhamid on 21/7/16.
 */
public class Main {

    private static HashSet<String> words = new HashSet<>();
    public static Map<String, Integer> dictionary = new TreeMap<>();

    public static String exec(String path) {
        dictionary = new TreeMap<>();

        /*BufferedReader k = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("C:\\Users\\Barracuda\\IdeaProjects\\TextMining\\data\\A.com.test\\51121");
        String path = k.readLine();*/

        try {
            Files.walk(Paths.get(path)).forEach(txtfile -> {
                try {
                    System.out.println("Parsing File: " + txtfile.getFileName().toString());
                    Files.lines(txtfile).forEach(line -> {
                        String w[] = line.split(" ");
                        for (String s : w) {
                            s = s.toLowerCase();
                            if (s.length() > 0)
                                if (dictionary.containsKey(s))
                                    dictionary.put(s, dictionary.get(s) + 1);
                                else
                                    dictionary.put(s, 1);
                        }
                        words.addAll(Arrays.asList(w));
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Total distinct words = " + words.size());
        //System.out.println(dictionary);
        //System.out.println(entriesSortedByValues(dictionary).toString().replace(", ", "\n"));
        //System.out.println("done");
        //System.out.println("Total distinct words = " + words.size() + "\n" + entriesSortedByValues(dictionary).toString().replace(", ", "\n"));
        return "Total distinct words = " + words.size() + "\n" + entriesSortedByValues(dictionary).toString().replace(", ", "\n").replace("=", "  =  ");
    }

    public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}