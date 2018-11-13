import groovy.ui.SystemOutputInterceptor;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class WGraph {

    // Using a list of edges to represent the graph
    private Edge edges[];
    private Vertex vertices[];
    private int numVertices, numEdges;

    public WGraph(String FName){
        File f = new File(FName);
        try {
            Scanner s = new Scanner(f);
            numVertices = s.nextInt();
            vertices = new Vertex[numVertices];
            s.nextLine();
//               System.out.println(numVertices);
            numEdges = s.nextInt();
            edges = new Edge[numEdges];
            s.nextLine();
            System.out.println(numEdges);

            int edge_index = 0;
            int vertex_index = 0;
            while (s.hasNextLine()) {
                Vertex src = new Vertex(s.nextInt(),s.nextInt());
                Vertex dest = new Vertex(s.nextInt(),s.nextInt());
                boolean src_checked = false;
                boolean dest_checked = false;
                for(Vertex v : vertices) {
                    if (v.equals(src)) { src_checked = true; }
                    if (v.equals(dest)) { dest_checked = true; }
                }
                if (!src_checked) {
                    vertices[vertex_index] = src;
                    vertex_index++;
                }
                if (!dest_checked) {
                    vertices[vertex_index] = src;
                    vertex_index++;
                }
                int wt = s.nextInt();
                Edge e = new Edge(src,dest,wt);
                edges[edge_index] = e;
                edge_index++;
//                System.out.println(src.x() + "," + src.y() + "," +
//                        dest.x() + "," + dest.y() + "," + wt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
        Vertex start = new Vertex(ux, uy);
        Vertex end = new Vertex(vx, vy);
        PriorityQ pq = new PriorityQ();

        for(Vertex v : vertices){
            v.dist = Integer.MAX_VALUE;
            v.parent = null;
            if(!v.equals(start)) {pq.add(v, v.dist);}
        }
        start.dist = 0;
        pq.add(start, 0);

        while(!pq.isEmpty()){
            Vertex u = pq.extractMin();
            if(u.equals(end)){ //Found it!!
                ArrayList<Integer> rval = new ArrayList<>();
                Vertex add = u;
                while(add.parent != null){
                    rval.add(0, u.y);
                    rval.add(0, u.x);
                }
                return rval;
            }
            for(Edge e : edges){
                if(e.u.equals(u)){
                    int newdist = u.dist + e.wt;
                    if(newdist < e.v.dist){
                        e.v.dist = newdist;
                        e.v.parent = u;
                        pq.setPriority(e.v, newdist);
                    }
                }
            }
        }



        return null;
    }

    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
        Vertex u = new Vertex(ux, uy);
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

        public void setPriority(Vertex v, int k) throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            int index = -1;
            for(int i = 0; i < queue.size(); i++){
                if(queue.get(i).vertex().equals(v)) index = i;
            }
            if(index == < 0) throw new HeapException("Vertex not in queue.");
            Pair pair = queue.remove(index);
            pair.setPriority(k);
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
        Vertex parent = null;
        int dist = Integer.MAX_VALUE;
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
        int wt;
        Edge(Vertex source, Vertex dest, int weight){
            u = source;
            v = dest;
            wt = weight;
        }
        Vertex u(){ return u; }
        Vertex v(){ return v; }
        int wt() { return wt; }
    }

}
