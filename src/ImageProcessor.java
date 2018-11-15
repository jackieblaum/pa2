import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ImageProcessor {
    private int H,W;
    private Pixel M[][];

    public ImageProcessor(String FName) {
        File f = new File(FName);
        try {
            Scanner s = new Scanner(f);
            H = s.nextInt();
            W = s.nextInt();

            M = new Pixel[H][W];
            for (int i=0; i<H; i++) {
                for (int j=0; j<W; j++) {
                    M[i][j] = new Pixel(s.nextInt(),s.nextInt(),s.nextInt());
                }
            }
            reduce_by_one();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ArrayList<ArrayList<Integer>> getImportance() {
        ArrayList<ArrayList<Integer>> I = new ArrayList<>();
        for(int i = 0; i < H; i++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int j = 0; j < W; j++){
                row.add(Importance(i, j));
            }
            I.add(row);
        }
        return I;
    }

    void writeReduced(int k, String FName) {

    }

    private void reduce_by_one(){
        ArrayList<ArrayList<Integer>> imp = getImportance();
        WGraph wg = new WGraph(imp);
        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
        for(int j = 0; j < W; j++){
            starts.add(0);
            starts.add(j);
            ends.add(H-1);
            ends.add(j);
        }
        ArrayList<Integer> path = wg.S2S(starts, ends);
        System.out.println(path);
    }

    private int Importance(int i, int j){
        int ximp, yimp;
        int ilow, iup, jlow, jup;
        ilow = i == 0 ? H-1 : i-1;
        iup = i == H-1 ? 0 : i+1;
        jlow = j == 0 ? W-1 : j-1;
        jup = j == W-1 ? 0 : j+1;
        System.out.println("jlow, j: " + jlow + ", " + j);
        System.out.println("ilow, i: " + ilow + ", " + i);
        yimp = PDist(M[ilow][j], M[iup][j]);
        ximp = PDist(M[i][jlow], M[i][jup]);
        return ximp + yimp;
    }

    private int PDist(Pixel p1, Pixel p2){
        int rdist = (p1.R-p2.R) * (p1.R-p2.R);
        int gdist = (p1.G-p2.G) * (p1.G-p2.G);
        int bdist = (p1.B-p2.B) * (p1.B-p2.B);
        return rdist + gdist + bdist;
    }

   private class Pixel{
        int R,G,B;
        Pixel(int r, int g, int b){
            R = r;
            G = g;
            B = b;
        }
        public String toString(){
            return "(" + R + "," + G + "," + B + ")";
        }
        public boolean equals(Object obj){
            if(!(obj instanceof Pixel)) return false;
            return (((Pixel)obj).R == R)
                    && (((Pixel)obj).G == G)
                    && (((Pixel)obj).B == B);
        }
   }

}
