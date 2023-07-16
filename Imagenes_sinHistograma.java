import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//clases para trabajar con archivos, representar colores y manipular imagenes


public class Imagenes_sinhistograma {
   public static void main(String args[]) {


      System.out.println("Beginning...");
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


            double matriz[][] = {
                { 1, 2, 1 },
                { 2, 4, 2 },
                { 1, 2, 1 } };


            // aplicar el kernel a la matriz
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


            // crear nuevo color
            Color color = new Color(red, green, blue);
            //Establecer un nuevo objeto de color en la imagen borrosa
            blurredImg.setRGB(x, y, color.getRGB());
         }
      }


      // guardar imagen modificada
      try {
         file = new File("C:\\Users\\Mahin\\Desktop\\proyecto LP\\ulima_new.jpg");
         ImageIO.write(blurredImg, "jpg", file);
         System.out.println("Done...");
      } catch (IOException e) {
         System.out.println(e);
      }
   }
}
