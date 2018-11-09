import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class WGraph {

    public WGraph(String FName){
        File f = new File(FName);

        try {
            Scanner s = new Scanner(f);
            if (s.hasNextLine()) {
                int numVertices = s.nextInt();
                s.nextLine();
            }
            if (s.hasNextLine()) {
                int numEdges = s.nextInt();
                s.nextLine();
            }
            while (s.hasNextLine()) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
        return null;
    }

    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
        return null;
    }

    public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
        return null;
    }


}
