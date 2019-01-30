package gude;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;


/**
 * Classe que representa o vértice de um grafo.
 *
 * @author Walace
 * @version 1.0
 */
public class Vertex implements Serializable {
    /**
     * Identificador do vértice.
     */
    private int id;
    /**
     * Grau do vértice.
     */
    private int degree;
    /**
     * Grafo que contém o vértice.
     */
    private Graph graph;
    /**
     * Armazena as arestas que incidem no vértice.
     */
    private TreeMap<Integer, Edge> edgeList;


   /**
    * Construtor chamado pela classe Graph.<br>
    * (uso interno)
    *
    * @param graph grafo que chamou o construtor.
    *
    * @see Graph#createVertex()
    */
    Vertex(Graph graph) {
        this.graph = graph;
        this.id = graph.generateIdVertex();
        this.degree = 0;
        edgeList = new TreeMap();
        graph.insertVertexInList(this.id, this);
    }

   /**
    * Destrutor chamado pela classe Graph.<br>
    * (uso interno)
    *
    * @see Graph#removeVertex(int)
    */
    void destroy() {
        Iterator it = edgeList.navigableKeySet().iterator();
        while(it.hasNext()){
            ((Edge)it.next()).destroy();
        }
        graph.removeVertexFromList(this.id);
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Vertex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   /**
    * Insere uma aresta na lista de arestas incidentes no vértice.<br>
    * (uso interno)
    *
    * @param aresta aresta que será inserida na lista.
    */
    void insertEdgeInList(Edge aresta) {
        this.degree++;
        edgeList.put(aresta.getId(),aresta);
    }
   
   /**
    * Remove uma aresta da lista de arestas incidentes no vértice.<br>
    * (uso interno)
    *
    * @param id id da aresta que sera removida da lista.
    */
    void removeEdgeFromList(int id) {
        edgeList.remove(id);
        this.degree--;
    }

   /**
    * Retorna a estrela de arestas incidentes no vértice, considerando um
    * grafo não direcionado.
    *
    * @return arrayList contendo as arestas que incidem no vértice.
    * @see #getArrayListOfEdgesIn()
    * @see #getArrayListOfEdgesOut()
    */
    public ArrayList<Edge> getArrayListOfEdges() {
        ArrayList<Edge> estrela = new ArrayList<Edge>();
        for(Edge a = firstEdge(); a != null; a = nextEdge(a.getId()))
            estrela.add(a);
        return estrela;
    }
   
   /**
    * Retorna a estrela de arestas de entrada incidentes no vértice.
    *
    * @return arrayList contendo as arestas de entrada que incidem no vértice.
    * @see #getArrayListOfEdges()
    * @see #getArrayListOfEdgesOut()
    */
    public ArrayList<Edge> getArrayListOfEdgesIn() {
        ArrayList<Edge> estrela = new ArrayList<Edge>();
        for(Edge a = firstIn(); a != null; a = nextIn(a.getId()))
            estrela.add(a);
        return estrela;
    }

   /**
    * Retorna a estrela de arestas de saída incidentes no vértice.
    *
    * @return arrayList contendo as arestas de saída que incidem no vértice.
    * @see #getArrayListOfEdges()
    * @see #getArrayListOfEdgesIn()
    */
    public ArrayList<Edge> getArrayListOfEdgesOut() {
        ArrayList<Edge> estrela = new ArrayList<Edge>();
        for(Edge a = firstOut(); a != null; a = nextOut(a.getId()))
            estrela.add(a);
        return estrela;
    }

   /**
    * Retorna a primeira aresta incidente no vértice, considerando um grafo
    * não direcionado.
    *
    * @return primeira aresta incidente ou null se não existir nenhuma.
    * @see #nextEdge(int)
    * @see #firstIn()
    * @see #firstOut()
    */
    public Edge firstEdge() {
        if (edgeList.size() == 0)
           return null;
        else {
           int idA = edgeList.firstKey();
           return edgeList.get(idA);
        }
    }

   /**
    * Retorna a próxima aresta incidente no vértice, depois da aresta com
    * chave id, considerando um grafo não direcionado,
    *
    * @param id identificador de aresta.
    * @return próxima aresta incidente ou null se não existir mais nenhuma.
    * @see #firstEdge()
    */
    public Edge nextEdge(int id) {
        if (!edgeList.containsKey(id) || edgeList.lastKey() == id)
            return null;
        else {
            SortedMap<Integer,Edge> map = edgeList.tailMap(id+1);
            int novoId = map.firstKey();
            return edgeList.get(novoId);
        }
    }

   /**
    * Retorna a primeira aresta de entrada incidente no vértice.
    *
    * @return primeira aresta de entrada ou null se não existir nenhuma.
    * @see #nextIn(int)
    * @see #firstOut()
    * @see #firstEdge()
    */
    public Edge firstIn() {
        for (Edge a = firstEdge(); a != null; a = nextEdge(a.getId())) {
            if (a.getOmega().getId() == id)
               return a;
        }
        return null;
    }

   /**
    * Retorna a primeira aresta de saída incidente no vértice.
    *
    * @return primeira aresta de saída ou null se não existir nenhuma.
    * @see #nextOut(int)
    * @see #firstIn()
    * @see #firstEdge()
    */
    public Edge firstOut() {
        for (Edge a = firstEdge(); a != null; a = nextEdge(a.getId())) {
            if (a.getAlpha().getId() == id)
               return a;
        }
        return null;
    }

   /**
    * Retorna a próxima aresta de entrada incidente no vértice, depois da
    * aresta com chave idA.
    *
    * @param idA identificador de aresta.
    * @return próxima aresta de entrada ou null se não existir mais nenhuma.
    * @see #firstIn()
    */
    public Edge nextIn(int idA) {
        for (Edge a = nextEdge(idA); a != null; a = nextEdge(a.getId())) {
            if (a.getOmega().getId() == id)
               return a;
        }
        return null;
    }

   /**
    * Retorna a próxima aresta de saída incidente no vértice, depois da
    * aresta com chave idA.
    *
    * @param idA identificador de aresta.
    * @return próxima aresta de saída ou null se não existir mais nenhuma.
    * @see #firstOut()
    */
    public Edge nextOut(int idA) {
        for (Edge a = nextEdge(idA); a != null; a = nextEdge(a.getId())) {
            if (a.getAlpha().getId() == id)
               return a;
        }
        return null;
    }

   /**
    * Retorna o grafo que contém o vértice.
    */
    public Graph getGraph() {
        return graph;
    }

   /**
    * Retorna o grau do vértice. Considerando que cada aresta de loop
    * acrescenta +2 ao grau do vértice.
    */
    public int getDegree() {
        return degree;
    }

   /**
    * Retorna o identificador do vértice, que funciona como uma chave para ele.
    */
    public int getId() {
        return id;
    }

   /**
    * Retorna a coleção de arestas incidentes no vértice.<br>
    * (uso interno)
    */
    TreeMap<Integer, Edge> getEdgeList() {
        return edgeList;
    }

   /**
    * Define o grafo que contém o vértice.<br>
    * (uso interno)
    */
    void setGraph(Graph grafo) {
        this.graph = grafo;
    }

   /**
    * Define o grau do vértice.<br>
    * (uso interno)
    */
    void setDegree(int grau) {
        this.degree = grau;
    }

   /**
    * Define o identificador do vértice.<br>
    * (uso interno)
    */
    void setId(int id) {
        this.id = id;
    }

   /**
    * Define a coleção de arestas que incidem no vértice.<br>
    * (uso interno)
    */
    void setEdgeList(TreeMap<Integer, Edge> listaArestas) {
        this.edgeList = listaArestas;
    }
    @Override
    public String toString(){
        
        return " "+this.id;
    }
}
