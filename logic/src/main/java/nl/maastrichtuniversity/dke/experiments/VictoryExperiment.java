package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;
import nl.maastrichtuniversity.dke.scenario.Scenario;

import java.util.ArrayList;

@Getter
@Setter
public class VictoryExperiment {

    Scenario scenario;
    int numOfGames;
//    int numOfIntruders;
//    int numOfGuards;
    boolean exp;

    private ArrayList<Victory> victories = new ArrayList<>();

    public VictoryExperiment(int numOfGames, boolean exp) {
        this.numOfGames = numOfGames;
//        this.numOfIntruders = numOfIntruders;
//        this.numOfGuards = numOfGuards;
        this.exp = exp;
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
}
