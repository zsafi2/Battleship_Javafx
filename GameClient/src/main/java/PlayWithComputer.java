import java.util.Arrays;
import java.util.Random;

public class PlayWithComputer {
    private CellState[][] playerBoard = new CellState[10][10];
    private CellState[][] computerBoard = new CellState[10][10];
    private Random random = new Random();

    public PlayWithComputer() {
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        setupComputerShips(); // Randomly place ships on the computer's board
    }

    private void initializeBoard(CellState[][] board) {
        for (int i = 0; i < 10; i++) {
            Arrays.fill(board[i], CellState.EMPTY);
        }
    }

    public void setupComputerShips() {
        // Example: Place 5 ships for the computer with predefined lengths
        placeShipRandomly(computerBoard, 5, true);
        placeShipRandomly(computerBoard, 4, false);
        placeShipRandomly(computerBoard, 3, true);
        placeShipRandomly(computerBoard, 3, false);
        placeShipRandomly(computerBoard, 2, true);
    }

    private boolean placeShipRandomly(CellState[][] board, int length, boolean vertical) {
        boolean placed = false;
        while (!placed) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            placed = placeShip(computerBoard, x, y, length, vertical);
        }
        return true;
    }

    public boolean placeShip(CellState[][] board, int x, int y, int length, boolean vertical) {
        if (vertical) {
            if (x + length > 10) return false; // Check if ship placement goes out of bounds
            for (int j = 0; j < length; ++j) {
                if (computerBoard[x + j][y] != CellState.EMPTY) {
                    return false; // Check if the slot is already occupied
                }
            }
            for (int j = 0; j < length; ++j) {
                computerBoard[x + j][y] = CellState.SHIP;
            }
        } else {
            if (y + length > 10) return false;
            for (int j = 0; j < length; ++j) {
                if (computerBoard[x][y + j] != CellState.EMPTY) {
                    return false;
                }
            }
            for (int j = 0; j < length; ++j) {
                computerBoard[x][y + j] = CellState.SHIP;
            }
        }
        return true;
    }



    public boolean placeShip(int x, int y, int length, boolean vertical) {
        if (vertical) {
            if (x + length > 10) return false; // Check if ship placement goes out of bounds
            for (int j = 0; j < length; ++j) {
                if (playerBoard[x + j][y] != CellState.EMPTY) {
                    return false; // Check if the slot is already occupied
                }
            }
            for (int j = 0; j < length; ++j) {
                playerBoard[x + j][y] = CellState.SHIP;
            }
        } else {
            if (y + length > 10) return false;
            for (int j = 0; j < length; ++j) {
                if (playerBoard[x][y + j] != CellState.EMPTY) {
                    return false;
                }
            }
            for (int j = 0; j < length; ++j) {
                playerBoard[x][y + j] = CellState.SHIP;
            }
        }
        return true;
    }

    public CellState playerAttack(int x, int y) {
        return attack(computerBoard, x, y);
    }

    public CellState computerAttack() {
        int x, y;
        do {
            x = random.nextInt(10);
            y = random.nextInt(10);
        } while (playerBoard[x][y] == CellState.HIT || playerBoard[x][y] == CellState.MISS);
        return attack(playerBoard, x, y);
    }

    private CellState attack(CellState[][] board, int x, int y) {
        if (board[x][y] == CellState.SHIP) {
            board[x][y] = CellState.HIT;
            return CellState.HIT;
        } else {
            board[x][y] = CellState.MISS;
            return CellState.MISS;
        }
    }

    public boolean hasLost(CellState[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == CellState.SHIP) {
                    return false; // Found a ship part that hasn't been hit yet
                }
            }
        }
        return true; // No ships left, the player or computer has lost
    }

    public boolean playerHasLost() {
        return hasLost(playerBoard);
    }

    public boolean computerHasLost() {
        return hasLost(computerBoard);
    }
}
