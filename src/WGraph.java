import groovy.ui.SystemOutputInterceptor;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class WGraph {

    // Using a list of edges to represent the graph
    private Edge edges[];
    private Vertex vertices[];
    private int numVertices, numEdges;
    private ArrayList<ArrayList<Integer>> Importance;

    public WGraph(String FName){
        File f = new File(FName);
        try {
            Scanner s = new Scanner(f);
            numVertices = s.nextInt();
            vertices = new Vertex[numVertices];
            numEdges = s.nextInt();
            edges = new Edge[numEdges];

            int edge_index = 0;
            int vertex_index = 0;
            while (edge_index < numEdges) {
                Vertex src = new Vertex(s.nextInt(),s.nextInt());
                Vertex dest = new Vertex(s.nextInt(),s.nextInt());
                boolean src_checked = false;
                boolean dest_checked = false;
                for (Vertex v : vertices) {
                    if(v != null) {
                        if (v.equals(src)) {
                            src_checked = true;
                            src = v;
                        }
                        if (v.equals(dest)) {
                            dest_checked = true;
                            dest = v;
                        }
                    }
                }
                if (!src_checked) {
                    vertices[vertex_index] = src;
                    vertex_index++;
                }
                if (!dest_checked) {
                    vertices[vertex_index] = dest;
                    vertex_index++;
                }
                int wt = s.nextInt();
                Edge e = new Edge(src,dest,wt);
                edges[edge_index] = e;
                edge_index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public WGraph(ArrayList<ArrayList<Integer>> matrix) {
        Importance = matrix;
        int W = matrix.get(0).size();
        int H = matrix.size();
        numVertices = H*W;
        numEdges = (H-1)*(3*(W-2)+4);
        vertices = new Vertex[numVertices];
        edges = new Edge[numEdges];

        int edge_index = 0;
        for(int j = 0; j < W; j++){
            edge_index = makeEdges(H, W, edge_index, 0, j);
        }
    }

    private int makeEdges(int H, int W, int edgeIndex, int i, int j){
        vertices[W*i + j] = new Vertex(i, j);
        if(i == H-1) return edgeIndex;
        int weight;
        if(j != 0){
            if (vertices[W*(i+1) + j-1] == null) {
                vertices[W * (i + 1) + j - 1] = new Vertex(i + 1, j - 1);
                edgeIndex = makeEdges(H, W, edgeIndex,i+1, j-1);
            }
            weight = Importance.get(i+1).get(j-1);
            if(i == 0) weight += Importance.get(i).get(j);
            edges[edgeIndex] = new Edge(vertices[W*i + j], vertices[W*(i+1) + j-1], weight);
            edgeIndex++;
        }
        if(vertices[W*(i+1) + j]==null) {
            vertices[W*(i+1) + j] = new Vertex(i+1,j);
            edgeIndex = makeEdges(H, W, edgeIndex, i+1, j);
        }
        weight = Importance.get(i+1).get(j);
        if(i == 0) weight += Importance.get(i).get(j);
        edges[edgeIndex] = new Edge(vertices[W*i + j], vertices[W*(i+1) + j], weight);
        edgeIndex++;
        if(j != W-1){
            if(vertices[W*(i+1) + j+1]==null) {
                vertices[W*(i+1) + j+1] = new Vertex(i+1,j+1);
                edgeIndex = makeEdges(H, W, edgeIndex, i+1, j+1);
            }
            weight = Importance.get(i+1).get(j+1);
            if(i == 0) weight += Importance.get(i).get(j);
            edges[edgeIndex] = new Edge(vertices[W*i + j], vertices[W*(i+1) + j+1], weight);
            edgeIndex++;
        }
        return edgeIndex;

    }

    private ArrayList<Integer> dijkstra(ArrayList<Integer> S1, ArrayList<Integer> S2){
        ArrayList<Vertex> starts = new ArrayList<>();
        ArrayList<Vertex> ends = new ArrayList<>();
        for(int i = 0; i < S1.size()-1; i+=2){
            for(Vertex v : vertices){
                if(v.x == S1.get(i) && v.y == S1.get(i+1)) {
                    starts.add(v);
                    break;
                }
            }
        }
        for(int i = 0; i < S2.size()-1; i+=2){
            for(Vertex v : vertices){
                if(v.x == S2.get(i) && v.y == S2.get(i+1)) {
                    ends.add(v);
                    break;
                }
            }
        }
        ArrayList<Vertex> V = new ArrayList<>(Arrays.asList(vertices));
        ArrayList<Edge> E = new ArrayList<>(Arrays.asList(edges));
        Vertex start = new Vertex(-1, -1);
        Vertex end = new Vertex(-2, -2);
        V.add(end);
        for(Vertex s : starts){
            E.add(new Edge(start, s, 0));
        }
        for(Vertex e : ends){
            E.add(new Edge(e, end, 0));
        }
        PriorityQ pq = new PriorityQ();
        for(Vertex v : V){
            v.dist = Integer.MAX_VALUE;
            v.parent = null;
            v.done = false;
            pq.add(v, v.dist);
        }
        start.dist = 0;
        pq.add(start, start.dist);
        V.add(start);

        while(!pq.isEmpty()){
            Vertex u = pq.extractMin();
            u.done = true;
            if(u.equals(end)){
                ArrayList<Integer> path = new ArrayList<>();
                Vertex add = u.parent;
                ArrayList<Edge> edge_path = new ArrayList<>();
                while(add != null && !add.equals(start)){
                    path.add(0, add.y);
                    path.add(0, add.x);
                    if(!add.equals(start)){
                        edge_path.add(new Edge(add.parent, add, 0));
                    }
                    add = add.parent;
                }
                return path;
            }
            for(Edge e : E){
                if(e.u.equals(u) && !e.v.done){
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


    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
        starts.add(ux);
        starts.add(uy);
        ends.add(vx);
        ends.add(vy);
        return dijkstra(starts, ends);
    }

    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
        ArrayList<Integer> starts = new ArrayList<>();
        starts.add(ux);
        starts.add(uy);
        return dijkstra(starts, S);
    }

    public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
        return dijkstra(S1, S2);
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
            if(index < 0) throw new HeapException("Vertex not in queue.");
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
        boolean done = false;
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

        @Override
        public String toString(){
            return "(" + x + "," + y + ")";
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
        @Override
        public String toString(){
            return "[" + u + "->" + wt + "->" + v + "]";
        }
    }

}
