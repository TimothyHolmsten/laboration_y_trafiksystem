package traffic;

import java.util.ArrayList;

/**
 * Created by timothy on 2016-10-17.
 */
public class ControlUnit extends Thread {
    private Road[] roads;
    private Direction lastPriority;

    public ControlUnit(Road[] roads) throws TrafficLightsException {
        this.roads = roads;
        ArrayList<TrafficLight> trafficLights = new ArrayList<>();
        for (Road road : this.roads)
            trafficLights.add(road.getTrafficLight());
        if (trafficLights.size() != 4) throw new TrafficLightsException("Must be four traffic lights");
    }

    @Override
    public void run() {

        int switchTime;
        while (!Thread.currentThread().isInterrupted()) {
            if (waitingAllRoads())
                switchTime = 1000;
            else
                switchTime = 3000;

            lastPriority = calculatePriorityRoad();
            roads[lastPriority.getValue()].getTrafficLight().goGreen(switchTime);
            roads[lastPriority.getValue()].interrupt();
        }

    }

    private boolean waitingAllRoads() {
        for (Road road : roads)
            if (road.getNumberOfCars() == 0)
                return false;
        return true;
    }

    private int getRoadCars(Direction dir) {
        return roads[dir.getValue()].getNumberOfCars();
    }

    private int getRoadEmergencies(Direction dir) {
        return roads[dir.getValue()].getNumberOfEmergencyVehicles();
    }

    private Direction calculatePriorityRoad() {
        Direction priorityRoad = Direction.NORTH;
        int roadCars = 0;
        int roadEmergencies = 0;
        for (Direction dir : Direction.values())
            if (getRoadEmergencies(dir) > roadEmergencies) {
                roadEmergencies = getRoadEmergencies(dir);
                priorityRoad = dir;
            }
        if (roadEmergencies > 0)
            return priorityRoad;

        for (Direction dir : Direction.values())
            if (getRoadCars(dir) > roadCars) {
                roadCars = getRoadCars(dir);
                priorityRoad = dir;
            }
        return priorityRoad;
    }

    @Override
    public String toString() {
        return "Last priority was: " + lastPriority;
    }
}
