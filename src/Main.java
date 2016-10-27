import traffic.ControlUnit;
import traffic.Direction;
import traffic.Road;
import traffic.TrafficLightsException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TrafficLightsException, InterruptedException {

        Road[] roads = {new Road(), new Road(), new Road(), new Road()};
        ControlUnit CU = new ControlUnit(roads);
        CU.start();
        for (Road road : roads)
            road.start();

        while (true) {
            for(Direction dir: Direction.values())
                System.out.println(dir + " " + roads[dir.getValue()].toString());

            System.out.println(CU.toString());

            Thread.sleep(16);

            clear();
        }
    }

    public static void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
