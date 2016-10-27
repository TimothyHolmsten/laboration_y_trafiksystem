package traffic;

import vehicle.Car;
import vehicle.EmergencyVehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by timothy on 2016-10-17.
 */
public class Road extends Thread {
    private ArrayList<Car> cars;
    private TrafficLight trafficLight;

    public Road(ArrayList<Car> cars) {
        this.cars = cars;
        trafficLight = new TrafficLight();
    }

    public Road() {
        cars = new ArrayList<>();
        trafficLight = new TrafficLight();
    }

    public synchronized void addCar(Car car) {
        cars.add(car);
    }

    public synchronized void addRandomCar() {
        if (new Random().nextInt() % 4 == 3)
            addCar(new EmergencyVehicle());
        else
            addCar(new Car());
    }

    @Override
    public void run() {

        Random random = new Random();

        boolean running = true;

        while (running) {

            if (trafficLight.getCurrentSignal() == Signal.GREEN) {
                for (Iterator<Car> iterator = cars.iterator(); iterator.hasNext(); ) {
                    if (trafficLight.getCurrentSignal() == Signal.GREEN) {
                        synchronized (this) {
                            Car car = iterator.next();
                            iterator.remove();
                            try {
                                Thread.sleep(random.nextInt(400) + 200);
                            } catch (InterruptedException e) {
                            }
                        }
                    } else
                        break;
                }
            }

            try {
                Thread.sleep(random.nextInt(2000) + 1000);
                addRandomCar();
            } catch (InterruptedException e) {
            }

        }

    }

    public synchronized int getNumberOfCars() {
        return cars.size();
    }

    public synchronized int getNumberOfEmergencyVehicles() {
        int nrOfEmergencyVehicles = 0;
        for (Car car : cars)
            if (car instanceof EmergencyVehicle)
                nrOfEmergencyVehicles++;
        return nrOfEmergencyVehicles;
    }

    public synchronized TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public String toString() {
        return String.format("Total cars: %d, Emergencies: %d\n", getNumberOfCars(), getNumberOfEmergencyVehicles());
    }
}
