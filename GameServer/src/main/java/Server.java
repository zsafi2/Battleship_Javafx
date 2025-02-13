//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {
    TheServer server;

    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    HashMap<Integer, GameBoard> Game = new HashMap<>();

    int count = 1;

    boolean PlayWithComputer = false;

    Server(){
        server = new TheServer();
        server.start();
    }


    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(5555);)
            {
                System.out.println("Server is waiting for a client!");

                while(true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    c.start();
                    clients.add(c);
                    Game.put(count, new GameBoard());
                    count++;

                }
            }
            catch(Exception e) {
                System.out.println("Server socket did not launch");
            }
        }
    }



    class ClientThread extends Thread {

        private Socket clientSocket;
        int count;
        int oppCount;
        private ObjectInputStream input;
        private ObjectOutputStream output;



        public ClientThread(Socket socket, int count) {
            this.clientSocket = socket;
            this.count = count;
            System.out.println("Client# " + count + " has been created");
            if (count == 1){
                oppCount = 2;
            }
            else{
                oppCount = 1;
            }
        }

        public void run() {
            try {
                this.output = new ObjectOutputStream(this.clientSocket.getOutputStream());
                this.input = new ObjectInputStream(this.clientSocket.getInputStream());
                this.clientSocket.setTcpNoDelay(true);

            } catch (IOException var3) {
                System.out.println("Error setting up streams");
            }

            while (true) {
                try {

                    Message message = (Message) this.input.readObject();
                    System.out.println("Message received!!");
                    if (message != null) {
                        processMessage(message);
                    }

                } catch (ClassNotFoundException | IOException var5) {
                    clients.remove(this);
                    System.out.println("Client disconnected");
                    break;
                }
            }
        }

        private void processMessage(Message message) throws IOException {
            if(message.loginPhase){
                Message data = new Message();
                if(message.playwithPerson){

                    if (clients.size() != 2){
                        data.loginPhase = true;
                        data.connPossible = false;
                    }
                    else{
                        data.loginPhase = true;
                        data.connPossible = true;
                    }

                }
                else{
                    data.loginPhase = true;
                    data.connPossible = true;
                    PlayWithComputer = true;

                }
                sendMessage(data);
            }
            else if (message.ShipPhase) {
                Message data = new Message();
                boolean result = Game.get(count).placeShip(message.ShipX, message.ShipY, this.getShipLength(message.ShipName), message.isVertical);
                data.ShipPhase = true;
                data.ShipCanBeplaced = result;
                sendMessage(data);

            }

            else if (message.playPhase) {

                Message data = new Message();
                CellState result = Game.get(oppCount).attack(message.playedX, message.playedY);
                data.myMove = true;
                data.hit = result == CellState.HIT;
                data.miss = result == CellState.MISS;
                data.won = Game.get(oppCount).hasLost();
                this.sendMessage(data);

                Message data1 = new Message();
                data1.opponentMove = true;
                data1.playedX = message.playedX;
                data1.playedY = message.playedY;
                data1.hit = result == CellState.HIT;
                data1.miss = result == CellState.MISS;
                data1.lost = data.won;
                clients.get(oppCount-1).output.writeObject(data1);
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
                    return 0;
            }
        }

        private void sendMessage(Message message) {
            try {
                this.output.writeObject(message);
            } catch (IOException var3) {
                System.out.println("Error sending message");
            }

        }
    }

}


