package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;

import java.util.ArrayList;

@Getter
@Setter
public class VictoryExperiment {

    private Game game;
    private int numOfGames;
    private boolean exp;

    private ArrayList<Victory> victories = new ArrayList<>();

    public VictoryExperiment(Game game, int numOfGames, boolean exp) {
        this.numOfGames = numOfGames;
        this.exp = exp;
        this.game = game;
    }

    public void printVictories() {
        for (Victory victory : victories) {
            System.out.print(victory.getWinner() + " ");
        }
    }

    public int countWinner(String winner) {
        int count = 0;
        for (Victory victory : victories) {
            if (victory.getWinner().equals(winner)) {
                count++;
            }
        }
        return count;
    }

    public boolean isDone() {
        return game.getGameNumber() >= getNumOfGames();
    }
}
