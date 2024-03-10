package machine_coding.tictactoe;

import machine_coding.tictactoe.controllers.GameController;
import machine_coding.tictactoe.models.*;

import java.util.*;

public class TicTacToe {

    private static Game game;
    private static GameController gameController;
    private static List<Player> players;
    private static Set<Character> symbolSet;
    private static boolean issueHappened;
    private static Scanner input;

    public static void main(String[] args) {

        input = new Scanner(System.in);
        gameController = new GameController();
        issueHappened = false;

        // First Play-through
        createAndPlayGame();

        // If there is no error while creating the game
        if(!issueHappened) {
            // Replay of the entire game
            askForReplay();
            // Play Again?
            System.out.println("Do you want to play again? (Y/N) ");
            char playAgain = input.next().charAt(0);
            while((playAgain == 'Y' || playAgain == 'y') && !issueHappened) {
                createAndPlayGame();
                if(!issueHappened) {
                    askForReplay();
                    System.out.println("Do you want to play again? (Y/N)");
                    playAgain = input.next().charAt(0);
                }
            }
            System.out.println("Thanks for Playing! See you soon...");
        }

    }

    private static void createAndPlayGame() {
        initializeGame();
        gatherInfoOfHumanPlayers();
        gatherInfoOfBots();
        int undoCount = gatherInfoOfUndoLimit();
        try {
            game = gameController.createGame(players, undoCount);
        } catch (Exception e) {
            System.out.println("Error while creating the game: " + e.getMessage());
            issueHappened = true;
            return;
        }
        playGame();
    }

    private static void initializeGame() {
        players = new ArrayList<>();
        symbolSet = new HashSet<>();
    }

    // Gathering Info of Human Players
    private static void gatherInfoOfHumanPlayers() {
        System.out.println("How many Human players?");
        int numOfHumanPlayers = input.nextInt();
        for(int i = 1; i <= numOfHumanPlayers; i++) {
            System.out.println("Enter Name & Symbol for Player " + i);
            String name = input.next();
            char symbol = input.next().charAt(0);
            while(symbolSet.contains(symbol)) {
                System.out.println("Symbol already taken! Enter a New Symbol");
                symbol = input.next().charAt(0);
            }
            symbolSet.add(symbol);
            players.add(new HumanPlayer(name, new Symbol(symbol)));
        }
    }

    private static void gatherInfoOfBots() {
        System.out.println("How many Bots?");
        int numOfBots = input.nextInt();
        // Gathering Info of Bots
        String randomSymbols = "abcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        for(int i = 1; i <= numOfBots; i++) {
            // Choosing Difficulty Level
            System.out.println("Choose the Difficulty Level: (E/M/H)");
            char botLevelChar = input.next().charAt(0);
            BotLevel botLevel;
            switch (botLevelChar) {
                case 'E' -> botLevel = BotLevel.EASY;
                case 'M' -> botLevel = BotLevel.MEDIUM;
                case 'H' -> botLevel = BotLevel.HARD;
                default  -> {
                    System.out.println("Invalid Input, Choosing EASY level");
                    botLevel = BotLevel.EASY;
                }
            }
            // Choosing Symbol
            int idx = random.nextInt(randomSymbols.length());
            char symbol = randomSymbols.charAt(idx);
            while(symbolSet.contains(symbol)) {
                idx = random.nextInt(randomSymbols.length());
                symbol = randomSymbols.charAt(idx);
            }
            players.add(new BotPlayer("Bot " + i, new Symbol(symbol), botLevel));
        }
    }

    private static int gatherInfoOfUndoLimit() {
        System.out.println("How many UNDO's per player? ");
        return input.nextInt();
    }

    private static void playGame() {
        // Start Playing the Game
        // while(game is IN_PROGRESS)
        // 1. Print the Board
        // 2. Make a Move
        //      * Check for game possibilities
        //           -> IF 'won', set the game status to ENDED
        //           -> ELSE IF 'drawn', set the game status to DRAWN
        //           -> ELSE, capture the move & pass the chance to next player
        while(gameController.getGameStatus(game) == GameStatus.IN_PROGRESS) {
            if(gameController.getCurrentPlayer(game) instanceof HumanPlayer) {
                System.out.println("Current Board");
                gameController.printBoard(game);
            }
            gameController.makeMove(game);
            gameController.printBoard(game);
            // Ask for UNDO, only if the game is in progress
            if(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {
                gameController.undo(game);
            }
        }
        GameStatus gameStatus = gameController.getGameStatus(game);
        if(gameStatus.equals(GameStatus.ENDED)) {
            Player winner = gameController.getCurrentPlayer(game);
            System.out.println("Game has ENDED, " + winner.getName() + " WON!");
        } else if(gameStatus.equals(GameStatus.DRAWN)) {
            System.out.println("Game has DRAWN!");
        }
        System.out.println();
        gameController.printBoard(game);
    }

    //Replay the entire game
    private static void askForReplay() {
        System.out.println("Do you want a REPLAY of the entire game? (Y/N) ");
        char replayReply = input.next().charAt(0);
        if(replayReply == 'Y' || replayReply == 'y') {
            gameController.replayGame(game);
        }
    }

}
