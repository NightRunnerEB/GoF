package behavioral;

import java.util.ArrayList;
import java.util.List;

interface Observer {

    void update(int price);
}

interface Subject {

    void attach(Observer o);

    void detach(Observer o);

    void notifyAllObservers();
}

class Stock implements Subject {

    private int price;
    private final List<Observer> observers = new ArrayList<>();

    public void setPrice(int price) {
        this.price = price;
        notifyAllObservers();
    }

    public int getPrice() {
        return price;
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer o : observers) {
            o.update(price);
        }
    }
}

class Chart implements Observer {

    @Override
    public void update(int price) {
        System.out.println("Chart updated with price = " + price);
    }
}

class Logger implements Observer {

    @Override
    public void update(int price) {
        System.out.println("Logger saved new price = " + price);
    }
}

@SuppressWarnings("unused")
final class ObserverExample {

    private ObserverExample() {
    }

    public static void example() {
        Stock stock = new Stock();
        stock.attach(new Chart());
        stock.attach(new Logger());

        stock.setPrice(100);
        stock.setPrice(120);
    }
}
