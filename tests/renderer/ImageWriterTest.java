package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Ray;

public class ImageWriterTest {

    @Test
    public void writeToImageTest() {
        ImageWriter imageWriter=new ImageWriter("testImage",800,500);
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        int interval=50;
        Color red=new Color(255,0,0);
        Color blue=new Color(0,0,255);

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if(i%interval==0 || j%interval==0)
                    imageWriter.writePixel(j, i, red);
                else
                    imageWriter.writePixel(j, i, blue);
            }
        }
        imageWriter.writeToImage();
    }
}
