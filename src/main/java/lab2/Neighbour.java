package lab2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neighbour extends Thread {

    private static Logger log = Logger.getLogger(Neighbour.class.getName());
    
    private AtomicBoolean myFlag, neighbourFlag;
    static private int berrys = 1000;
    private int myBerrys = 0;
    private String name;

    public Neighbour(AtomicBoolean myFlag, AtomicBoolean neighbourFlag, String name) {
        this.myFlag = myFlag;
        this.neighbourFlag = neighbourFlag;
        this.name = name;
    }

    public void run() {
        while(!isInterrupted()) {
            myFlag.set(true);
            if (neighbourFlag.get()) {
                myFlag.set(false);
            } else {
                log.log(Level.INFO, "{0} says: I'm on the field now...", name);
                int count = Math.min(new Random().nextInt(10), berrys);
                for (int i = 0; i < count; i++)
                    try {
                        log.log(Level.INFO, "{0} says: I've find berry!", name);
                        berrys = berrys - 1;
                        myBerrys = myBerrys + 1;
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        this.interrupt();
                    }
                log.log(Level.INFO, "{0} says: I'm leaving field...", name);
                if (berrys == 0)
                    interrupt();
                myFlag.set(false);
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
        log.log(Level.INFO, "{0} says: I'm done! My berrys: " + Integer.toString(myBerrys), name);
    }
}
