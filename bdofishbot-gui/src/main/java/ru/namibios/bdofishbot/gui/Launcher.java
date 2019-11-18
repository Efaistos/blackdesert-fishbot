package ru.namibios.bdofishbot.gui;

import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.cli.config.Message;
import ru.namibios.bdofishbot.gui.controller.RootConroller;
import ru.namibios.bdofishbot.utils.ExceptionUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;

public class Launcher {

    private static final Logger LOG = Logger.getLogger(Launcher.class);

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {

        LOG.info("Start program..");

        RootConroller rootConroller = new RootConroller();

        rootConroller.showPreview();

        Application.check();

        Application.getUser();

        Locale.setDefault(Application.getLocale());

        try {

            UIManager.setLookAndFeel(Application.getInstance().THEME());

        } catch (Exception e) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LOG.error(ExceptionUtils.getString(e));
        }

        UIManager.getDefaults().addResourceBundle("locale");

        try {
            rootConroller.showApplication();
        }catch (Exception e) {
            LOG.info(String.format(Message.LOG_FORMAT_ERROR, e));
            LOG.error(ExceptionUtils.getString(e));
        }

    }

}