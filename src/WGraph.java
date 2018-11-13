import groovy.ui.SystemOutputInterceptor;

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
                System.out.println(numVertices);
            }
            if (s.hasNextLine()) {
                int numEdges = s.nextInt();
                s.nextLine();
                System.out.println(numEdges);
            }
            while (s.hasNextLine()) {
                Vertex src = new Vertex(s.nextInt(),s.nextInt());
                Vertex dest = new Vertex(s.nextInt(),s.nextInt());
                int wt = s.nextInt();
                Edge e = new Edge(src,dest);
                System.out.println(src.x() + "," + src.y() + "," +
                        dest.x() + "," + dest.y() + "," + wt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
        Vertex start = new Vertex(ux, uy);
        Vertex end = new Vertex(vx, vy);
        return null;
    }

    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
        return null;
    }

    public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
        return null;
    }


    private class PriorityQ {
        private ArrayList<Pair> queue;

        public PriorityQ(){
            queue = new ArrayList<Pair>();
        }

        public void add(Vertex v, int p){
            Pair node = new Pair(v,p);
            queue.add(node);
            //compare w parent, if smaller, switch, if bigger, stop
            int index = queue.size()-1;
            while(queue.get(getParent(index)).priority() > node.priority()) {
                swap(getParent(index),index);
                index = getParent(index);
            }
        }

        public Vertex returnMin() throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            return queue.get(0).vertex();
        }

        public Vertex extractMin() throws HeapException{
            return extract(0);
        }

        public void remove(int i) throws HeapException{
            extract(i);
        }

        public void decrementPriority(int i, int k) throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            Pair pair = queue.remove(i);
            pair.setPriority(pair.priority() - k);
            add(pair.vertex(), pair.priority());
        }

        public boolean isEmpty(){
            return queue.size() == 0;
        }

        //Key, priority pair used in the queue
        private class Pair{
            private Vertex v;
            private int p;

            Pair(Vertex v, int p){
                this.v = v;
                this.p = p;
            }

            Vertex vertex() {return v;}
            int priority() {return p;}
            void setPriority(int p){ this.p = p; }
        }

        //Returns index of parent
        private int getParent(int index) {return (index-1)/2;}

        // This returns the index of the left child
        private int getChild(int index) {return 2*(index+1)-1;}

        //Swaps two elements in the queue
        private void swap(int i, int j) {
            Pair temp = queue.get(i);
            queue.set(i,queue.get(j));
            queue.set(j,temp);
        }

        //Removes the element at index i and returns the string.
        private Vertex extract(int i) throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            Vertex rval = queue.get(i).vertex();
            swap(i, queue.size()-1);
            queue.remove(queue.size()-1);
            if(i == queue.size() || queue.size()==0) return rval;
            Pair lChild, rChild;
            if(getChild(i) < queue.size()) {
                lChild = queue.get(getChild(i));
            }
            else lChild = null;

            if(getChild(i)+1 < queue.size()) {
                rChild = queue.get(getChild(i) + 1);
            }
            else rChild = null;

            int val = queue.get(i).priority();
            int index = i;
            while( (lChild!=null && lChild.priority() < val)
                    || (rChild!=null && rChild.priority() < val)){
                if( (lChild!=null) && (rChild==null || lChild.priority() < rChild.priority()) ){
                    swap(getChild(index), index);
                    index = getChild(index);
                }
                else{
                    swap(getChild(index)+1, index);
                    index = getChild(index)+1;
                }
                if(getChild(index) >= queue.size()) lChild = null;
                else {
                    lChild = queue.get(getChild(index));
                }
                if(getChild(index)+1 >= queue.size()) rChild = null;
                else {
                    rChild = queue.get(getChild(index) + 1);
                }
            }

            return rval;
        }

        private class HeapException extends RuntimeException{
            public HeapException(String message){
                super(message);
            }
        }
    }

    private class Vertex{
        int x, y;
        Vertex(int xcoord, int ycoord){
            x = xcoord;
            y = ycoord;
        }
        int x(){ return x; }
        int y(){ return y; }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Vertex)) return false;
            return (x == ((Vertex)obj).x) && (y == ((Vertex)obj).y);
        }
    }

    private class Edge{
        Vertex u, v;
        Edge(Vertex source, Vertex dest){
            u = source;
            v = dest;
        }
        Vertex u(){ return u; }
        Vertex v(){ return v; }
    }

}
