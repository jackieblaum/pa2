import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class WGraphTest {

    WGraph graph = new WGraph("C:\\Users\\Jackie\\Documents\\CS311\\pa2\\src\\example.txt");

    @Test
    public void v2V() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(2);
        result.add(5);
        result.add(6);
        System.out.println(graph.V2V(1,2,5,6).toString());
        assertEquals(graph.V2V(1,2,5,6),result);
    }
//
//    @Test
//    public void v2S() {
//    }
//
//    @Test
//    public void s2S() {
//    }
}