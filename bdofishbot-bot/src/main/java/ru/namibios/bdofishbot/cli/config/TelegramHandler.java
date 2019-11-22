package ru.namibios.bdofishbot.cli.config;

import org.apache.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import ru.namibios.bdofishbot.bot.Screen;
import ru.namibios.bdofishbot.bot.service.BotService;
import ru.namibios.bdofishbot.bot.service.HttpService;
import ru.namibios.bdofishbot.bot.state.SideTaskContainer;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.utils.ExceptionUtils;
import ru.namibios.bdofishbot.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;

public class TelegramHandler extends StompSessionHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(TelegramHandler.class);

    private HttpService httpService;

    private BotService botService;

    public TelegramHandler(BotService botService) {
        this.httpService = new HttpService();
        this.botService = botService;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOG.debug("New session established : " + session.getSessionId());

        session.subscribe("/hash/topic/reg", this);
        session.subscribe("/hash/topic/commands", this);

        RemoteMessage msg = new RemoteMessage();
        msg.setHash(Application.getUser().getHash());
        msg.setKey(Application.getConfig().TELEGRAM_KEY());

        session.send("/app/tg_bot", msg);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOG.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return RemoteMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        RemoteMessage msg = (RemoteMessage) payload;

        LOG.debug("Received:" + msg);

        if (msg.getText() != null) {
            LOG.info(msg.getText());
        }

        if (msg.getRemoteCommand() != null) {
            switch (msg.getRemoteCommand()) {
                case "SCREEN":
                    LOG.info("Get screenshot..");

                    try {

                        BufferedImage screen = Screen.getScreen(Application.getConfig().FULL_SCREEN());
                        httpService.sendTelegramPhoto(Application.getConfig().TELEGRAM_KEY(), ImageUtils.imageToBytes(screen));

                    } catch (IOException e) {
                        LOG.error(ExceptionUtils.getString(e));
                    }

                    break;

                case "START":
                    LOG.info("Starting fishing bot..");

                    botService.start();

                    try {

                        httpService.sendTelegramMessage(Application.getConfig().TELEGRAM_KEY(), "Bot starting..");

                    } catch (IOException e) {
                        LOG.error(ExceptionUtils.getString(e));
                    }

                    break;

                case "STOP":
                    LOG.info("Stopping fishing bot");

                    botService.stop();

                    try {

                        httpService.sendTelegramMessage(Application.getConfig().TELEGRAM_KEY(), "Bot stopping..");

                    } catch (IOException e) {
                        LOG.error(ExceptionUtils.getString(e));
                    }


                    break;

                case "INVENTORY":
                    LOG.info("Get screen inventory");

                    SideTaskContainer.getInstance().add(SideTaskContainer.Task.INVENTORY);

                    break;

                case "SKIP_CALENDAR":
                    LOG.info("Skip calendar");

                    SideTaskContainer.getInstance().add(SideTaskContainer.Task.SKIP_CALENDAR);

                    break;

                case "UPTIME":
                    LOG.info("Get uptime");

                    SideTaskContainer.getInstance().add(SideTaskContainer.Task.UPTIME);

                    break;
            }

        }

    }

}

class RemoteMessage {

    private String hash;
    private String key;
    private String remoteCommand;
    private String time;
    private String text;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRemoteCommand() {
        return remoteCommand;
    }

    public void setRemoteCommand(String remoteCommand) {
        this.remoteCommand = remoteCommand;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RemoteMessage{" +
                "hash='" + hash + '\'' +
                ", key='" + key + '\'' +
                ", remoteCommand='" + remoteCommand + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}