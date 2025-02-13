import java.io.Serializable;

public class Message implements Serializable {

    static final long serialVersionUID = 42L;
    // Phase1 login Phase
    boolean loginPhase;
    boolean playWithComputer;
    boolean playwithPerson;

    // phase1 receving end
    boolean connPossible;

    // phase2 Ship Placment
    boolean ShipPhase;
    String ShipName;
    int ShipX;
    int ShipY;
    boolean isVertical;
    boolean isHorizontal;

    // phase3 receving end
    boolean ShipCanBeplaced;

    // phase 3 readyToPlay
    boolean readyForPlay;

    // phase 4 play a move
    boolean playPhase;
    boolean myMove;
    boolean opponentMove;
    int playedX;
    int playedY;

    // phase 4 receving
    boolean won;
    boolean lost;
    boolean hit;
    boolean miss;

    Message(){
        loginPhase = false;
        playWithComputer = false;
        playwithPerson = false;
        connPossible = false;
        ShipPhase = false;
        readyForPlay = false;
        playPhase = false;
        won = false;
        lost = false;
        hit = false;
        miss = false;
        isHorizontal = false;
        isVertical = false;
        myMove = false;
        opponentMove = false;
    }


}
