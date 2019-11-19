package ru.namibios.bdofishbot.bot.service;

import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.bot.state.FishBot;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.cli.Bot;
import ru.namibios.bdofishbot.cli.config.Message;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BotService {

    private static final Logger LOG = Logger.getLogger(BotService.class);

    private Bot bot;

    private Executor executor = Executors.newSingleThreadExecutor();

    public BotService() {
        this.bot = new Bot();
    }

    public void start(){

        if (Application.getUser().isBlocked()){
            LOG.info(Message.USER_IS_BLOCKED);
            JOptionPane.showMessageDialog(null, Message.USER_IS_BLOCKED);
            return;
        }

        boolean isInit = bot != null;
        if (isInit) {

            FishBot fishBot = bot.getFishBot();

            if (fishBot == null) {
                executor.execute(bot);
            } else {

                if (!fishBot.isRunned()) {
                    bot = new Bot();
                    executor.execute(bot);

                } else {
                    LOG.info(Message.ALREADY_WORK);
                    JOptionPane.showMessageDialog(null, Message.ALREADY_WORK);
                }

            }

        }

    }

    public void stop(){

        if (bot != null){
            bot.getFishBot().setRunned(false);
            bot.getFishBot().stopExecutors();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        var executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            System.out.println("Start");
        });

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();

        System.out.println("Done");
    }

}