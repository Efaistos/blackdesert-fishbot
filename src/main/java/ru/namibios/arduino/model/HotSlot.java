package ru.namibios.arduino.model;

import java.util.concurrent.ThreadLocalRandom;

public class HotSlot {

    private String command;

    private boolean active;

    private String key;

    private long delay;

    private long period;

    private long randomDelay;

    private long randomPeriod;

    private long pauseFrom;
    private long pauseTo;

    public long getPauseFrom() {
        return pauseFrom;
    }

    public long getPauseTo() {
        return pauseTo;
    }

    public long getPause(){
        return ThreadLocalRandom.current().nextLong(pauseFrom, pauseTo);
    }

    public void setPauseFrom(String pauseFrom) {
        this.pauseFrom = converterMills(pauseFrom);
    }

    public void setPauseTo(String pauseTo) {
        this.pauseTo = converterMills(pauseTo);
    }

    public void setPauseFrom(long pauseFrom) {
        this.pauseFrom = pauseFrom;
    }

    public void setPauseTo(long pauseTo) {
        this.pauseTo = pauseTo;
    }

    public long getRandomDelay() {
        return randomDelay;
    }

    public void setRandomDelay(long randomDelay) {
        this.randomDelay = randomDelay;
    }

    public void setRandomDelay(String randomDelay) {
        this.randomDelay = converterMills(randomDelay);
    }

    public long getRandomPeriod() {
        return randomPeriod;
    }

    public void setRandomPeriod(long randomPeriod) {
        this.randomPeriod = randomPeriod;
    }

    public void setRandomPeriod(String randomPeriod) {
        this.randomPeriod = converterMills(randomPeriod);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getDelay() {
        return delay;
    }

    public long getDelayWithRandom() {
        if (randomDelay > 0 && randomDelay > delay) {
            return ThreadLocalRandom.current().nextLong(delay, randomDelay);
        }
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getPeriod() {
        return period;
    }

    public long getPeriodWithRandom() {
        if (randomDelay > 0 && randomDelay > delay) {
            return ThreadLocalRandom.current().nextLong(period, randomPeriod);
        }
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setDelay(String s) {
        this.delay = converterMills(s);
    }

    public void setPeriod(String s) {
        this.period = converterMills(s);
    }

    public static void main(String[] args) {

        HotSlot hotSlot= new HotSlot();
        hotSlot.setActive(true);
        hotSlot.setCommand("2");

        hotSlot.setPeriod("20s");
        hotSlot.setRandomPeriod("30s");

        hotSlot.setDelay(10000);
        hotSlot.setRandomDelay(20000);

        hotSlot.setPauseFrom(1000);
        hotSlot.setPauseTo(2000);

        long pause = hotSlot.getPause();
        System.out.println("pause = " + pause);

    }

    private long converterMills(String value) {
        long ms = -1;

        // К примеру если указано просто 0
        if (value.length() == 1) {
            return Long.parseLong(value);
        }

        char format = value.charAt(value.length() - 1);
        long unformat = Long.valueOf(value.substring(0, value.indexOf(format)));

        switch (format) {
            case 's' : ms = unformat * 1000;      break;
            case 'm' : ms = unformat * 1000 * 60; break;
            default  : ms = Long.parseLong(value);
        }

        return ms;
    }

    @Override
    public String toString() {
        return "HotSlot{" +
                "command='" + command + '\'' +
                ", active=" + active +
                ", key='" + key + '\'' +
                ", delay=" + delay +
                ", period=" + period +
                ", randomDelay=" + randomDelay +
                ", randomPeriod=" + randomPeriod +
                '}';
    }
}