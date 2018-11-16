import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;


public class WGraphTest {

    @Test
    public void v2Vexample() {
        WGraph graph = new WGraph("src\\example.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 1,2,3,4,5,6);
        System.out.println(graph.V2V(1,2,5,6).toString());
        assertEquals(result, graph.V2V(1,2,5,6));
    }

    @Test
    public void v2Vex1() {
        WGraph graph = new WGraph("src\\ex1.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 1,2,2,1,3,3,4,3,5,2);
        System.out.println(graph.V2V(1,2,5,2).toString());
        assertEquals(result, graph.V2V(1,2,5,2));
    }

    @Test
    public void v2Vex2() {
        WGraph graph = new WGraph("src\\ex2.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 5,1,4,2,3,1,2,2,2,3,3,4,3,5);
        System.out.println(graph.V2V(5,1,3,5).toString());
        assertEquals(result, graph.V2V(5,1,3,5));
    }

    @Test
    public void v2Sexample() {
        WGraph graph = new WGraph("src\\example.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 1,2,7,8);
        ArrayList<Integer> s = new ArrayList<>();
        Collections.addAll(s, 3,4,5,6,7,8);
        System.out.println(graph.V2S(1,2,s).toString());
        assertEquals(result, graph.V2S(1,2,s));
    }

    @Test
    public void v2Sexample2() {
        WGraph graph = new WGraph("src\\example.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 5,6,1,2,7,8);
        ArrayList<Integer> s = new ArrayList<>();
        Collections.addAll(s, 3,4,7,8);
        System.out.println(graph.V2S(5,6,s).toString());
        assertEquals(result, graph.V2S(5,6,s));
    }

    @Test
    public void s2Sex1() {
        WGraph graph = new WGraph("src\\ex1.txt");
        ArrayList<Integer> result = new ArrayList<>();
        Collections.addAll(result, 2,1,3,3,4,3,5,2);
        ArrayList<Integer> s1 = new ArrayList<>();
        Collections.addAll(s1, 2,3,2,1);
        ArrayList<Integer> s2 = new ArrayList<>();
        Collections.addAll(s2, 4,1,5,2);
        System.out.println(graph.S2S(s1,s2));
        assertEquals(result, graph.S2S(s1,s2));
    }
}