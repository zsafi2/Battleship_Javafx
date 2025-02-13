import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class loginPage {

    private Stage stage;
    private Scene scene;
    private VBox layout;

    Button btnPlayWithPlayer;
    Button btnPlayWithComputer;
    ClientServer clientconnection;

    public loginPage(Stage stage, ClientServer clientconnection) {
        this.stage = stage;
        this.clientconnection = clientconnection;
        initialize();
    }

    private void initialize() {
        // Create the Text for the Title
        Text title = new Text("BattleShip Game");
        title.setStyle("-fx-font: 32 arial; -fx-text-fill: #000000;");

        // Create Buttons
        btnPlayWithPlayer = new Button("Play With Another Player");
        btnPlayWithComputer = new Button("Play with Computer");

        // Styling Buttons
        String buttonStyle = "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px;";
        btnPlayWithPlayer.setStyle(buttonStyle);
        btnPlayWithComputer.setStyle(buttonStyle);

        // Set Button Widths
        btnPlayWithPlayer.setPrefWidth(200);
        btnPlayWithComputer.setPrefWidth(200);
        btnPlayWithPlayer.setPrefHeight(40);
        btnPlayWithComputer.setPrefHeight(40);

        // Set padding and spacing to match the image

        layout = new VBox(10, title, btnPlayWithPlayer, btnPlayWithComputer);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10)); // Adjust padding as necessary
        layout.setStyle("-fx-background-color: #4a90e2;");

        // Set the scene with a size of 400x400 as per the requirement
        scene = new Scene(layout, 400, 400);

        // Stage setup is done outside this class, just set the scene

    }



    public void show() {
        this.stage.setScene(scene);
        stage.show();
    }
};

