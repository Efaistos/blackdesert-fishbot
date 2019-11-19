package ru.namibios.bdofishbot.gui.controller;

import ru.namibios.bdofishbot.bot.service.BotService;
import ru.namibios.bdofishbot.gui.view.Preview;
import ru.namibios.bdofishbot.gui.view.RootView;

import javax.swing.*;

public class RootConroller {

    private Preview preview;

    private RootView rootView;

    private BotService botService;

    public RootConroller(BotService botService) {
        this.botService = botService;
    }

    public void showPreview() {

        SwingUtilities.invokeLater(() -> {
            preview = new Preview();
        });

    }

    public void showApplication() {

        SwingUtilities.invokeLater(() -> {
            rootView = new RootView();
        });

        preview.dispose();
    }

    public void addAction(){

        rootView.getButtonStart().addActionListener(e -> {
            enablePreference(false);
            if (e.getActionCommand().equals(UIManager.getString("rootview.button.start"))) {
                botService.start();
            }
        });

        rootView.getButtonStop().addActionListener(e ->{
            enablePreference(true);
            if (e.getActionCommand().equals(UIManager.getString("rootview.button.stop"))) {
                botService.stop();
            }
        });

    }

    private void enablePreference(boolean state) {
        JMenuItem preference = rootView.getPreference();
        preference.setEnabled(state);
    }

}