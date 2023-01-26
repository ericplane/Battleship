package battleship;

import java.util.Arrays;

public class Player {
    String[][] gameBoard = new String[10][10];
    String[][] warBoard = new String[10][10];

    public Player() {
        for (String[] strings : this.gameBoard) {
            Arrays.fill(strings, "~");
        }

        for (String[] strings : this.warBoard) {
            Arrays.fill(strings, "~");
        }
    }

    public String[][] getUserBoard() {
        // Get the filled out board
        return this.gameBoard;
    }

    public String[][] getWarBoard() {
        // Get the blank board
        return this.warBoard;
    }

    private int[] getTile(String tile) {
        try {
            // If the length of the String is above 3 or below 2 it is an invalid tile length
            if (tile.length() != 2 && tile.length() != 3) {
                throw new battleshipException("Invalid tile length");
            }

            // Gets the letter tile and removes the ASCII number from it
            int letter = tile.charAt(0) - 65;

            // Initiates the number variable
            int number;

            // Separate the letter from the numbers of the tile
            String intString = tile.substring(1);

            // Take the numbers and convert it into an Integer from a String subtracting one from it, so it starts with 0 not 1
            number = Integer.parseInt(intString) - 1;

            // If the letter or number integers are above 9 it is invalid
            if (letter > 9 || number > 9) {
                throw new battleshipException("Invalid tile");
            }

            return new int[]{letter, number};
        } catch (battleshipException exception) {
            return null;
        }
    }

    private boolean isNearbyTileOccupied(int[] tile) {
        int letter = tile[0];
        int number = tile[1];

        // Is the tile to the left empty?
        if (letter != 0) {
            if (gameBoard[letter - 1][number].equals("O")) {
                return true;
            }
        }

        // Is the tile to the left right?
        if (letter != 9) {
            if (gameBoard[letter + 1][number].equals("O")) {
                return true;
            }
        }

        // Is the tile on top empty?
        if (number != 0) {
            if (gameBoard[letter][number - 1].equals("O")) {
                return true;
            }
        }

        // Is the tile below empty?
        if (number != 9) {
            if (gameBoard[letter][number + 1].equals("O")) {
                return true;
            }
        }

        return false;
    }

    public String takeShot(String tile) {

        // Get the tile letter and number
        int[] formattedTile = getTile(tile);

        // If an error occurred return it
        if (formattedTile == null) {
            return "Error!";
        }

        int letter = formattedTile[0];
        int number = formattedTile[1];

        // Check if that tile has already been set to a different value
        if (gameBoard[letter][number].equals("X") || gameBoard[letter][number].equals("M")) {
            return "You already hit this tile!";
        }

        // If there is a ship it'll mark it as hit or mark it as missed if no ship was hit
        if (gameBoard[letter][number].equals("O")) {
            gameBoard[letter][number] = "X";
            warBoard[letter][number] = "X";

            for (String[] row : gameBoard) {
                for (String column : row) {
                    if (column.equals("O")) {
                        if (isNearbyTileOccupied(formattedTile)) {
                            return "You hit a ship!";
                        } else {
                            return "You sank a ship!";
                        }
                    }
                }
            }

            return "You sank the last ship. You won. Congratulations!";
        } else {
            gameBoard[letter][number] = "M";
            warBoard[letter][number] = "M";
            return "You missed.";
        }
    }

    public boolean addShip(String tiles, String shipType) throws battleshipException {
        if (tiles.length() >= 5) {
            // Get the tiles by letter and number
            int[] formattedTile1 = getTile(tiles.split(" ")[0]);
            int[] formattedTile2 = getTile(tiles.split(" ")[1]);

            // If an error occurred return it
            if (formattedTile1 == null || formattedTile2 == null) {
                return false;
            }

            int letterBegin = formattedTile1[0];
            int letterEnd = formattedTile2[0];
            int numBegin = formattedTile1[1];
            int numEnd = formattedTile2[1];

            boolean sameRow = false;

            // If the letters are the same it is all in the same row array
            if (letterBegin == letterEnd) {
                sameRow = true;
            } else {
                if (numBegin != numEnd) {
                    return false;
                }
            }

            // Check if the numbers go from big to small instead of the correct way
            if (numEnd < numBegin) {
                int tempNum = numBegin;
                numBegin = numEnd;
                numEnd = tempNum;
            }

            if (letterEnd < letterBegin) {
                int tempLetter = letterBegin;
                letterBegin = letterEnd;
                letterEnd = tempLetter;
            }

            int shipLength;

            // Calculates the ship length
            if (sameRow) {
                shipLength = (numEnd + 1) - numBegin;
            } else {
                shipLength = (letterEnd + 1) -  letterBegin;
            }

            // Checks if it matches the ship type or return it
            switch (shipType) {
                case "Carrier" -> {
                    if (shipLength != 5) {
                        return false;
                    }
                }
                case "Battleship" -> {
                    if (shipLength != 4) {
                        return false;
                    }
                }
                case "Submarine", "Cruiser" -> {
                    if (shipLength != 3) {
                        return false;
                    }
                }
                case "Destroyer" -> {
                    if (shipLength != 2) {
                        return false;
                    }
                }
            }

            // Add the ship to the 2D array if there are no nearby tiles which already have a ship on it
            if (sameRow) {
                for (int i = numBegin; i < (numEnd + 1); i++) {
                    if (this.gameBoard[letterBegin][i].equals("O")) {
                        return false;
                    }

                    if (isNearbyTileOccupied(new int[]{letterBegin, i})) {
                        return false;
                    }
                }

                for (int i = numBegin; i < (numEnd + 1); i++) {
                    this.gameBoard[letterBegin][i] = "O";
                }
            } else {
                for (int i = letterBegin; i < (letterEnd + 1); i++) {
                    if (this.gameBoard[i][numBegin].equals("O")) {
                        return false;
                    }

                    if (isNearbyTileOccupied(new int[]{i, numBegin})) {
                        return false;
                    }
                }

                for (int i = letterBegin; i < (letterEnd + 1); i++) {
                    this.gameBoard[i][numBegin] = "O";
                }
            }
        } else {
            return false;
        }
        return true;
    }
}