package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class TsvReader {

    public static List<String[]> readFile(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Header überspringen
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] cols = line.split("\t");
                rows.add(cols);
            }
        }
        return rows;
    }
}
