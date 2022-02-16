package nl.maastrichtuniversity.dke;


import nl.maastrichtuniversity.dke.discrete.MapParser;

import java.io.File;

public class Main{

   public static void main(String[] args) {
      var map = "src/main/resources/maps/testmap.txt";
      MapParser parser = new MapParser(new File(map));
      var scenario = parser.createScenario();
   }

}
