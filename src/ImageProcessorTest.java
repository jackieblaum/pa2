import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void getImportance() {
        ImageProcessor img = new ImageProcessor("src\\imgprocessor_ex.txt");
        ArrayList<ArrayList<Integer>> real = img.getImportance();
        ArrayList<Integer> row0 = new ArrayList<>(Arrays.asList(8981, 47351, 6048, 53797));
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(20545, 26381, 14067, 25958));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(11408, 35444, 23271, 33355));
        ArrayList<ArrayList<Integer>> ideal = new ArrayList<>(Arrays.asList(row0, row1, row2));
        assertEquals(ideal, real);
    }

    @Test
    public void reduce_by_one(){
        ImageProcessor img = new ImageProcessor("src\\imgprocessor_ex.txt");
        img.reduce_by_one();
        ArrayList<ArrayList<Integer>> real = img.getImportance();
        ArrayList<Integer> row0 = new ArrayList<>(Arrays.asList(134604,6048,138480));
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(34365,14067,35743));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(73813,23271,69303));
        ArrayList<ArrayList<Integer>> ideal = new ArrayList<>(Arrays.asList(row0, row1, row2));
        assertEquals(ideal, real);
    }

    @Test
    public void reduce_by_two(){
        ImageProcessor img = new ImageProcessor("src\\imgprocessor_ex.txt");
        img.reduce_by_one();
        img.reduce_by_one();
        ArrayList<ArrayList<Integer>> real = img.getImportance();
        ArrayList<Integer> row0 = new ArrayList<>(Arrays.asList(8516,14962));
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(987,564));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(14323,12234));
        ArrayList<ArrayList<Integer>> ideal = new ArrayList<>(Arrays.asList(row0, row1, row2));
        assertEquals(ideal, real);
    }

    @Test
    public void reduce_by_three(){
        ImageProcessor img = new ImageProcessor("src\\imgprocessor_ex.txt");
        img.reduce_by_one();
        img.reduce_by_one();
        img.reduce_by_one();
        ArrayList<ArrayList<Integer>> real = img.getImportance();
        ArrayList<Integer> row0 = new ArrayList<>(Arrays.asList(8516));
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(225));
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(11381));
        ArrayList<ArrayList<Integer>> ideal = new ArrayList<>(Arrays.asList(row0, row1, row2));
        assertEquals(ideal, real);
    }

    @Test
    public void time_30_40(){
        ImageProcessor img = new ImageProcessor("src\\ex_30_40.txt");
        for(int k = 0; k < 10; k++) {
            img.reduce_by_one();
        }
        assertEquals(true, true);
    }

    @Test
    public void time_300_400(){
        ImageProcessor img = new ImageProcessor("src\\ex_300_400.txt");
        for(int k = 0; k < 10; k++) {
            img.reduce_by_one();
            System.out.println(k + "");
        }
        assertEquals(true, true);
    }

    @Test
    public void time_30_4000(){
        ImageProcessor img = new ImageProcessor("src\\ex_30_4000.txt");
        for(int k = 0; k < 10; k++) {
            img.reduce_by_one();
            System.out.println(k + "");
        }
        assertEquals(true, true);
    }

    @Test
    public void time_4000_30(){
        ImageProcessor img = new ImageProcessor("src\\ex_4000_30.txt");
        for(int k = 0; k < 10; k++) {
            img.reduce_by_one();
            System.out.println(k + "");
        }
        assertEquals(true, true);
    }
//    @Test
//    public void writeReduced() {
//    }
}