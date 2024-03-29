package app;

//import static app.Passo1.contadorRotuloComposto;
//import static app.Passo1.indiceInstrucaoEmTeste;
import controller.AppController;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import model.AppModel;
import model.InstRotuladaComp;
import model.InstRotulada;
import model.Operacao;
import model.Rotulo;

/**
 *
 * @author Mateus Gundel
 */
public class Processa {

    AppController controller;

    // Palavras do programa 1
    String[] words1;
    String[] words2;

    // Lista com os itens removidos das Instrucoes Rotuladas.
    public static LinkedList<InstRotulada> itensRemovidos = new LinkedList<>();

    public Processa(AppController controller) {
        this.controller = controller;
    }

    /**
     * Se processou com sucesso: retorna true. Ocorreu algum erro: retorna
     * false.
     *
     */
    public Boolean processar() throws Exception {
        StringProperty programa1 = controller.programaUmProperty;
        StringProperty programa2 = controller.programaDoisProperty;

        LinkedList<InstRotulada> instrucoes1 = new LinkedList<>();
        LinkedList<InstRotulada> instrucoes2 = new LinkedList<>();

        LinkedList<InstRotuladaComp> lista1;
        LinkedList<InstRotuladaComp> lista2;

        Passo1.contadorRotuloComposto = 1;

        Passo2.rotulosFora = new LinkedList<>();
        Passo2.rotulosDentro = new LinkedList<>();

        try {

            // Quebra a string no vetor de palavras Programa 1.
            words1 = toWords(programa1.get());
            words2 = toWords(programa2.get());

            // Pega instruções Programa 1.
            instrucoes1 = getInstrucoes(words1);
            instrucoes2 = getInstrucoes(words2);

            // Passo1 Programa 1
            Passo1 p1Passo1 = new Passo1(instrucoes1);
            lista1 = p1Passo1.gerar();
            Platform.runLater(() -> {
                controller.resultadoPasso1Programa1.setText(p1Passo1.comoString(lista1));

                // exibir o último elemento como ciclo quando tiver.
                if (p1Passo1.temCiclo) {
                    controller.resultadoPasso1Programa1.appendText("w:(ciclo,w),(ciclo,w)");
                }
            });

            // Passo1 Programa 2
            int valor = Passo1.contadorRotuloComposto - 1;

            Passo1 p2Passo1 = new Passo1(instrucoes2);
            lista2 = p2Passo1.gerar();

            // Ajusta a numeracao dos rotulos do segundo programa.
            p2Passo1.incrementaRotulos(valor);

            Platform.runLater(() -> {
                controller.resultadoPasso1Programa2.setText(p2Passo1.comoString(lista2));

                // Tratamento para exibir o último elemento como ciclo quando tiver.
                if (p2Passo1.temCiclo) {
                    controller.resultadoPasso1Programa2.appendText("w:(ciclo,w),(ciclo,w)");
                }
            });

            // Passo2 Programa 1
            //  System.out.println("app.ProcessaDados.processar() " + lista1);
            //   System.out.println("app.ProcessaDados.processar() " + lista2);
            Passo2 p1Passo2 = new Passo2(lista1);
            p1Passo2.gerar();
            Platform.runLater(() -> {
                controller.resultadoPasso2Programa1.setText(p1Passo2.comoString(p1Passo2.getListaCadeiaFinita()));
            });

            Thread.sleep(100);

            // Passo2 Programa 2
            Passo2 p2Passo2 = new Passo2(lista2);
            p2Passo2.gerar();
            Platform.runLater(() -> {
                controller.resultadoPasso2Programa2.setText(p2Passo2.comoString(p2Passo2.getListaCadeiaFinita()));
            });

            // Passo3 Programa 1
            if (p1Passo2.getRotulosFora().size() >= 1) {
                Passo3 p1Passo3 = new Passo3(p1Passo2.getRotulosFora(), p1Passo2.getRotulosDentro());
                p1Passo3.gerar();
                Platform.runLater(() -> {
                    controller.resultadoPasso3Programa1.setText(p1Passo1.comoString(p1Passo2.getRotulosDentro()));
                    controller.tabPasso3.disableProperty().set(false);
                });
            } else {
                Platform.runLater(() -> {
                    controller.resultadoPasso3Programa1.setText("");
                    controller.tabPasso3.disableProperty().set(true);
                });
            }

            // Passo3 Programa 2
            if (p2Passo2.getRotulosFora().size() >= 1) {
                Passo3 p2Passo3 = new Passo3(p2Passo2.getRotulosFora(), p2Passo2.getRotulosDentro());
                p2Passo3.gerar();
                Platform.runLater(() -> {
                    controller.resultadoPasso3Programa2.setText(p1Passo1.comoString(p2Passo2.getRotulosDentro()));
                    controller.tabPasso3.disableProperty().set(false);
                    controller.tabPasso3.setStyle("-fx-background-color: white");
                });
            } else {
                Platform.runLater(() -> {
                    controller.resultadoPasso3Programa1.setText("");

                });
            }

            //passo4
            Passo4 pPasso4 = new Passo4(lista1, lista2);
            String equivalente = pPasso4.gerar();

            Platform.runLater(() -> {
                controller.resultadoPasso4.setText(equivalente);
            });
        } catch (Exception ex) {
            Platform.runLater(() -> {
                controller.errorMessage.set("Ocorreu um erro. Por favor, reinicie o programa!");
            });
        }

        return true;
    }

    /**
     * Identifica operações.
     *
     * @return Rotulo
     */
    private Rotulo getRotulo(String word) {
        if (word.equals("Faca")) {
            return Rotulo.OPERACAO_NORMAL;

        } else if (word.equals("va_para")) {
            return Rotulo.DESVIO;

        } else if (word.equals("Se")) {
            return Rotulo.TESTE;

        } else if (word.equals("entao")) {
            return Rotulo.OPERACAO_VERDADEIRO;

        } else if (word.equals("senao")) {
            return Rotulo.OPERACAO_FALSO;
//            No minimo 1 até 5 digitos numéricos ou qualquer letra maiuscula.
        } else if (word.matches("^\\d{1,5}|[A-Z]{1}+$")) {
            return Rotulo.OPERANDO;

        } else {
            return Rotulo.NENHUM;
        }

    }

    /**
     * Percorre os elementos ajustando os indices trocados por causa das
     * remocoes.
     *
     * @param lista
     * @return
     */
    public static LinkedList<InstRotuladaComp> trataRemovidos(LinkedList<InstRotuladaComp> lista) {

        LinkedList<Integer> removidos = new LinkedList<>();

        for (int i = 1; i <= lista.size(); i++) {
            InstRotuladaComp instrucao = lista.get(i - 1);
            int ir = instrucao.getIr();

            // Identifica se é um rótulo que foi removido.
            if (!(i == ir && ir != ir + 1)) {
                removidos.add(ir - 1);
            }
        }

        if (AppModel.LOG_SAIDA_ATIVO) {
            System.out.println("\nInstrucoes removidas: ");
            for (Integer x : removidos) {
                System.out.println(x + " removido.");
            }
        }

        LinkedList<InstRotuladaComp> novaLista = new LinkedList<>();

        for (int i : removidos) {
            String indAnt = String.valueOf(i + 1);
            for (InstRotuladaComp instrucao : lista) {

                if (instrucao.getIr() == Integer.valueOf(indAnt)) {
                    instrucao.setIr(i);
                }
                if (instrucao.getRotuloV() == Rotulo.OPERANDO) {
                    if (instrucao.getIdOpV().matches("\\d{1,5}")) {
                        if (instrucao.getIdOpF().equals(indAnt)) {
                            instrucao.setIdOpV(String.valueOf(i));
                        }
                    }
                }
                if (instrucao.getRotuloF() == Rotulo.OPERANDO) {
                    if (instrucao.getIdOpF().matches("\\d{1,5}")) {
                        if (instrucao.getIdOpF().equals(indAnt)) {
                            instrucao.setIdOpF(String.valueOf(i));
                        }
                    }
                }

                novaLista.add(instrucao);

            }
        }

        return lista;
    }

    /**
     * Recebe o vetor bruto com as palavras. Faz o tratamento e retorna as
     * instruções já identificadas.
     *
     * @param words
     * @return LinkedList<IRComposta>
     * @throws RuntimeException
     */
    private LinkedList<InstRotulada> getInstrucoes(String[] words) throws RuntimeException {

        LinkedList<InstRotulada> instrucoes = new LinkedList<>();

        // variável aux para salvar o operando.
        String operando;

        // Adiciona a primeira instrucao na lista como PARTIDA.
        instrucoes.add(new InstRotulada(0, new Operacao(Rotulo.PARTIDA, null, "1"), new Operacao(Rotulo.OPERANDO, null, "1"), new Operacao(Rotulo.OPERANDO, null, "1")));

        if (AppModel.LOG_SAIDA_ATIVO) {
            System.out.println("\nInstruções Monolíticas Geradas:");
            System.out.println(instrucoes.get(0));
        }

        // Contador da instrução rotulada.
        int ir = 1;

        final Iterator<String> iterator = Arrays.asList(words).iterator();

        while (iterator.hasNext()) {
            String word = iterator.next();

            // Tipo do rótulo.
            Rotulo rotulo;

            // Operação da IR caso for teste.
            Operacao opTeste = null;

            // Operação da IR caso verdade.
            Operacao opVerdade = null;

            // Operação da IR caso falso.
            Operacao opFalse = null;

            rotulo = getRotulo(word);

            // Enquanto houverem instruções para extrair.
            while (rotulo != Rotulo.NENHUM) {

                if (rotulo == Rotulo.OPERACAO_NORMAL) {
                    // Se for uma operação, extrai a próxima String, que é o operando.
                    word = iterator.next();
                    rotulo = getRotulo(word);

                    // Valida se o novo valor extraido é um operando
                    // e adiciona na IR.
                    if (rotulo == Rotulo.OPERANDO) {

                        operando = word;

                        // Extrai a próxima String, que é o verdadeiro.
                        word = iterator.next();
                        rotulo = getRotulo(word);

                        // O próximo deve ser o desvio.
                        // Então tenho uma operação do tipo: Faça F va_para 2
                        if (rotulo == Rotulo.DESVIO) {

                            opTeste = new Operacao(Rotulo.NENHUM, null, null);

                            // Extrai a operação de desvio.
                            word = iterator.next();
                            rotulo = getRotulo(word);

                            // duplico minhas operações.
                            opVerdade = new Operacao(rotulo, operando, word);
                            opFalse = new Operacao(rotulo, operando, word);
                        }
                    }

                } else if (rotulo == Rotulo.TESTE) {

                    // Se for um teste, extrai a próxima String, que é o teste.
                    word = iterator.next();
                    rotulo = getRotulo(word);

                    operando = word;

                    opTeste = new Operacao(Rotulo.TESTE, word, null);

                    // Extrai a próxima String, que é o verdadeiro.
                    word = iterator.next();
                    rotulo = getRotulo(word);

                    // Válida se o obtido foi o verdadeiro
                    if (rotulo == Rotulo.OPERACAO_VERDADEIRO) {

                        // Então extrai a condição verdadeira.
                        word = iterator.next();
                        rotulo = getRotulo(word);

                        // É esperada uma operação, então valida se está correto.
                        if (rotulo == Rotulo.DESVIO) {

                            // Então extrai a condição verdadeira.
                            word = iterator.next();
                            rotulo = getRotulo(word);

                            // Se é um operando, então está tudo correto até aqui.
                            if (rotulo == Rotulo.OPERANDO) {
                                opVerdade = new Operacao(rotulo, null, word);
                            }

                        }
                    }

                    // Extrai a operação caso falso.
                    word = iterator.next();
                    rotulo = getRotulo(word);

                    // Valida se é mesmo falso.
                    if (rotulo == Rotulo.OPERACAO_FALSO) {

                        // Extrai a operação caso falso.
                        word = iterator.next();
                        rotulo = getRotulo(word);

                        // É esperada uma operação, então valida se está correto.
                        if (rotulo == Rotulo.DESVIO) {

                            // Então extrai a condição falsa.
                            word = iterator.next();
                            rotulo = getRotulo(word);

                            // Se é um operando, então está tudo correto até aqui.
                            if (rotulo == Rotulo.OPERANDO) {
                                opFalse = new Operacao(rotulo, null, word);
                            }

                        }

                    }

                }

                if (opTeste == null || opVerdade == null || opFalse == null) {
                    throw new RuntimeException("Erro ao identificar as operações. "
                            + "Foi encontrada uma entrada inválida.");
                }

                // Quando é operação, passa o parametro de destino operação.
                // Salva-se pq os rotulos sao reordenados, mas a sequencia nao deve ser
                InstRotulada instrucao = new InstRotulada(ir, opTeste, opVerdade, opFalse,
                        Integer.valueOf(opVerdade.getDesvio()));

                if (iterator.hasNext()) {

                    word = iterator.next();
                    rotulo = getRotulo(word);
                } else {
                    rotulo = Rotulo.NENHUM;
                }

                if (AppModel.LOG_SAIDA_ATIVO) {
                    System.out.println(instrucao);
                }

                // Adiciona a nova instrucao na lista.
                instrucoes.add(instrucao);

                /*
            Valida se contem 1 a 5 digitos numéricos seguido de :, ou seja,
            está trocando de rótulo.
                 */
                if (word.matches("\\d{1,5}:")) {
                    // pula para o próximo rótulo.
                    ir++;
                }

            }

        }
        if (AppModel.LOG_SAIDA_ATIVO) {
            System.out.println("Total de rótulos: " + ir);
        }
        return instrucoes;
    }

    /**
     * Converte a expreção regular do programa para palavras e as retorna em um
     * vetor de string.
     *
     * @param programa
     * @return
     */
    public String[] toWords(String programa) {

        // Primeiro remove todas as quebras de linha e retornos de carro.
        programa = programa.replaceAll("[\\n|\\r]", " ");

        // Separa usando o espaço.
        String[] words = programa.split(" ");

        return words;
    }
}
