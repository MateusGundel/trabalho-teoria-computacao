package model;

/**
 * Mapeia as funções.
 *
 * @author Mateus Gundel
 */
public class Operacao {

    // operacao sendo realizada
    private Rotulo rotulo;

    // descricao
    private String operacao;

    private String desvio;

    public Operacao(Rotulo rotulo, String operacao, String desvio) {
        this.rotulo = rotulo;
        this.operacao = operacao;
        this.desvio = desvio;
    }

    @Override
    public String toString() {
        return "Operacao{" + "rotulo=" + rotulo + ", operacao=" + operacao + ", desvio=" + desvio + '}';
    }

    public Rotulo getRotulo() {
        return rotulo;
    }

    public void setRotulo(Rotulo rotulo) {
        this.rotulo = rotulo;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDesvio() {
        return desvio;
    }

    public void setDesvio(String desvio) {
        this.desvio = desvio;
    }

}
