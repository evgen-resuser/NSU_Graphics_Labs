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

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);

            writer.write(String.format("n=%d m=%d m1=%d x=%s y=%s z=%s zf=%s%n",
                    toSave.getSegmentsNum(), toSave.getGeneratrixCount(), toSave.getLinesInCircle(),
                    String.format("%s", decimalFormat.format(toSave.getX())),
                    String.format("%s", decimalFormat.format(toSave.getY())),
                    String.format("%s", decimalFormat.format(toSave.getZ())),
                    String.format("%s", decimalFormat.format(toSave.getZf()))
            ));
            StringBuilder points = new StringBuilder();
            int sum = 7;

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
