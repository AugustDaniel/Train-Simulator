package util.graph;

public class Edge {
    private int vertices;
    private int vertices2;

    public Edge(int vertices, int vertices2){
        this.vertices = vertices;
        this.vertices2 = vertices2;
    }

    public boolean equels(Object o){
        return vertices2 == ((Edge) o).vertices2 && vertices == ((Edge)o).vertices;
    }

}
