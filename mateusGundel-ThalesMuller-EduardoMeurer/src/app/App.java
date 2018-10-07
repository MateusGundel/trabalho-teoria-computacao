package app;

import controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AppModel;

/**
 *
 * @author Mateus Gundel
 */
public class App extends Application {

    public static Stage PRIMARY_STAGE;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppModel.LOG_SAIDA_ATIVO = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AplicacaoLayout.fxml"));

        Parent root = loader.load();

        AppController controller = loader.getController();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Verificador de Equivalência - Trabalho de Teoria da Computação");
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/app.jpg")));
        primaryStage.show();

        PRIMARY_STAGE = primaryStage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
