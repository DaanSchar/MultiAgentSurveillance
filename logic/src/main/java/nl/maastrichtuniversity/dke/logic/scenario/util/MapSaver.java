package nl.maastrichtuniversity.dke.logic.scenario.util;

import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MapSaver {
    private FileWriter writer;

    public MapSaver() {
        try {
            writer = new FileWriter("core/assets/generatedTestMap.txt");
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public void saveMap() {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.close();
    }


    public void scenarioSaver() {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("gameMode = 1");
        printWriter.println("height = 40");
        printWriter.println("width = 60");
        printWriter.println("scaling = 0.1");
        printWriter.println("numGuards = 1");
        printWriter.println("numIntruders = 1");
        printWriter.println("baseSpeedIntruder = 14.0");
        printWriter.println("sprintSpeedIntruder = 20.0");
        printWriter.println("baseSpeedGuard = 14.0");
        printWriter.println("timeStep = 0.1");
    }


    public void areaSaver(int x1, int y1, int x2, int y2, TileType type) {
        PrintWriter printWriter = new PrintWriter(writer);
        if (type == TileType.SPAWN_GUARDS) {
            printWriter.println("spawnAreaGuards  = " + x1 + " " + y1 + " " + x2 + " " + y2);
        } else if (type == TileType.SPAWN_INTRUDERS) {
            printWriter.println("spawnAreaIntruders  = " + x1 + " " + y1 + " " + x2 + " " + y2);
        } else if (type == TileType.TARGET) {
            printWriter.println("targetArea  = " + x1 + " " + y1 + " " + x2 + " " + y2);
        } else if (type == TileType.SENTRY) {
            printWriter.println("sentrytower  = " + x1 + " " + y1 + " " + x2 + " " + y2);

        } else {
            printWriter.println(type.name().toLowerCase() + " = " + x1 + " " + y1 + " " + x2 + " " + y2);
        }

    }


    public void teleportSaver(int x1, int y1, int x2, int y2, int disx, int disy, int rotation) {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("teleport = " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + disx + " " + disy + " " + rotation);
    }


    public void tileSaver(int x, int y, TileType type) {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(type.name().toLowerCase() + " = " + x + " " + y);
    }
}