package machine_coding.tictactoe.models;

import machine_coding.tictactoe.factories.BotPlayingStrategyFactory;
import machine_coding.tictactoe.strategies.bot_playing.BotPlayingStrategy;
import machine_coding.tictactoe.strategies.bot_playing.EasyBotPlayingStrategy;

public class BotPlayer extends Player {

    private BotLevel botLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(String name, Symbol symbol, BotLevel botLevel) {
        super(name, symbol);
        this.botLevel = botLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botLevel);
    }

    @Override
    public RowColumn makeMove(Board board) {
        System.out.println("\nThinking...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(this.getName() + "'s Move ");
        return botPlayingStrategy.makeMove(board);
    }


}
