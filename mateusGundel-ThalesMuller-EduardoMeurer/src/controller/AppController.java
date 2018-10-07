package controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import model.AppModel;

/**
 * FXML Controller class
 *
 * @author Mateus Gundel
 */
public class AppController {

    @FXML
    public Label lblErro;

    @FXML
    public Button btnVerificarEqui;

    @FXML
    public Button btnCancelar;

    @FXML
    public Button btnSair;

    @FXML
    private TextArea entradaProgramaUm;

    @FXML
    private TextArea entradaProgramaDois;

    @FXML
    public TextArea resultadoPasso1Programa1;

    @FXML
    public TextArea resultadoPasso1Programa2;

    @FXML
    public TextArea resultadoPasso2Programa1;

    @FXML
    public TextArea resultadoPasso2Programa2;

    @FXML
    public TextArea resultadoPasso3Programa1;

    @FXML
    public TextArea resultadoPasso3Programa2;

    @FXML
    public TextArea resultadoPasso4;

    @FXML
    public Tab tabPasso3;
    @FXML
    public Tab tabPasso4;

    AppModel model;

    // seta string do botão verificar equi
    private StringProperty strStatus = new SimpleStringProperty("Verificar Equivalência");

    // Captura String do programa 1
    public StringProperty programaUmProperty = new SimpleStringProperty();
    public StringProperty programaDoisProperty = new SimpleStringProperty();

    public StringProperty errorMessage = new SimpleStringProperty();

    public AppController() throws Exception {
        this.model = new AppModel(this);
    }

    /**
     * Inicializa o controller. Realiza os binds com o view.
     */
    public void initialize() {

        // Bind da variável com o text area.
        programaUmProperty.bindBidirectional(entradaProgramaUm.textProperty());
        programaDoisProperty.bindBidirectional(entradaProgramaDois.textProperty());

        try {
            model = new AppModel(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // status thread
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();

        lblErro.textProperty().bind(errorMessage);

        strStatus.bind(Bindings.createStringBinding(() -> {
            if (stateProperty.isEqualTo(Worker.State.RUNNING).get()) {
                return "Processando...";
            }
            return "Verificar Equivalência";
        }, stateProperty));
        btnVerificarEqui.textProperty().bind(strStatus);

        btnVerificarEqui.disableProperty().bind(stateProperty.isEqualTo(Worker.State.RUNNING));
        btnCancelar.disableProperty().bind(stateProperty.isNotEqualTo(Worker.State.RUNNING));

        // Começa a escutar os eventos dos botões
        inicializarEventos();
    }

    /**
     * Adiciona listeners.
     */
    private void inicializarEventos() {
        btnVerificarEqui.setOnAction(actionEvent -> {
            //Inicializa a execução da Thread.
            ((Service) model.worker).restart();
        });

        btnCancelar.setOnAction(actionEvent -> {
            // Cancela a execução da Thread.
            model.worker.cancel();
        });

        btnSair.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }
}
