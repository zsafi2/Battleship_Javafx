import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerGUI extends Application {

    Server serverconnection;

    public void start(Stage primaryStage) {

        serverconnection = new Server();
        Label myLabel = new Label("Server Connected");
        VBox root = new VBox(myLabel);
        Scene serverScene = new Scene(root, 250, 250);
        primaryStage.setScene(serverScene);
        primaryStage.setTitle("Server");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

