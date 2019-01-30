/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import gude.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author walace
 */
public class ACMain {

    /**
     * @param args the command line arguments
     */
    public static int N; //QNT FORMIGAS PRIMEIRA FASE (DESCOBERTA)
    public static int F;  //QNT FORMIGAS SEGUNDA FASE  (REFINAMENTO)
    public static int T;  //QNT DE INTERAÇÕES (REFINAMENTO)
    /**
     * TAXA DE ATUALIZAÇÂO DO FEROMONIO
     */
    public static int Q;

    public final static int ALPHA = 3;
    public final static int BETA = 3;

    public static Graph grafo = new Graph();

    public static void main(String[] args) {

        try {
            grafo = Graph.loadXML("grafo01.xml");
        } catch (IOException ex) {
            Logger.getLogger(ACMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        int numVertices = grafo.getNumVertex();

        Vertex inicio = grafo.getVertex(1);
        Vertex fim = grafo.getVertex(numVertices);
        Boolean mod = true;

        N = 700;
        F = 180;
        T = 5;
        N = numVertices * 100;
        F = numVertices * 5;
        T = numVertices * 1;
        Q = (int) grafo.getEdge(numVertices).getValue() * numVertices * 1000;

        Float folga = 0.1f;

        if (numVertices >= 10) {
            if (numVertices >= 50) {
                folga = 0.001f;
                if (numVertices >= 100) {
                    folga = 0.00001f;
                    if (numVertices >= 500) {
                        folga = 0.0001f;
                        if (numVertices >= 1000) {
                            folga = 0.000001f;
                        }
                    }
                }
            }
        }

        Long tInicial = System.currentTimeMillis();

        /* PRIMEIRA ETAPA:  BUSCANDO CAMINHOS ALEATORIAMENTE    */
        Caminho melhor = new Caminho();
        System.out.println("1ª FASE:");
        for (int i = 0; i < N; i++) {

            // System.out.println((Float.valueOf(i)/Float.valueOf(N))*100+"%");
            Formiga formiga = new Formiga(inicio, fim, folga);

            while (!formiga.encontrouCaminho()) {

                formiga.buscaCaminhoInicio();

            }

            if (formiga.custoCaminho() < melhor.custoCaminho()) {
                grafo.recompensaRotaIncio(formiga.caminho);
                melhor = formiga.caminho;
            } else {

                grafo.atualizaFeromonio(formiga.caminho);
            }
        }

        System.out.println("1ª FASE Caminho Encontrado:" + melhor.convertToVertex().toString() + " \n"
                + "com custo = " + melhor.custoCaminho());

        if (mod) {

            Formiga fGulosa = new Formiga(inicio, fim, 1f);
            while (!fGulosa.encontrouCaminho()) {

                fGulosa.guloso();
            }

            if (fGulosa.custoCaminho() < melhor.custoCaminho()) {
                grafo.recompensaRota(fGulosa.caminho);
                melhor = fGulosa.caminho;
            }
            System.out.println(
                    "Caminho Guloso:" + fGulosa.caminho.convertToVertex().toString() + "\n"
                    + " com custo = " + fGulosa.custoCaminho());
        }

        /* SEGUNDA ETAPA:  REFINAMENTO DO CAMINHO */
        for (int t = 0; t < T; t++) {
            System.out.println(">>>>>       " + (Float.valueOf(t) / Float.valueOf(T)) * 100 + "%      <<<<<<<<");
            int convergencia = 0;

            for (int k = 0; k < F; k++) {

                Formiga formiga = new Formiga(inicio, fim, folga);

                while (!formiga.encontrouCaminho()) {

                    formiga.proximaCidade();
                }

                if (formiga.custoCaminho() <= melhor.custoCaminho()) {
                    System.out.println("T" + t + " F" + k + "   Caminho = " + formiga.caminho.custoCaminho() + " Errou = " + formiga.erros);
                    grafo.recompensaRota(formiga.caminho);
                    melhor = formiga.caminho;
                    convergencia++;
                } else {
                    grafo.atualizaFeromonio(formiga.caminho);
                }

            }
            grafo.evaporaFeromonio();
            if (convergencia == F) {  //Se Convergiu, força a saida
                t = T;
                System.out.println(">> Convergiu (100%)");
            }

        }
        System.out.println(
                "2º FASE Caminho Encontrado:" + melhor.convertToVertex().toString() + "\n"
                + " com custo = " + melhor.custoCaminho());

        System.out.println("Tempo de Execução: " + (System.currentTimeMillis() - tInicial) * 0.001 + " segundos");

        System.exit(0);

    }

}
