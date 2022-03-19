package util;

import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FactoryTest {


    @Test
    public void test() {
        var scenario = new MapParser(new File("src/test/resources/test.txt")).createScenario();
    }

}
