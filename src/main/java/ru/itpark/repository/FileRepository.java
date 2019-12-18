package ru.itpark.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileRepository {


    public Map<String, List<String>> parseAllFilesByPhrase(List<File> fileList, String phrase) throws FileNotFoundException {

        Map<String, List<String>> foundStringsMap = new HashMap<>();

        for (File file : fileList) {
            List<String> stringsFromFile = this.parseFile(phrase, file);
            if (!stringsFromFile.isEmpty()) {
                foundStringsMap.put(file.getName(), stringsFromFile);
            }
        }
        return foundStringsMap;

    }

    private List<String> parseFile(String phrase, File file) throws FileNotFoundException {
        System.out.println("parse file started "+phrase);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Scanner scanner = new Scanner(file);
        List<String> foundStringsFromFile = new ArrayList<>();
        while (scanner.hasNextLine()) {
            final String lineFromFile = scanner.nextLine();
            if (lineFromFile.contains(phrase)) {
                foundStringsFromFile.add(lineFromFile);
            }
        }
        System.out.println("parse file is finished "+phrase);
        return foundStringsFromFile;
    }
}
