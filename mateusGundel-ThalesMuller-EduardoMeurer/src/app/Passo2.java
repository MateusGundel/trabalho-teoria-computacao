package app;

import java.util.LinkedList;
import model.ConjuntoCadeiaFinita;
import model.InstRotuladaComp;
import model.Rotulo;

/**
 *
 * @author Mateus Gundel
 */
public class Passo2 {

    public LinkedList<InstRotuladaComp> lista;
    public static LinkedList<InstRotuladaComp> rotulosFora = new LinkedList<>();
    public static LinkedList<InstRotuladaComp> rotulosDentro = new LinkedList<>();
    public LinkedList<ConjuntoCadeiaFinita> listaCadeiaFinita = new LinkedList<>();

    public void gerar() {

        //armazena a posicao de parada
        int posParada = 0;

        // Primeiro conjunto da cadeia finita.
        ConjuntoCadeiaFinita cadeiaFinita = new ConjuntoCadeiaFinita("&", "A0");
        listaCadeiaFinita.add(cadeiaFinita);

        int contRotulo = 1; // contador da qtd de rótulos percorridos.

        // Percorre a lista de tras para frente, procurando a parada
        // e gera a lista de cadeia de conjuntos finitos.
        for (int i = lista.size() - 1; i >= 0; i--) {

            InstRotuladaComp instrucao = lista.get(i);

            // se posParada != 0, então já encontrou a parada e o resto dos
            // rótulos estão dentro.
            if (posParada != 0) {

                // Se o rótulo já existe na lista, ignora.
                if (verificaSeExiste(instrucao, rotulosDentro)) {
                    continue;
                }
                cadeiaFinita = new ConjuntoCadeiaFinita(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                        "A" + contRotulo);
                listaCadeiaFinita.add(cadeiaFinita);
                rotulosDentro.add(instrucao);

            } else {

                if (instrucao.getRotuloF() == Rotulo.PARADA || instrucao.getRotuloV() == Rotulo.PARADA) {
                    posParada = i;
                    cadeiaFinita = new ConjuntoCadeiaFinita(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                            "A" + contRotulo);
                    listaCadeiaFinita.add(cadeiaFinita);
                    rotulosDentro.add(instrucao);
                    contRotulo++;
                    continue;
                }

                cadeiaFinita = new ConjuntoCadeiaFinita(cadeiaFinita.getConjunto() + "," + instrucao.getIr(),
                        "A" + contRotulo);
                listaCadeiaFinita.add(cadeiaFinita);
                rotulosFora.add(instrucao);

            }

            contRotulo++;
        }

        // Último conjunto da cadeia finita.
        cadeiaFinita = new ConjuntoCadeiaFinita(cadeiaFinita.getConjunto(), "A" + contRotulo);
        listaCadeiaFinita.add(cadeiaFinita);

    }

    // Verifica se o rótulo já existe na lista.
    public boolean verificaSeExiste(InstRotuladaComp instrucao, LinkedList<InstRotuladaComp> lista) {
        for (int i = 0; i < lista.size(); i++) {
            InstRotuladaComp instrucaoLista = lista.get(i);
            if (instrucao.getOpV().equals(instrucaoLista.getOpV())
                    && instrucao.getIdOpV().equals(instrucaoLista.getIdOpV())
                    && instrucao.getOpF().equals(instrucaoLista.getOpF())
                    && instrucao.getIdOpF().equals(instrucaoLista.getIdOpF())) {
                return true;
            }
        }
        return false;
    }

    //Gera a saida em uma string
    public static String comoString(LinkedList<ConjuntoCadeiaFinita> lista) {
        StringBuilder texto = new StringBuilder();

        for (ConjuntoCadeiaFinita cadeiaFinita : lista) {
            texto.append(cadeiaFinita + "\n");
        }
        return texto.toString();
    }

    public static LinkedList<InstRotuladaComp> getRotulosFora() {
        return rotulosFora;
    }

    public static void setRotulosFora(LinkedList<InstRotuladaComp> rotulosFora) {
        Passo2.rotulosFora = rotulosFora;
    }

    public static LinkedList<InstRotuladaComp> getRotulosDentro() {
        return rotulosDentro;
    }

    public static void setRotulosDentro(LinkedList<InstRotuladaComp> rotulosDentro) {
        Passo2.rotulosDentro = rotulosDentro;
    }

    public LinkedList<ConjuntoCadeiaFinita> getListaCadeiaFinita() {
        return listaCadeiaFinita;
    }

    public void setListaCadeiaFinita(LinkedList<ConjuntoCadeiaFinita> listaCadeiaFinita) {
        this.listaCadeiaFinita = listaCadeiaFinita;
    }

    public Passo2(LinkedList<InstRotuladaComp> lista) {
        this.lista = lista;
    }

    public LinkedList<InstRotuladaComp> getLista() {
        return lista;
    }

    public void setLista(LinkedList<InstRotuladaComp> lista) {
        this.lista = lista;
    }

}
