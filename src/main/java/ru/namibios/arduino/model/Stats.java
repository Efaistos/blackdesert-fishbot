package ru.namibios.arduino.model;

import ru.namibios.arduino.model.template.Loot;
import ru.namibios.arduino.model.template.StatusCaptchaTemplate;
import ru.namibios.arduino.model.template.StatusCutTemplate;

public final class Stats {

    private int changeRod;
    private int useSlots;

    private int catchBad;
    private int catchGood;
    private int catchPerfect;

    private int captchaParseSuccess;
    private int captchaParseFailure;

    private int fish;
    private int scala;
    private int event;
    private int key;
    private int unknown;
    private int trash;

    public int getChangeRod() {
        return changeRod;
    }

    public int getUseSlots() {
        return useSlots;
    }

    public int getCatchBad() {
        return catchBad;
    }

    public int getCatchGood() {
        return catchGood;
    }

    public int getCatchPerfect() {
        return catchPerfect;
    }

    public int getCaptchaParseSuccess() {
        return captchaParseSuccess;
    }

    public int getCaptchaParseFailure() {
        return captchaParseFailure;
    }

    public int getFish() {
        return fish;
    }

    public int getScala() {
        return scala;
    }

    public int getEvent() {
        return event;
    }

    public int getKey() {
        return key;
    }

    public int getUnknown() {
        return unknown;
    }

    public int getTrash() {
        return trash;
    }

    private static Stats instance = new Stats();

    public static Stats getInstance(){
        if (instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    public void incCatchBad(){
        catchBad++;
    }

    public void incCatchGood(){
        catchGood++;
    }

    public void incCatchPerfect(){
        catchPerfect++;
    }

    public void incCaptchaParseSuccess(){
        captchaParseSuccess++;
    }

    public void incCaptchaParseFailure(){
        captchaParseFailure++;
    }

    public void incKey(){
        key++;
    }

    public void incFish(){
        fish++;
    }

    public void incScala(){
        scala++;
    }

    public void incEvent(){
        event++;
    }

    public void incUnknown(){
        unknown++;
    }

    public void incTrash(){
        trash++;
    }

    synchronized public void incChangeRod() {
        changeRod++;
    }

    synchronized public void incUseSlot() {
        useSlots++;
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

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("===================== STATISTIC =====================").append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("===================== STATES ====================").append(System.lineSeparator());
        builder.append("USE SLOTS: ").append(useSlots).append(System.lineSeparator());
        builder.append("CHANGE RODS: ").append(changeRod).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("===================== CATCH =======================").append(System.lineSeparator());
        builder.append("BAD: ").append(catchBad).append(System.lineSeparator());
        builder.append("GOOD: ").append(catchGood).append(System.lineSeparator());
        builder.append("PERFECT: ").append(catchPerfect).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("================= PARSE CAPTCHA ==================").append(System.lineSeparator());
        builder.append("SUCCESS: ").append(captchaParseSuccess).append(System.lineSeparator());
        builder.append("FAILURE: ").append(captchaParseFailure).append(System.lineSeparator());

        builder.append(System.lineSeparator()).append("===================== DROP =======================").append(System.lineSeparator());
        builder.append("FISH: ").append(fish).append(System.lineSeparator());
        builder.append("KEY: ").append(key).append(System.lineSeparator());
        builder.append("SCALA: ").append(scala).append(System.lineSeparator());
        builder.append("EVENT: ").append(event).append(System.lineSeparator());
        builder.append("TRASH: ").append(trash).append(System.lineSeparator());
        builder.append("UNKNOWN: ").append(unknown).append(System.lineSeparator());

        return builder.toString();

    }

}