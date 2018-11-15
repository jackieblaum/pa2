import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void getImportance() {
        ImageProcessor img = new ImageProcessor("imgprocessor_ex2.txt");
        img.getImportance();
    }

//    @Test
//    public void writeReduced() {
//    }
}