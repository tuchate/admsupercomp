package lab2;


import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        AtomicBoolean firstFlag = new AtomicBoolean(false);
        AtomicBoolean secondFlag = new AtomicBoolean(false);
        Neighbour jhoe = new Neighbour(firstFlag, secondFlag, "Jhoe");
        Neighbour mike = new Neighbour(secondFlag, firstFlag, "Mike");
        jhoe.start();
        mike.start();
    }
}
