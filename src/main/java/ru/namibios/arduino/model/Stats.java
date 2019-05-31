package ru.namibios.arduino.model;

import ru.namibios.arduino.model.template.Loot;
import ru.namibios.arduino.model.template.StatusCaptchaTemplate;
import ru.namibios.arduino.model.template.StatusCutTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

public final class Stats {

    private static Stats instance = new Stats();

    public static Stats getInstance(){
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    enum Statistic{

        START_TIME,
        END_TIME,

        CHANGE_ROD,
        USE_SLOTS,

        CATCH_BAD,
        CATCH_GOOD,
        CATCH_PERFECT,

        CAPTCHA_PARSE_SUCCESS,
        CAPTCHA_PARSE_FAILURE,

        FISH,
        SCALA,
        EVENT,
        KEY,
        UNKNOWN,
        TRASH
    }


    private final Map<Statistic, Object> stats = new EnumMap<>(Statistic.class);

    private void add(Statistic key, long value){
        if (!stats.containsKey(key)) {
            stats.put(key, 0L);
        }

        long newValue = getLong(key) + value;
        stats.put(key, newValue);

    }

    private long getLong(Statistic key) {
        return stats.containsKey(key) ? (long) stats.get(key) : 0L;
    }

    public void initStart(){
        stats.put(Statistic.START_TIME, new Timestamp(new Date().getTime()));
    }

    public void initEnd(){
        stats.put(Statistic.END_TIME, new Timestamp(new Date().getTime()));
    }

    public void incCatchBad(){
        add(Statistic.CATCH_BAD, 1);
    }

    public void incCatchGood(){
        add(Statistic.CATCH_GOOD, 1);
    }

    public void incCatchPerfect(){
        add(Statistic.CATCH_PERFECT, 1);
    }

    public void incCaptchaParseSuccess(){
        add(Statistic.CAPTCHA_PARSE_SUCCESS, 1);
    }

    public void incCaptchaParseFailure(){
        add(Statistic.CAPTCHA_PARSE_FAILURE, 1);
    }

    public void incKey(){
        add(Statistic.KEY, 1);
    }

    public void incFish(){
        add(Statistic.FISH, 1);
    }

    public void incScala(){
        add(Statistic.SCALA, 1);
    }

    public void incEvent(){
        add(Statistic.EVENT, 1);
    }

    public void incUnknown(){
        add(Statistic.UNKNOWN, 1);
    }

    public void incTrash(){
        add(Statistic.TRASH, 1);
    }

    synchronized public void incChangeRod() {
        add(Statistic.CHANGE_ROD, 1);
    }

    synchronized public void incUseSlot() {
        add(Statistic.USE_SLOTS, 1);
    }

    public void lootFilter(int slot) {

        if (slot == Loot.FISH.ordinal()) {
            instance.incFish();
        }

        if (slot == Loot.EVENT.ordinal()) {
            instance.incEvent();
        }

        if (slot == Loot.SCALA.ordinal()) {
            instance.incScala();
        }

        if (slot == Loot.KEY.ordinal()) {
            instance.incKey();
        }

        if (slot == Loot.TRASH.ordinal()) {
            instance.incTrash();
        }

        if (slot == -1) {
            instance.incUnknown();
        }
    }

    public void statusCaptcha(StatusCaptchaTemplate status){

        switch (status) {
            case SUCCESS:
                instance.incCaptchaParseSuccess();
                break;

            case FAILED:
                instance.incCaptchaParseFailure();
                break;
        }
    }

    public void statusCut(StatusCutTemplate status){
        switch (status) {
            case PERFECT:
                instance.incCatchPerfect();
                break;

            case GOOD:
                instance.incCatchGood();
                break;

            case BAD:
                instance.incCatchBad();
                break;
        }
    }

    public Map<Statistic, Object> getStats() {
        return stats;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("===================== STATISTIC =====================").append(System.lineSeparator());

        builder.append("START TIME: ").append(stats.get(Statistic.START_TIME)).append(System.lineSeparator());
        builder.append("END TIME: ").append(stats.get(Statistic.END_TIME)).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("===================== STATES ====================").append(System.lineSeparator());
        builder.append("USE SLOTS: ").append(stats.get(Statistic.USE_SLOTS)).append(System.lineSeparator());
        builder.append("CHANGE RODS: ").append(stats.get(Statistic.CHANGE_ROD)).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("================= CATCH =================").append(System.lineSeparator());
        builder.append("BAD: ").append(stats.get(Statistic.CATCH_BAD)).append(System.lineSeparator());
        builder.append("GOOD: ").append(stats.get(Statistic.CATCH_GOOD)).append(System.lineSeparator());
        builder.append("PERFECT: ").append(stats.get(Statistic.CATCH_PERFECT)).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("================= PARSE CAPTCHA =================").append(System.lineSeparator());
        builder.append("SUCCESS: ").append(stats.get(Statistic.CAPTCHA_PARSE_SUCCESS)).append(System.lineSeparator());
        builder.append("FAILURE: ").append(stats.get(Statistic.CAPTCHA_PARSE_FAILURE)).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("===================== DROP =======================").append(System.lineSeparator());
        builder.append("FISH: ").append(stats.get(Statistic.FISH)).append(System.lineSeparator());
        builder.append("KEY: ").append(stats.get(Statistic.KEY)).append(System.lineSeparator());
        builder.append("SCALA: ").append(stats.get(Statistic.SCALA)).append(System.lineSeparator());
        builder.append("EVENT: ").append(stats.get(Statistic.EVENT)).append(System.lineSeparator());
        builder.append("TRASH: ").append(stats.get(Statistic.TRASH)).append(System.lineSeparator());
        builder.append("UNKNOWN: ").append(stats.get(Statistic.UNKNOWN)).append(System.lineSeparator());

        return builder.toString();

    }

}