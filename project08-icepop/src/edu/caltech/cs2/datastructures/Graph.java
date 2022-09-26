package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.*;

public class Graph<V, E> extends IGraph<V, E> {

    private IDictionary<V, IDictionary<V, E>> data;

    public Graph(){
        this.data = new ChainingHashDictionary<>(MoveToFrontDictionary::new);
    }

    @Override
    public boolean addVertex(V vertex) {
        if (this.data.containsKey(vertex)){
            return false;
        }
        this.data.put(vertex, new ChainingHashDictionary<>(MoveToFrontDictionary::new));
        return true;
    }

    @Override
    public boolean addEdge(V src, V dest, E e) {
        if (!this.data.containsKey(src) || !this.data.containsKey(dest)){
            throw new IllegalArgumentException("Illegal Edge");
        }
        IDictionary<V, E> edges = this.data.get(src);
        if (edges.containsKey(dest)){
            edges.put(dest, e);
            return false;
        }
        edges.put(dest, e);
        return true;
    }

    @Override
    public boolean addUndirectedEdge(V n1, V n2, E e) {
        if (!this.data.containsKey(n1) || !this.data.containsKey(n2)){
            throw new IllegalArgumentException("Illegal Edge");
        }
        IDictionary<V, E> edge1 = this.data.get(n1);
        IDictionary<V, E> edge2 = this.data.get(n2);
        if (edge1.containsKey(n2) || edge2.containsKey(n1)){
            edge1.put(n2, e);
            edge2.put(n1, e);
            return false;
        }
        edge1.put(n2, e);
        edge2.put(n1, e);
        return true;
    }

    @Override
    public boolean removeEdge(V src, V dest) {
        if (!this.data.containsKey(src) || !this.data.containsKey(dest)){
            throw new IllegalArgumentException("Illegal Edge");
        }
        IDictionary<V, E> edges = this.data.get(src);
        if (!edges.containsKey(dest)){
            return false;
        }
        edges.remove(dest);
        return true;
    }

    @Override
    public ISet<V> vertices() {
        return this.data.keySet();
    }

    @Override
    public E adjacent(V i, V j) {
        IDictionary<V, E> edges = this.data.get(i);
        if (edges == null){
            throw new IllegalArgumentException("Illegal Edge");
        }
        if (edges.containsKey(j)) {
            return edges.get(j);
        }
        return null;
    }

    @Override
    public ISet<V> neighbors(V vertex) {
        if (!this.data.containsKey(vertex)){
            throw new IllegalArgumentException("Illegal Edge");
        }
        return this.data.get(vertex).keySet();
    }
}