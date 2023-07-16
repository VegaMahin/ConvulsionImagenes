import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Imagenes_GS3x3 extends Application {
   public static void main(String args[]) {
      launch(args);
   }

   @Override
   public void start(Stage stage) {
      System.out.println("Beginning...");
      
      long InicioTiem = System.currentTimeMillis(); // Obtener el tiempo de inicio
      
      File file = null;
      BufferedImage img = null;
      try {
         file = new File("C:\\Users\\Mahin\\Desktop\\proyecto LP\\ulima.jpg"); // Ruta donde se encuentra la imagen
         img = ImageIO.read(file); // Carga de la imagen en la variable img
      } catch (IOException e) {
         System.out.println(e);
      }

      BufferedImage blurredImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

      for (int y = 0; y < img.getHeight(); y++) {
         for (int x = 0; x < img.getWidth(); x++) {
            int red = 0;
            int green = 0;
            int blue = 0;

            double matriz[][] = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };

            // Aplicar el kernel a la matriz
            int auxRed;
            int auxGreen;
            int auxBlue;

            for (int j = -1; j <= 1; j++) {
               for (int i = -1; i <= 1; i++) {
                  try {
                     Color pixelColor = new Color(img.getRGB(x + i, y + j));

                     double auxMatrix = (matriz[i + 1][j + 1]) / 16.0;
                     auxRed = (int) (pixelColor.getRed() * auxMatrix);
                     auxGreen = (int) (pixelColor.getGreen() * auxMatrix);
                     auxBlue = (int) (pixelColor.getBlue() * auxMatrix);
                  } catch (ArrayIndexOutOfBoundsException e) {
                     auxRed = 0;
                     auxGreen = 0;
                     auxBlue = 0;
                  }

                  red += auxRed;
                  green += auxGreen;
                  blue += auxBlue;
               }
            }

            // Crear nuevo color
            Color color = new Color(red, green, blue);
            // Establecer un nuevo objeto de color en la imagen borrosa
            blurredImg.setRGB(x, y, color.getRGB());
         }
      }

      // Guardar imagen modificada
      try {
         file = new File("C:\\Users\\Mahin\\Desktop\\proyecto LP\\ulima_new.jpg");
         ImageIO.write(blurredImg, "jpg", file);
         System.out.println("Done...");
      } catch (IOException e) {
         System.out.println(e);
      }

      // Calcular el histograma
      int[] histograma = new int[256];

      for (int y = 0; y < blurredImg.getHeight(); y++) {
         for (int x = 0; x < blurredImg.getWidth(); x++) {
            Color pixelColor = new Color(blurredImg.getRGB(x, y));
            int gray = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;
            histograma[gray]++;
         }
      }

      // Crear el gráfico de histograma
      CategoryAxis xAxis = new CategoryAxis();
      NumberAxis yAxis = new NumberAxis();
      BarChart<String, Number> histogramChart = new BarChart<>(xAxis, yAxis);
      
      histogramChart.setPrefSize(600, 1000);

      XYChart.Series<String, Number> series = new XYChart.Series<>();
      for (int i = 0; i < 256; i++) {
         series.getData().add(new XYChart.Data<>(String.valueOf(i), histograma[i]));
      }

      histogramChart.getData().add(series);

      // Crear la escena y mostrar el gráfico
      VBox vbox = new VBox(histogramChart);
      Scene scene = new Scene(vbox, 800, 600);
      stage.setTitle("Histograma");
      stage.setScene(scene);
      stage.show();

      long FinTiem = System.currentTimeMillis(); // Obtener el tiempo de finalización
      long TiempoEjecucion = FinTiem - InicioTiem; // Calcular el tiempo de ejecución
      
      System.out.println("Tiempo de ejecución: " + TiempoEjecucion + " milisegundos");
   }
}
