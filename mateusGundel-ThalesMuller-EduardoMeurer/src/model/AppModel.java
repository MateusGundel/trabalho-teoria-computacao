package model;

import app.Processa;
import controller.AppController;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 *
 * @author Mateus Gundel
 */
public class AppModel {

    private AppController controller;

    public Worker<String> worker;

    public static Boolean LOG_SAIDA_ATIVO = false;

    public AppModel(AppController controller) throws Exception {

        this.controller = controller;

        /**
         * Implementa Worker rodando por thread. (Está usando classe anônima
         * para rodar uma Task.)
         */
        worker = new Service<String>() {
            @Override
            protected Task<String> createTask() {

                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {

                        //Atualiza atributo message.
                        updateMessage("Processando...");

                        // Seta animação para status de intermitente.
                        updateProgress(-1, -1);

                        Processa processaDados = new Processa(controller);
                        Boolean sucesso;

                        try {
                            // chama a função que processa os dados
                            sucesso = processaDados.processar();
                        } catch (InterruptedException e) {

                            // Detecta se o usuário cancelou a execução.
                            if (isCancelled()) {
                                // Interrompe a execução.
                                return null;
                            }
                            throw new RuntimeException("Ocorreu um erro ao processar dados!");
                        }

                        if (sucesso) {
                            updateProgress(1, 1);
                            updateMessage("Concluído");
                        } else {
                            throw new RuntimeException("Ocorreu um erro ao processar os dados!");
                        }
                        return "Finalizado";

                    }
                };

            }

            @Override
            protected void succeeded() {
                System.out.println("Completou com sucesso.");
            }

            @Override
            protected void cancelled() {
                System.out.println("Cancelado pelo usuário.");
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    System.out.println("Ocorreu um erro na função processar.");
                    controller.errorMessage.set("Ocorreu um erro na função processar.");

                });
            }

        };

    }

}
