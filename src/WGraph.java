import java.util.ArrayList;

public class WGraph {

    public WGraph(String FName){}

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){

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

        public void add(String s, int p){
            Pair node = new Pair(s,p);
            queue.add(node);
            //compare w parent, if smaller, switch, if bigger, stop
            int index = queue.size()-1;
            while(queue.get(getParent(index)).getV() > node.getV()) {
                swap(getParent(index),index);
                index = getParent(index);
            }
        }

        public String returnMin() throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            return queue.get(0).getS();
        }

        public String extractMin() throws HeapException{
            return extract(0);
        }

        public void remove(int i) throws HeapException{
            extract(i);
        }

        public void decrementPriority(int i, int k) throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            Pair pair = queue.remove(i);
            pair.setV(pair.getV() - k);
            add(pair.getS(), pair.getV());
        }

        public int[] priorityArray() throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            int[] rval = new int[queue.size()];
            for(int i = 0; i < queue.size(); i++){
                rval[i] = queue.get(i).getV();
            }
            return rval;
        }

        public int getKey(int i){
            return queue.get(i).getV();
        }

        public String getValue(int i){
            return queue.get(i).getS();
        }

        public boolean isEmpty(){
            return queue.size() == 0;
        }

        //Key, priority pair used in the queue
        private class Pair{
            private String s;
            private int v;

            Pair(String s, int v){
                this.s = s;
                this.v = v;
            }

            int getV() {return v;}
            String getS() {return s;}

            void setV(int newV) {v = newV;}
            void setS(String newS){s = newS;}

            @Override
            public String toString(){
                return "(" + s + "," + v + ")";
            }
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
        private String extract(int i) throws HeapException{
            if(queue.size() == 0) throw new HeapException("Heap is Empty");
            String rval = queue.get(i).getS();
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

            int val = queue.get(i).getV();
            int index = i;
            while( (lChild!=null && lChild.getV() < val)
                    || (rChild!=null && rChild.getV() < val)){
                if( (lChild!=null) && (rChild==null || lChild.getV() < rChild.getV()) ){
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
