import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Board {

    private Button[][] playerGridButtons = new Button[10][10];
    private Button[][] opponentGridButtons = new Button[10][10];
    private Button[] shipButtons;

    private Button verticalButton;
    private Button horizontalButton;
    private Button playButton;

    private VBox boardContainer;
    private Stage stage;

    Scene scene;
    ClientServer clientconnection;

    boolean ShipPlacementPhase = false;
    boolean playPhase = false;
    String ShipName;
    String ShipPlacement;
    int ButtonToActivate = 0;

    Stage dialog;

    // Check to ensure no index out of bounds exception

    boolean computerGame = false;
    boolean computerShipsPlaced = false;

    PlayWithComputer game;


    public Board(Stage stage, ClientServer server) {
        this.stage = stage;
        clientconnection = server;
        initializeBoard();
        if(computerGame) {
            game = new PlayWithComputer();
        }
    }

    private void initializeBoard() {
        // Create the player and opponent grids
        GridPane playerGrid = createGrid(playerGridButtons, "Your Fleet");
        GridPane opponentGrid = createGrid(opponentGridButtons, "Enemy Waters");

        // Button to start the game
        playButton = new Button("Play");
        playButton.setStyle("-fx-background-color: palegreen; -fx-font-size: 14px; -fx-text-fill: white;");
        playButton.setDisable(true);

        // Ship buttons
        shipButtons = new Button[]{
                new Button("Aircraft Carrier"),
                new Button("Battleship"),
                new Button("Cruiser"),
                new Button("Submarine"),
                new Button("Destroyer")
        };

        for (Button shipButton : shipButtons) {
            shipButton.setStyle("-fx-background-color: lightsalmon; -fx-font-size: 12px;");
            shipButton.setPrefSize(150, 40);
            shipButton.setDisable(true);

        }
        shipButtons[0].setDisable(false);


        // Placement buttons
        verticalButton = new Button("Vertical");
        horizontalButton = new Button("Horizontal");
        verticalButton.setDisable(true);
        horizontalButton.setDisable(true);
        verticalButton.setStyle("-fx-background-color: lightgrey; -fx-font-size: 12px;");
        horizontalButton.setStyle("-fx-background-color: lightgrey; -fx-font-size: 12px;");

        // Ship selection box
        VBox shipsBox = new VBox(5);
        shipsBox.getChildren().add(createTitleText("Ships"));
        shipsBox.getChildren().addAll(shipButtons);
        styleBox(shipsBox);

        HBox plcementButtons = new HBox(10, verticalButton, horizontalButton);

        // Placement selection box
        VBox placementBox = new VBox(5, createTitleText("Ships and Placement"), shipsBox, plcementButtons);
        styleBox(placementBox);

        // Play box
        VBox playBox = new VBox(5, createTitleText("Messages Box"));
        styleBox(playBox);

        // Combine ships and placement boxes
        VBox rightPanel = new VBox(300, playBox, placementBox);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        // Layout with grids and right panel
        VBox gridsBox = new VBox(10, opponentGrid, playerGrid);
        HBox root = new HBox(20, gridsBox, rightPanel);

        // Container for the entire board
        boardContainer = new VBox();
        boardContainer.getChildren().addAll(root);
        boardContainer.setPadding(new Insets(10));
        boardContainer.setStyle("-fx-background-color: white;");

        dialog = new Stage();
        dialog.initOwner(stage);
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            dialog.close();
            stage.close();
        });

        StackPane dialogPane = new StackPane();
        dialogPane.getChildren().add(quitButton);
        Scene dialogScene = new Scene(dialogPane, 200, 100);
        dialog.setScene(dialogScene);



        setButtonsOnAction();


        scene = new Scene(boardContainer);

    }

    private Text createTitleText(String title) {
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", 16));
        titleText.setUnderline(true);
        return titleText;
    }

    private void styleBox(VBox box) {
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
        box.setAlignment(Pos.CENTER);
    }

    private GridPane createGrid(Button[][] gridButtons, String title) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 1px;");

        Text gridTitle = createTitleText(title);
        grid.add(gridTitle, 0, 0, gridButtons[0].length, 1);
        GridPane.setHalignment(gridTitle, HPos.CENTER);

        for (int row = 0; row < gridButtons.length; row++) {
            for (int col = 0; col < gridButtons[row].length; col++) {
                Button button = new Button();
                button.setMinSize(30, 30);
                button.setStyle("-fx-background-color: blue; -fx-font-size: 10px;");
//                button.setDisable(true);
                grid.add(button, col, row + 1); // +1 to account for title
                gridButtons[row][col] = button;
            }
        }
        return grid;
    }

    public void setButtonsOnAction(){
        shipButtons[0].setOnAction(e ->{handleAircraftShip();});
        shipButtons[1].setOnAction(e ->{handleBattleship();});
        shipButtons[2].setOnAction(e ->{handleCruiser();});
        shipButtons[3].setOnAction(e ->{handleSubmarine();});
        shipButtons[4].setOnAction(e ->{handleDestroyer();});
        horizontalButton.setOnAction(e ->{handleHorizontal();});
        verticalButton.setOnAction(e ->{handleVertical();});
        for (int row = 0; row < playerGridButtons.length; row++) {
            for (int col = 0; col < playerGridButtons[row].length; col++) {
                int finalRow = row;
                int finalCol = col;
                playerGridButtons[row][col].setOnAction(e->handlePlayerGridButtons(finalRow, finalCol));
            }
        }

        for (int row = 0; row < opponentGridButtons.length; row++) {
            for (int col = 0; col < opponentGridButtons[row].length; col++) {
                int finalRow = row;
                int finalCol = col;
                opponentGridButtons[row][col].setOnAction(e->handleopponentGridButtons(finalRow, finalCol));
            }
        }

    }

    public void handleopponentGridButtons(int X, int Y){

        if (!computerGame) {

            if (playPhase == true) {
                Message data = new Message();
                data.playPhase = true;
                data.playedX = X;
                data.playedY = Y;
                clientconnection.send(data);

                synchronized (clientconnection.lock) {
                    try {
                        clientconnection.lock.wait(); // Wait for notification
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupted exception
                    }
                }
                if (clientconnection.UpdatedMessage.myMove) {

                    if (clientconnection.UpdatedMessage.won) {
                        dialog.setTitle("You Won!");
                        dialog.show();
                    }

                    if (clientconnection.UpdatedMessage.hit) {
                        opponentGridButtons[X][Y].setDisable(true);
                        opponentGridButtons[X][Y].setStyle("-fx-background-color: red; -fx-font-size: 10px;");
                    } else if (clientconnection.UpdatedMessage.miss) {
                        opponentGridButtons[X][Y].setDisable(true);
                        opponentGridButtons[X][Y].setStyle("-fx-background-color: white; -fx-font-size: 10px;");

                    }
                }
                playPhase = false;

            }
        }
        else{
            game.playerAttack(X, Y);

        }


    }
    public void play(int X, int Y, boolean hit, boolean miss, boolean lost){
        if(lost){
            dialog.setTitle("You Lost!");
            dialog.show();
        }
        if (hit) {
            playerGridButtons[X][Y].setDisable(true);
            playerGridButtons[clientconnection.UpdatedMessage.playedX][clientconnection.UpdatedMessage.playedY].setStyle("-fx-background-color: red; -fx-font-size: 10px;");
        } else if (miss) {
            playerGridButtons[X][Y].setDisable(true);
            playerGridButtons[X][Y].setStyle("-fx-background-color: white; -fx-font-size: 10px;");

        }
        playPhase = true;
    }


    public void handleAircraftShip(){
        shipButtons[0].setDisable(true);
        verticalButton.setDisable(false);
        horizontalButton.setDisable(false);
        ShipName = "Aircraft Ship";

    }

    public void handleBattleship(){
        shipButtons[1].setDisable(true);
        verticalButton.setDisable(false);
        horizontalButton.setDisable(false);
        ShipName = "Battleship";

    }

    public void handleCruiser(){
        shipButtons[2].setDisable(true);
        verticalButton.setDisable(false);
        horizontalButton.setDisable(false);
        ShipName = "Cruiser";


    }

    public void handleSubmarine(){;
        shipButtons[3].setDisable(true);
        horizontalButton.setDisable(false);
        verticalButton.setDisable(false);
        ShipName = "Submarine";

    }

    public void handleDestroyer(){
        shipButtons[4].setDisable(true);
        verticalButton.setDisable(false);
        horizontalButton.setDisable(false);
        ShipName = "Destroyer";
        if(!computerGame) {
            ShipPlacementPhase = false;
            playPhase = true;
        }
    }

    public void handleHorizontal(){
        verticalButton.setDisable(true);
        horizontalButton.setDisable(true);
        ShipPlacement = "Horizontal";
        if(!computerGame) {
            ShipPlacementPhase = true;

        }

    }

    public void handleVertical(){

        verticalButton.setDisable(true);
        horizontalButton.setDisable(true);
        ShipPlacement = "Vertical";
        if(!computerGame) {
            ShipPlacementPhase = true;
        }
    }



    public void handlePlayerGridButtons(int X, int Y){

        if(!computerGame) {

            if (ShipPlacementPhase == true) {
                Message data = new Message();
                data.ShipPhase = true;
                data.ShipName = ShipName;
                if (ShipPlacement == "Horizontal") {
                    data.isHorizontal = true;
                } else {
                    data.isVertical = true;
                }
                data.ShipX = X;
                data.ShipY = Y;

                clientconnection.send(data);

                synchronized (clientconnection.lock) {
                    try {
                        clientconnection.lock.wait(); // Wait for notification
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupted exception
                    }
                }

                if (clientconnection.UpdatedMessage.ShipCanBeplaced) {
                    placeShip(X, Y);
                    ShipPlacementPhase = false;
                    ButtonToActivate++;
                    if (ButtonToActivate < 5) {
                        shipButtons[ButtonToActivate].setDisable(false);
                    }

                    ShipPlacementPhase = false;
                } else {
                    System.out.println("Ship placement failed");
                }

            }
        }
        else{

            placeShip(X, Y);
            ButtonToActivate++;
            if (ButtonToActivate < 5) {
                shipButtons[ButtonToActivate].setDisable(false);
            }
            if (ShipPlacement == "Horizontal") {
                game.placeShip(X, Y, getShipLength(ShipName), false);
            }
            else{
                game.placeShip(X, Y, getShipLength(ShipName), true);
            }

            if(!computerShipsPlaced){
                game.setupComputerShips();
            }
        }

    }

    public void placeShip(int X, int Y) {
        int length = getShipLength(ShipName);
        if ("vertical".equalsIgnoreCase(ShipPlacement)) {
            for (int i = 0; i < length; i++) {
                Button btn = playerGridButtons[X + i][Y];
                btn.setStyle("-fx-background-color: black; -fx-font-size: 10px;");
                btn.setDisable(true);
            }
        } else if ("horizontal".equalsIgnoreCase(ShipPlacement)) {
            for (int j = 0; j < length; j++) {
                Button btn = playerGridButtons[X][Y + j];
                btn.setStyle("-fx-background-color: black; -fx-font-size: 10px;");
                btn.setDisable(true);
            }
        }
    }

    private int getShipLength(String shipName) {
        switch (shipName) {
            case "Aircraft Ship":
                return 5;
            case "Battleship":
                return 4;
            case "Cruiser":
                return 3;
            case "Submarine":
                return 3;
            case "Destroyer":
                return 2;
            default:
                return 1; // Default to 1 if not recognized
        }
    }




    public void show(){
        stage.setScene(scene);
        stage.show();
    }
}
