package org.example;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class App {
    Map<String, List<Integer>> indexMap = new TreeMap<>();

    private static final Set<String> fluffWords = new HashSet<>(Arrays.asList(
        "a", "and", "the", "to", "for", "they", "is", "it", "that", "this", "an",
        "in", "on", "at", "by", "of", "with", "as", "but", "or", "not"
    ));

    public static void addToMap(Map<String, List<Integer>> map, String key, int wordCount){
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<>());
        }

        map.get(key).add(wordCount);
    }

    void indexWordsFromFile(String fileName) {
        int totalWordIndex = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
                for (String word : words) {
                    if (!fluffWords.contains(word) && !word.isEmpty()) {
                        addToMap(indexMap, word, totalWordIndex);
                        totalWordIndex++;
                    } else {
                        totalWordIndex++;
                    }
                }
            }

            writeIndexWordsToFile(indexMap, "invertedWords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeIndexWordsToFile(Map<String, List<Integer>> indexMap, String outputFileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (Entry<String, List<Integer>> word : indexMap.entrySet()) {
                writer.write("\"" + word.getKey() + "\"" + ": " + word.getValue().toString());
                writer.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App app = new App();    //Couldn't use the name of the file? Had to use the path for Windows instead?
        app.indexWordsFromFile("C:\\Users\\blake\\OneDrive\\Documents\\GitHub\\CSC216-Searching-for-the-Answer-OCERESA\\app\\src\\main\\java\\org\\example\\sample.txt");   
    }
}
