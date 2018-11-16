import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void getImportance() {
        ImageProcessor img = new ImageProcessor("imgprocessor_ex.txt");
        ArrayList<ArrayList<Integer>> real = img.getImportance();
        ArrayList<Integer> row0 = new ArrayList<>(Arrays.asList(8981, 47351, 6048, 53797));
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(20545, 26381, 14067, 25958));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(11408, 35444, 23271, 33355));
        ArrayList<ArrayList<Integer>> ideal = new ArrayList<>(Arrays.asList(row0, row1, row2));
        assertEquals(ideal, real);
    }

//    @Test
//    public void writeReduced() {
//    }
}