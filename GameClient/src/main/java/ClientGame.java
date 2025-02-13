import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.Serializable;

public class ClientGame extends Application {

    ClientServer clientconnection;
    Alert alert;
    Board board;

    Board computerBoard;
    PlayWithComputer game;


    public void start(Stage primaryStage) {

        clientconnection = new ClientServer((Serializable data) -> {
            if (data instanceof Message) {
                Message message = (Message) data;
                Platform.runLater(() -> {
                    board.play(message.playedX, message.playedY, message.hit, message.miss, message.lost);
                });
            }
        });
        clientconnection.start();
        loginPage loginMenu = new loginPage(primaryStage, clientconnection);
        board = new Board(primaryStage, clientconnection);
        computerBoard = new Board(primaryStage, clientconnection);

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Box");


        loginMenu.btnPlayWithComputer.setOnAction(e->PlayWithComputer(board));
        loginMenu.btnPlayWithPlayer.setOnAction(e->PlaywithPerson(board));
        loginMenu.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private void PlayWithComputer(Board board) {

        game = new PlayWithComputer();
        computerBoard.computerGame = true;
        computerBoard.show();

    }

    private void PlaywithPerson(Board board) {

        Message data = new Message();
        data.loginPhase = true;
        data.playwithPerson = true;

        clientconnection.send(data);

        synchronized(clientconnection.lock) {
            try {
                clientconnection.lock.wait(); // Wait for notification
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interrupted exception
            }
        }

        if (!clientconnection.UpdatedMessage.connPossible) {
            alert.setContentText("There is no Other Client to connect to");
            alert.showAndWait();
        } else {
            board.show();
        }
    }

}