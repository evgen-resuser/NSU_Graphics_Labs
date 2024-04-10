package org.evgen.editor.file;

import org.evgen.editor.BSpline;
import org.evgen.utils.SplinePoint;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader {

    private BSpline result;
    private int read;

    public BSpline loadExample() throws IOException{
        File file = new File("src/main/java/org/evgen/editor/file/example.txt"); //todo make with resources
        return readSplineFromFile(file);
    }

    public BSpline readSplineFromFile(File file) throws IOException {
        result = new BSpline();
        read = 0;

        Scanner scanner;
        try {
            if (file == null) throw new IOException("error while opening the file!");
            scanner = new Scanner(file);

            readParams(scanner.nextLine());
            readPoints(scanner.nextLine());

            int expected = scanner.nextInt();
            if (expected != read) throw new IOException("too many/too little values!");

            scanner.close();

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        return result;
    }

    private void readParams(String string) throws IOException {
        if (string == null) {
            throw new IOException("parameters line is null!");
        }

        Map<String, Integer> map = new HashMap<>();

        String[] params = string.split(" ");
        String[] p;
        int value;
        try {
            for (String s : params) {
                p = s.split("=");
                if (p.length == 2) {
                    value = Integer.parseInt(p[1]);
                    map.put(p[0], value);
                } else {
                    throw new IOException("error while parsing parameter "+p[0]);
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        result.setSegmentsNum(map.get("n"));
        //result.(map.get("k"));

        read += map.size();
    }

    private void readPoints(String string) throws IOException {
        if (string == null) {
            throw new IOException("coordinates line is null!");
        }

        String[] points = string.split(" ");

        try {
            for (String p : points) {
                String[] coords = p.split(";");

                if (coords.length != 2) {
                    throw new IOException("error while parsing coordinate " + Arrays.toString(coords));
                }
                 result.addPoint(
                         new SplinePoint(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]))
                 );
                read++;
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

}
