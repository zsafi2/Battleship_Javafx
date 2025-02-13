import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientServer extends Thread {

    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;
    boolean messageReceived = false;
    Message UpdatedMessage;
    final Object lock = new Object();

    private Consumer<Serializable> callback;
    ClientServer(Consumer<Serializable> callback) {
        this.callback = callback;
    }

    public void run() {
        try {
            socketClient = new Socket("127.0.0.1", 5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        } catch(Exception e) {
        }

        while(true) {
            try {
                Message data = (Message) in.readObject();
                synchronized (lock) {
                    if (data.opponentMove == true) {
                        callback.accept(data);
                    }
                    UpdatedMessage = data;
                    lock.notifyAll(); // Notify waiting thread
                }
            } catch (Exception e) {}
        }
    }



    public void send(Message data) {
        try {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

