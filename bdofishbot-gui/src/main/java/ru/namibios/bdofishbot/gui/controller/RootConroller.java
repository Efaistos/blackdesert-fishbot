package ru.namibios.bdofishbot.gui.controller;

import ru.namibios.bdofishbot.gui.view.Preview;
import ru.namibios.bdofishbot.gui.view.RootView;

import javax.swing.*;

public class RootConroller {

    private Preview preview;

    public void showPreview() {

        SwingUtilities.invokeLater(() -> {
            preview = new Preview();
        });

    }

    public void showApplication() {

        SwingUtilities.invokeLater(RootView::new);

        preview.dispose();
    }

}