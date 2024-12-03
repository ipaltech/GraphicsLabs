import javafx.application.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    Canvas canvas;
    Scene scene;
    BorderPane rootBorderPane;


    private void initCanvas(int v, int v1){
        rootBorderPane = new BorderPane();
        canvas = new Canvas(v, v1);

        rootBorderPane.setCenter(canvas);

        scene = new Scene(rootBorderPane, v, v1);
    }

    private void initStage(Stage stage){
        stage.setTitle("Відображення точок з датасету DS6.txt (Палієнко Ілля КМ-33)");
        stage.setScene(scene);
    }

    private void drawByPoints(ArrayList<Point2D> point2DArrayList){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK);
        if(point2DArrayList != null){
            for(Point2D p : point2DArrayList){
                gc.fillOval(p.getX(),p.getY(), 5,5);
            }
        }
    }

    private void saveCanvasToFile(String fileName, String format){
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        Alert isExported = new Alert(Alert.AlertType.INFORMATION);
        isExported.setTitle("Експорт");
        isExported.setHeaderText(String.format("Файл %s збережено як %s",fileName,format));


        File file = new File(fileName);
        try{
            if(format.equals("jpeg")){
                BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(writableImage, null);
                BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);

                Graphics2D g = rgbImage.createGraphics();
                g.setPaint(java.awt.Color.WHITE); 
                g.fillRect(0, 0, rgbImage.getWidth(), rgbImage.getHeight());
                g.drawImage(bufferedImage, 0, 0, null);
                g.dispose();

                ImageIO.write(rgbImage, format, file);
                isExported.show();
            }
            else if(format.equals("bmp")){
                BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(writableImage, null);
                BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);

                Graphics2D g = rgbImage.createGraphics();
                g.setPaint(java.awt.Color.WHITE);
                g.fillRect(0, 0, rgbImage.getWidth(), rgbImage.getHeight());
                g.drawImage(bufferedImage, 0, 0, null);
                g.dispose();

                ImageIO.write(rgbImage, format, file);
                isExported.show();
            }
            else{
                ImageIO.write(javafx.embed.swing.SwingFXUtils.fromFXImage(writableImage, null), format, file);
                isExported.show();
            }
        }
        catch(IOException e){
            e.printStackTrace();

        }

    }

    private void initMenu(int v, int v1){
        Menu exportMenu = new Menu("Експорт");
        MenuBar menuBar = new MenuBar();

        MenuItem exportToPng = new Menu("Зберегти як PNG");
        MenuItem exportToJpeg = new Menu("Зберегти як JPEG");
        MenuItem exportToGif = new Menu("Зберегти як GIF");
        MenuItem exportToTiff = new Menu("Зберегти як TIFF");
        MenuItem exportToBmp = new Menu("Зберегти як BMP");

        exportToPng.setOnAction(e -> saveCanvasToFile("output.png","png"));
        exportToJpeg.setOnAction(e -> saveCanvasToFile("output.jpeg","jpeg"));
        exportToGif.setOnAction(e -> saveCanvasToFile("output.gif","gif"));
        exportToTiff.setOnAction(e -> saveCanvasToFile("output.tiff","tiff"));
        exportToBmp.setOnAction(e -> saveCanvasToFile("output.bmp","bmp"));

        exportMenu.getItems().addAll(exportToPng, exportToJpeg, exportToBmp, exportToTiff, exportToGif);

        menuBar.getMenus().addAll(exportMenu);

        rootBorderPane.setTop(menuBar);


    }

    @Override
    public void init(){
        initCanvas(540, 960);
        initMenu(540,100);

    }

    @Override
    public void start(Stage stage){
        initStage(stage);
        ArrayList <Point2D> point2DArrayList = ReadCoords.read("res/DS6.txt");
        drawByPoints(point2DArrayList);
        stage.show();

    }
}
