/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import gude.Edge;
import gude.Vertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rodrigo
 */
public class Formiga {



    Vertex fim;  
    Vertex atual;
   
    int erros; 
    Caminho caminho;
    
    List<Vertex> visitados;
    List<Vertex> restantes;  
    Float folga;
    
    Random rnd = new Random();
   
   
    public Formiga(Vertex inicio, Vertex fim, Float folga) {

   
        this.fim = fim;
        this.atual = inicio;
        this.caminho = new Caminho();
        this.folga = folga;
        this.visitados = new ArrayList<Vertex>();
        this.restantes = ACMain.grafo.getArrayListOfVertexes();
        
        visitados.add(inicio);
        restantes.remove(inicio);
        
        erros = 0;

        
    }

    public Boolean encontrouCaminho() {
        
        if (restantes.isEmpty()){
            
            if(visitados.get(caminho.rota.size()) != fim){
                
                Edge aresta = ACMain.grafo.getEdge(atual.getId(), fim.getId());
                caminho.rota.add(aresta);

            }
                return true;
        }
        
        return false;

    }
    
    public Float custoCaminho(){
        
      return this.caminho.custoCaminho();
    }
    
    
    public void buscaCaminhoInicio(){
        
        
        int numCidades = restantes.size();
        int indiceProximaCidade = rnd.nextInt(numCidades);  //Escolhe a proxima cidade aleatoriamente
        Vertex cidade = restantes.get(indiceProximaCidade);
       
        Edge aresta = ACMain.grafo.getEdge(atual.getId(), cidade.getId());
        
        
                atual = cidade;     //Seleciona a cidade
                restantes.remove(cidade);
                visitados.add(cidade);
                caminho.rota.add(aresta);
        

    }
    

    public void proximaCidade(){
       
        Float prob;
        Float feromonioRestante = CalculaFeromonioRestante();
        
        for (Vertex cidade : restantes) {
               
            Edge aresta = ACMain.grafo.getEdge(atual.getId(), cidade.getId());
            
            prob =  (aresta.getFeromonio() * (1/aresta.getValue()))/feromonioRestante * (1/aresta.getValue());
            
             Float random = rnd.nextFloat()*folga;
           //  System.out.println(prob +"     X       "+ random);
     
            if (prob > random){  
                atual = cidade;     //Seleciona a cidade
                restantes.remove(cidade);
                visitados.add(cidade);
                caminho.rota.add(aresta);
                
                return;
            }
            
        }
        
        this.erros++;
      this.folga = folga/1.6f;
      
    }
    
    public void guloso(){
        
        Float menorA = Float.MAX_VALUE;
        int idMenor = 0;
        
       for (Vertex cidade : restantes) {
               
            Edge aresta = ACMain.grafo.getEdge(atual.getId(), cidade.getId());
            
            if(aresta.getValue() < menorA){
                menorA = aresta.getValue();
                idMenor = cidade.getId();
            }
            
            
       }
                Vertex cidade = ACMain.grafo.getVertex(idMenor);
                Edge aresta = ACMain.grafo.getEdge(atual.getId(), cidade.getId());
                atual = cidade;     //Seleciona a cidade
                restantes.remove(cidade);
                visitados.add(cidade);
                caminho.rota.add(aresta);
        
    }
    
    
    private Float CalculaFeromonioRestante(){
        
        Float feromonio = 0f;
        
        for (Vertex cidade : restantes) {
            
            Edge aresta = ACMain.grafo.getEdge(atual.getId(), cidade.getId());
            feromonio += aresta.getFeromonio();
        }
        
        return feromonio;
    }
    
    
    

}
