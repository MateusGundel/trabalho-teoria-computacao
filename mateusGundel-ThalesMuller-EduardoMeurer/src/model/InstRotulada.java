package model;

/**
 * Model para a instrucao rotulada
 *
 * @author Mateus Gundel
 */
public class InstRotulada {

    private int IR;

    private Operacao opTeste;

    private Operacao opVerdade;

    private Operacao opFalso;

    private Integer destinoOperacao;

    public InstRotulada(int IR, Operacao opTeste, Operacao opVerdade, Operacao opFalso) {
        this.IR = IR;
        this.opTeste = opTeste;
        this.opVerdade = opVerdade;
        this.opFalso = opFalso;
    }

    public InstRotulada(int IR, Operacao opTeste, Operacao opVerdade, Operacao opFalso, Integer destinoOperacao) {
        this.IR = IR;
        this.opTeste = opTeste;
        this.opVerdade = opVerdade;
        this.opFalso = opFalso;
        this.destinoOperacao = destinoOperacao;
    }

    public Integer getDestinoOperacao() {
        return destinoOperacao;
    }

    public void setDestinoOperacao(Integer destinoOperacao) {
        this.destinoOperacao = destinoOperacao;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public Boolean verificado = new Boolean(false);

    @Override
    public String toString() {
        return "IRComposta{" + "IR=" + IR + ", opTeste=" + opTeste + ", opVerdade=" + opVerdade + ", opFalso=" + opFalso + '}';
    }

    public int getIR() {
        return IR;
    }

    public void setIR(int IR) {
        this.IR = IR;
    }

    public Operacao getOpTeste() {
        return opTeste;
    }

    public void setOpTeste(Operacao opTeste) {
        this.opTeste = opTeste;
    }

    public Operacao getOpVerdade() {
        return opVerdade;
    }

    public void setOpVerdade(Operacao opVerdade) {
        this.opVerdade = opVerdade;
    }

    public Operacao getOpFalso() {
        return opFalso;
    }

    public void setOpFalso(Operacao opFalso) {
        this.opFalso = opFalso;
    }

}
