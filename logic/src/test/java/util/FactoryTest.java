package util;

import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FactoryTest {


    @Test
    public void testScenarioFromMapParser() {
        var parser = new MapParser(new File("src/test/resources/maps/testmap.txt"));
        var scenario = parser.createScenario();
        var guards = scenario.getGuards();
        var environment = scenario.getEnvironment();

        Assertions.assertEquals(3, guards.size());
        Assertions.assertEquals(0, scenario.getIntruders().size());
        Assertions.assertEquals(60, environment.getWidth());
        Assertions.assertEquals(40, environment.getHeight());
        Assertions.assertEquals(60 * 40, environment.size());
        Assertions.assertEquals(1, scenario.getGameMode());
        Assertions.assertEquals(0.1, scenario.getScaling());
        Assertions.assertEquals("test environment", scenario.getName());
    }


}
