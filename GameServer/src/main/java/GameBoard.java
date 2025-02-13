//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Arrays;

class GameBoard {
    private CellState[][] board = new CellState[10][10];

    public GameBoard() {
        for(int i = 0; i < 10; ++i) {
            Arrays.fill(this.board[i], CellState.EMPTY);
        }

    }

    public boolean placeShip(int x, int y, int length, boolean vertical) {
        if (vertical) {
            if (x + length > 10) return false;  // Check if the ship goes out of bounds downwards
            for (int j = 0; j < length; ++j) {
                if (this.board[x + j][y] != CellState.EMPTY) {
                    return false;  // Ensure all cells are empty before placing the ship
                }
            }
            for (int j = 0; j < length; ++j) {
                this.board[x + j][y] = CellState.SHIP;  // Place the ship vertically
            }
        } else {
            if (y + length > 10) return false;  // Check if the ship goes out of bounds to the right
            for (int j = 0; j < length; ++j) {
                if (this.board[x][y + j] != CellState.EMPTY) {
                    return false;  // Ensure all cells are empty before placing the ship
                }
            }
            for (int j = 0; j < length; ++j) {
                this.board[x][y + j] = CellState.SHIP;  // Place the ship horizontally
            }
        }
        return true;
    }


    public CellState attack(int x, int y) {
        if (this.board[x][y] == CellState.SHIP) {
            this.board[x][y] = CellState.HIT;
            return CellState.HIT;
        } else if (this.board[x][y] == CellState.EMPTY) {
            this.board[x][y] = CellState.MISS;
            return CellState.MISS;
        } else {
            return this.board[x][y];
        }
    }

    public boolean hasLost() {
        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (this.board[i][j] == CellState.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}
