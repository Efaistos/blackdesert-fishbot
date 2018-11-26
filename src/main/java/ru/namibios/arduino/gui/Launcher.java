package ru.namibios.arduino.gui;

import org.apache.log4j.Logger;
import ru.namibios.arduino.config.Application;
import ru.namibios.arduino.config.Message;
import ru.namibios.arduino.gui.view.RootView;
import ru.namibios.arduino.utils.ExceptionUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Launcher {
	
	private static final Logger LOG = Logger.getLogger(Launcher.class);

	public static final Map<String, Locale> LOCALES = new HashMap<>();

	private static ServerSocket socket;
    private static final int PORT = Application.getInstance().LOCAL_PORT();

	static {
		LOCALES.put("English", new Locale("en", "US"));
		LOCALES.put("Русский", new Locale("ru", "RU"));
	}

	private static void checkIfRunning() {

		try {

			socket = new ServerSocket(PORT,0, InetAddress.getByAddress(new byte[] {127,0,0,1}));
		}

		catch (BindException e) {
			LOG.error("Program already running on port: " + Application.getInstance().LOCAL_PORT());
			LOG.error(ExceptionUtils.getString(e));
			System.exit(1);
		}
		catch (IOException e) {
			LOG.error("Unexpected error.");
			LOG.error(ExceptionUtils.getString(e));
			System.exit(2);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

		checkIfRunning();

		LOG.info("Start program..");

        String language = Application.getInstance().LANGUAGE();
        LOG.debug("Language: " + language);

        Locale locale = LOCALES.get(language);
        if (locale == null) {
            locale = LOCALES.get("English");
            LOG.debug("Unknown locale. Setting by default");
        }

        Locale.setDefault(locale);

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LOG.error(ExceptionUtils.getString(e));
        }

		UIManager.getDefaults().addResourceBundle("locale");

		try {

			SwingUtilities.invokeLater(RootView::new);

		}catch (Exception e) {
			LOG.info(String.format(Message.LOG_FORMAT_ERROR, e));
			LOG.error(ExceptionUtils.getString(e));
		}
	}
}
