import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadCoords {
    public static ArrayList<Point2D> read(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            ArrayList<Point2D> points2DL = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);

                String x = tokenizer.nextToken();
                String y = tokenizer.nextToken();

                Point2D point = new Point2D.Float(Float.parseFloat(x), Float.parseFloat(y));
                points2DL.add(point);
            }

            return points2DL;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
