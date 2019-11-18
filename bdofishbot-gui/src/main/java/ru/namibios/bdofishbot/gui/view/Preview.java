package ru.namibios.bdofishbot.gui.view;

import ru.namibios.bdofishbot.gui.UI;

import javax.swing.*;
import java.awt.*;

public class Preview extends JDialog {

    public Preview() {

        setLocation(RootView.LOCATION_X, RootView.LOCATION_Y);
        setSize(new Dimension(RootView.WIDTH, RootView.HEIGHT));

        getContentPane().setBackground(Color.WHITE);

        JLabel background = new JLabel(new ImageIcon(UI.IMG_BACKGROUND));
        add(background);

        setUndecorated(true);
        setAlwaysOnTop(true);

        setVisible(true);

    }

}