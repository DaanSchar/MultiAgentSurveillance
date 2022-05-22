package nl.maastrichtuniversity.dke.scenario.util;

import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVwriter {

    public void exportData(ArrayList<Victory> victories) {

        try{
            BufferedWriter br = new BufferedWriter(new FileWriter("victoryData.csv"));
            StringBuilder sb = new StringBuilder();

            sb.append("Games");
            sb.append(",");
            sb.append("Victories");
            sb.append("\r\n");

            // Append strings from array
            for (int i = 0; i < victories.size(); i++) {
                sb.append(i);
                sb.append(",");
                sb.append(victories.get(i).getWinner());
                sb.append("\r\n");
            }

            br.write(sb.toString());
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}