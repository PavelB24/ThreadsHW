package ru.gb;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.gb.Main.cdl;
import static ru.gb.Main.cdl2;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static volatile boolean isWinner= false;
    private static CyclicBarrier cbr = new CyclicBarrier(4);

//    public static CountDownLatch cdl= new CountDownLatch(4);
    public static int getCarsCount() {
        return CARS_COUNT;
    }
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cdl.countDown();
            cbr.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
        }
            if(!isWinner) {
                isWinner=true;
                System.out.println(this.getName() + " ПОБЕДИТЕЛЬ!");

            }
        cdl2.countDown();

    }
}
