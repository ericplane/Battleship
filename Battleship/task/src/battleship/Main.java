package battleship;

import java.util.Scanner;
import java.lang.String;

import battleship.battleshipException;
import battleship.Player;

public class Main {
    public static void changePlayer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press \"Enter\" and pass the move to another player...");
        scanner.nextLine();
        scanner.close();
        System.out.println();
        return;
    }
    public static void setupGameBoard(Player userBoard) throws RuntimeException, battleshipException {
        boolean placedCarrier = false;
        boolean placedBShip = false;
        boolean placedSub = false;
        boolean placedCruiser = false;
        boolean placedDestroyer = false;

        Scanner scanner = new Scanner(System.in);

        printGameBoard(userBoard, false);

        while (!placedCarrier) {
            try {
                System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
                System.out.println();
                String CarrierInput = scanner.nextLine();
                System.out.println();
                placedCarrier = userBoard.addShip(CarrierInput, "Carrier");

                if (!placedCarrier) {
                    throw new RuntimeException("Error! Try again: ");
                }
            } catch (RuntimeException | battleshipException runtimeException) {
                System.out.println(runtimeException.getMessage());
                System.out.println();
                String CarrierInput = scanner.nextLine();
                System.out.println();
                placedCarrier = userBoard.addShip(CarrierInput, "Carrier");
            }
        }

        printGameBoard(userBoard, false);

        while (!placedBShip) {
            try {
                System.out.println("Enter the coordinates of the Battleship (4 cells):");
                System.out.println();
                String BShipInput = scanner.nextLine();
                System.out.println();
                placedBShip = userBoard.addShip(BShipInput, "Battleship");
                if (!placedBShip) {
                    throw new RuntimeException("Error! Try again: ");
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                System.out.println();
                String BShipInput = scanner.nextLine();
                System.out.println();
                placedBShip = userBoard.addShip(BShipInput, "Battleship");
            }
        }

        printGameBoard(userBoard, false);

        while (!placedSub) {
            try {
                System.out.println("Enter the coordinates of the Submarine (3 cells):");
                System.out.println();
                String SubInput = scanner.nextLine();
                System.out.println();
                placedSub = userBoard.addShip(SubInput, "Submarine");
                if (!placedSub) {
                    throw new RuntimeException("Error! Try again: ");
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                System.out.println();
                String SubInput = scanner.nextLine();
                System.out.println();
                placedSub = userBoard.addShip(SubInput, "Submarine");
            }
        }

        printGameBoard(userBoard, false);

        while (!placedCruiser) {
            try {
                System.out.println("Enter the coordinates of the Cruiser (3 cells):");
                System.out.println();
                String CruiserInput = scanner.nextLine();
                System.out.println();
                placedCruiser = userBoard.addShip(CruiserInput, "Cruiser");
                if (!placedCruiser) {
                    throw new RuntimeException("Error! Try again: ");
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                System.out.println();
                String CruiserInput = scanner.nextLine();
                System.out.println();
                placedCruiser = userBoard.addShip(CruiserInput, "Cruiser");
            }
        }

        printGameBoard(userBoard, false);

        while (!placedDestroyer) {
            try {
                System.out.println("Enter the coordinates of the Destroyer (2 cells):");
                System.out.println();
                String DestroyerInput = scanner.nextLine();
                System.out.println();
                placedDestroyer = userBoard.addShip(DestroyerInput, "Destroyer");
                if (!placedDestroyer) {
                    throw new RuntimeException("Error! Try again: ");
                }
            } catch (RuntimeException runtimeException) {
                System.out.println(runtimeException.getMessage());
                System.out.println();
                String DestroyerInput = scanner.nextLine();
                System.out.println();
                placedDestroyer = userBoard.addShip(DestroyerInput, "Destroyer");
            }
        }

        printGameBoard(userBoard, false);
    }

    public static void mainGameLoop(Player playerOne, Player playerTwo) {
        boolean gameFinished = false;

        Scanner scanner = new Scanner(System.in);

        System.out.println("The game starts!");
        System.out.println();

        changePlayer();

        while (!gameFinished) {

            boolean playerFinished = false;

            while (!playerFinished) {
                printGameBoard(playerTwo, true);
                System.out.println("---------------------");
                printGameBoard(playerOne, false);
                System.out.println();
                System.out.println("Player 1, it's your turn:");
                System.out.println();
                String space = scanner.next();
                System.out.println();
                String isHit = playerTwo.takeShot(space);

                if (isHit.equals("Error!")) {
                    System.out.println("Error!");
                    System.out.println();
                    continue;
                }

                System.out.println(isHit);
                System.out.println();

                if (isHit.equals("You sank the last ship. You won. Congratulations!")) {
                    gameFinished = true;
                    playerFinished = true;
                    break;
                } else {
                    playerFinished = true;
                }
            }

            playerFinished = false;

            if (gameFinished) {
                continue;
            }
            changePlayer();

            while (!playerFinished) {
                printGameBoard(playerOne, true);
                System.out.println("---------------------");
                printGameBoard(playerTwo, false);
                System.out.println();
                System.out.println("Player 2, it's your turn:");
                System.out.println();
                String space = scanner.next();
                System.out.println();
                String isHit = playerOne.takeShot(space);

                if (isHit.equals("Error!")) {
                    System.out.println("Error!");
                    System.out.println();
                    continue;
                }

                System.out.println(isHit);
                System.out.println();

                if (isHit.equals("You sank the last ship. You won. Congratulations!")) {
                    gameFinished = true;
                    playerFinished = true;
                    break;
                } else {
                    playerFinished = true;
                }
            }

            if (!gameFinished) {
                changePlayer();
            }
        }
    }

    public static void printGameBoard(Player userBoardClass, boolean warBoard) {
        String[][] gameBoard = warBoard ? userBoardClass.getWarBoard() : userBoardClass.getUserBoard();
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < gameBoard.length; row++) {
            char rowLetter = (char) (row + 65);
            System.out.print(rowLetter + " ");
            for (int rowVal = 0; rowVal < gameBoard[row].length; rowVal++) {
                if (rowVal == gameBoard[row].length - 1) {
                    System.out.println(gameBoard[row][rowVal]);
                } else {
                    System.out.print(gameBoard[row][rowVal] + " ");
                }
            }
        }
    }

    public static void main(String[] args) throws RuntimeException, battleshipException {

        // Initializes the game board
        Player playerOne = new Player();
        Player playerTwo = new Player();

        // Places Ships
        System.out.println("Player 1, place your ships on the game filed");
        System.out.println();
        setupGameBoard(playerOne);
        System.out.println();

        changePlayer();

        System.out.println("Player 2, place your ships on the game filed");
        System.out.println();
        setupGameBoard(playerTwo);

        // Initiates the main game loop
        mainGameLoop(playerOne, playerTwo);
    }
}