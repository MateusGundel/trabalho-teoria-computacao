package model;

/**
 *
 * @author Mateus Gundel
 */
public class ConjuntoCadeiaFinita {

    public String rotulo;
    public String conjunto;

    public ConjuntoCadeiaFinita(String conjunto, String rotulo) {
        this.conjunto = conjunto;
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return this.rotulo + ":{" + this.conjunto + "}";
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getConjunto() {
        return conjunto;
    }

    public void setConjunto(String conjunto) {
        this.conjunto = conjunto;
    }

}
