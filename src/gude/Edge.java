package gude;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

/**
 * Classe que representa a aresta de um grafo.
 *
 * @author Walace
 * @version 1.0
 */
public class Edge implements Serializable {
    /**
     * Identificador da aresta.
     */
    private int id;
    /**
     * Vértice de partida que define a aresta.
     */
    private Vertex alpha;
    /**
     * Vértice de chegada que define a aresta.
     */
    private Vertex omega;
    /**
     * Grafo que contém a aresta.
     */
    private Graph grafo;
    
    private float value;
    private Float feromonio;

   /**
    * Construtor chamado pela classe Graph.<br>
    * (uso interno)
    *
    * @param alpha id do vertice de partida da aresta
    * @param omega id do vertice de chegada da aresta
    * @param graph grafo que chamou o construtor.
    *
    * @see Graph#createEdge(int,int)
    */
    Edge( int alpha, int omega, Graph graph) {
        this.grafo = graph;
        this.id = graph.generateIdEdge();
        this.alpha = graph.getVertex(alpha);
        this.omega = graph.getVertex(omega);
        graph.insertEdgeInList(this.id, this);
        graph.getVertex(alpha).insertEdgeInList(this);
        graph.getVertex(omega).insertEdgeInList(this);
    }
    
    Edge( int alpha, int omega, Graph graph, Float value) {
        this.grafo = graph;
        this.id = graph.generateIdEdge();
        this.value = value;
        this.alpha = graph.getVertex(alpha);
        this.omega = graph.getVertex(omega);
        this.feromonio = 1f;
        graph.insertEdgeInList(this.id, this);
        graph.getVertex(alpha).insertEdgeInList(this);
        graph.getVertex(omega).insertEdgeInList(this);
    }
   /**
    * Destrutor chamado pela classe Graph.<br>z
    * (uso interno)
    *
    * @see Graph#removeEdge(int) 
    */
    void destroy(){
        grafo.getVertex(alpha.getId()).removeEdgeFromList(this.id);
        grafo.getVertex(omega.getId()).removeEdgeFromList(this.id);
        grafo.removeEdgeFromList(this.id);
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Edge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   /**
    * Dado um dos vértices que definem a aresta, retorna o vértice que
    * está na outra extremidade dela.
    *
    * @param id id de um dos vértices que definem a aresta.
    *
    * @return o vértice vizinho que está na outra extremidade da aresta,
    *         ou null se a aresta não incide no vértice id.
    */
    public Vertex neighbour(int id){
        if (id == this.alpha.getId())
            return this.omega;
        else if (id == this.omega.getId())
            return this.alpha;
        else return null;
    }

    /**
     * Testa se é uma aresta de loop, isto é, se (alpha == omega).
     * 
     * @return true se for uma aresta de loop e false se não for.
     */
    public boolean isLoop() {
        return getAlpha().getId() == getOmega().getId();
    }

   /**
    * Retorna o vértice de partida da aresta.
    *
    * @return o vértice alpha.
    *
    * @see #getOmega()
    */
    public Vertex getAlpha() {
        return alpha;
    }

   /**
    * Retorna o vértice de chegada da aresta.
    *
    * @return o vértice omega.
    *
    * @see #getAlpha()
    */
    public Vertex getOmega() {
        return omega;
    }

   /**
    * Retorna o grafo que contém a aresta.
    */
    public Graph getGraph() {
        return grafo;
    }

   /**
    * Retorna o identificador da aresta, que funciona como uma chave para ela.
    */
    public int getId() {
        return id;
    }

   /**
    * Define o vértice alpha da aresta.<br>
    * (uso interno)
    */
    void setAlpha(int alpha) {
        this.alpha = grafo.getVertex(alpha);
    }

   /**
    * Define o grafo que contém a aresta.<br>
    * (uso interno)
    */
    void setGraph(Graph grafo) {
        this.grafo = grafo;
    }

   /**
    * Define o identificador da aresta.<br>
    * (uso interno)
    */
    void setId(int id) {
        this.id = id;
    }

   /**
    * Define o vértice omega da aresta.<br>
    * (uso interno)
    */
    void setOmega(int omega) {
        this.omega = grafo.getVertex(omega);
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     * @return the feromonio
     */
    public Float getFeromonio() {
        return feromonio;
    }

    /**
     * @param feromonio the feromonio to set
     */
    public void setFeromonio(Float feromonio) {
        this.feromonio = feromonio;
    }

}
