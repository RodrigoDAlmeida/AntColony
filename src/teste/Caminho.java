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

/**
 *
 * @author Rodrigo
 */
public class Caminho {
    
    
    List<Edge> rota;

    public List<Edge> getCaminho() {
        return rota;
    }

    public void setCaminho(List<Edge> caminho) {
        this.rota = caminho;
    }

    public Caminho() {
        
        this.rota = new ArrayList<Edge>();
    }  
    
     public Float custoCaminho(){
        
        Float custo = 0f;
        
        for (Edge c : rota) {
            
            custo += c.getValue();
        }
           
        if(custo == 0){
            
            return Float.MAX_VALUE;
        }else{
            
             return custo;
        }     
    }
     
     public int size(){
         
         return rota.size();
     }
     
     public List<Vertex> convertToVertex(){
         
         List<Vertex> vertices = new ArrayList<>();
         
         for (int i = 0; i < rota.size(); i++) {
             
             if(vertices.contains(rota.get(i).getOmega())){
                 vertices.add(rota.get(i).getAlpha());
             }else{
                vertices.add(rota.get(i).getOmega());
             }
             
             
             
         }          
         return vertices;
     }
  
}
