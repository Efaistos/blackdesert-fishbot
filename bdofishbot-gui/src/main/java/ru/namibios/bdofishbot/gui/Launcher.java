package ru.namibios.bdofishbot.gui;

import org.apache.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.namibios.bdofishbot.bot.service.BotService;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.cli.config.Message;
import ru.namibios.bdofishbot.cli.config.TelegramHandler;
import ru.namibios.bdofishbot.gui.controller.RootConroller;
import ru.namibios.bdofishbot.utils.ExceptionUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;

public class Launcher {

    private static final Logger LOG = Logger.getLogger(Launcher.class);

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        LOG.info("Start program..");

        Locale.setDefault(Application.getLocale());

        UIManager.getDefaults().addResourceBundle("locale");

        BotService botService = new BotService();

        RootConroller rootConroller = new RootConroller(botService);

        rootConroller.showPreview();

        Application.check();
        Application.getUser();

        if (Application.getInstance().TELEGRAM()) {
            WebSocketClient client = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(client);

            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            StompSessionHandler sessionHandler = new TelegramHandler(botService);
            stompClient.connect(Application.getInstance().URL_WS(), sessionHandler);
        }

        try {

            UIManager.setLookAndFeel(Application.getInstance().THEME());

        } catch (Exception e) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LOG.error(ExceptionUtils.getString(e));
        }

        try {

            rootConroller.showApplication();
            rootConroller.addAction();

        }catch (Exception e) {
            LOG.info(String.format(Message.LOG_FORMAT_ERROR, e));
            LOG.error(ExceptionUtils.getString(e));
        }

    }

}