package app;

import java.util.LinkedList;
import model.InstRotuladaComp;
import model.Rotulo;

/**
 *
 * @author Mateus Gundel
 */
public class Passo3 {

    public static LinkedList<InstRotuladaComp> rotulosFora;
    public static LinkedList<InstRotuladaComp> rotulosDentro;

    public Passo3(LinkedList<InstRotuladaComp> rotulosFora, LinkedList<InstRotuladaComp> rotulosDentro) {
        this.rotulosFora = rotulosFora;
        this.rotulosDentro = rotulosDentro;
    }

    public void gerar() {

        for (int i = 0; i < rotulosDentro.size(); i++) {

            InstRotuladaComp instrucaoDentro = rotulosDentro.get(i);

            for (int j = 0; j < rotulosFora.size(); j++) {

                InstRotuladaComp instrucaoFora = rotulosFora.get(j);

                if (instrucaoDentro.getOpV() == instrucaoFora.getOpV()) {
                    instrucaoDentro.setRotuloV(Rotulo.CICLO);
                    instrucaoDentro.setIdOpV("w");
                    instrucaoDentro.setOpV("ciclo");
                    rotulosDentro.set(i, instrucaoDentro);
                }
                if (instrucaoDentro.getOpF() == instrucaoFora.getOpF()) {
                    instrucaoDentro.setRotuloF(Rotulo.CICLO);
                    instrucaoDentro.setIdOpF("w");
                    instrucaoDentro.setOpF("ciclo");
                    rotulosDentro.set(i, instrucaoDentro);
                }

            }

        }

    }

    public static LinkedList<InstRotuladaComp> getRotulosFora() {
        return rotulosFora;
    }

    public static void setRotulosFora(LinkedList<InstRotuladaComp> rotulosFora) {
        Passo3.rotulosFora = rotulosFora;
    }

    public static LinkedList<InstRotuladaComp> getRotulosDentro() {
        return rotulosDentro;
    }

    public static void setRotulosDentro(LinkedList<InstRotuladaComp> rotulosDentro) {
        Passo3.rotulosDentro = rotulosDentro;
    }

}
