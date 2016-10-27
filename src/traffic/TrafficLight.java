package traffic;

/**
 * Created by timothy on 2016-10-17.
 */
public class TrafficLight {

    private Signal currentSignal;

    public TrafficLight() {
        currentSignal = Signal.RED;
    }

    public synchronized void goGreen(int time) {
        if (currentSignal == Signal.RED) {
            setCurrentSignal(Signal.YELLOW);
        }

        if (currentSignal == Signal.YELLOW) {
            setCurrentSignal(Signal.GREEN);
            sleepBetweenSwitch(time);
            goRed();
        }
    }

    private void goRed() {
        if (currentSignal == Signal.GREEN) {
            setCurrentSignal(Signal.YELLOW);
        }
        if (currentSignal == Signal.YELLOW)
            setCurrentSignal(Signal.RED);
    }

    private void sleepBetweenSwitch(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            setCurrentSignal(Signal.RED);
            Thread.currentThread().interrupt();
        }
    }

    private void setCurrentSignal(Signal signal) {
        currentSignal = signal;
    }

    public synchronized Signal getCurrentSignal() {
        return currentSignal;
    }
}