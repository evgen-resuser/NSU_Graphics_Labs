package org.evgen.editor.file;

import org.evgen.editor.BSpline;
import org.evgen.utils.SplinePoint;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FileBuilder {



    private FileBuilder(){}

    public static void saveFile(String name, BSpline toSave) {
        try (FileWriter writer = new FileWriter(name)) {

            writer.write(String.format("n=%d k=%d m=%d m1=%d r=%d z=%d%n",
                    toSave.getSegmentsNum(), toSave.getControlPoints().size(), 0, 0, 0, 0));
            StringBuilder points = new StringBuilder();
            int sum = 6;

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);
            for (SplinePoint p : toSave.getControlPoints()) {
                points.append(String.format("%s;%s ", decimalFormat.format(p.getX()),
                        decimalFormat.format(p.getY())));
                sum++;
            }
            writer.write(points.append("\n").toString());
            writer.write(String.valueOf(sum));

        } catch ( IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
