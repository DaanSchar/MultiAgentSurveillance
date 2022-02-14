package nl.maastrichtuniversity.dke;

import nl.maastrichtuniversity.dke.scenario.MapParser;

public class Main{

   public static void main(String[] args) {
      var map = "src/main/resources/maps/testmap.txt";

      var scenario = new MapParser(map).createScenario();
   }

}
