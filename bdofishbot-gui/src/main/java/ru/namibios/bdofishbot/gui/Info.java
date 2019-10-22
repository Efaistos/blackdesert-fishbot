package ru.namibios.bdofishbot.gui;

import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.bot.service.HttpService;
import ru.namibios.bdofishbot.gui.view.RootView;
import ru.namibios.bdofishbot.utils.ExceptionUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Info extends WindowAdapter {

    private static final Logger LOG = Logger.getLogger(Info.class);

    private RootView rootView;

    public Info(RootView rootView) {
        this.rootView = rootView;
    }

    private String getInfo(){

        try {

            HttpService httpService = new HttpService();
            return httpService.getInfo();

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getString(e));
        }

        return null;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        new InfoTask().start();
    }

    private final class InfoTask extends Thread {

        @Override
        public void run() {

            LOG.debug("Task info");

            String info = getInfo();
            if (info != null && !info.isEmpty()){
                JOptionPane.showMessageDialog(rootView, info, "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

}