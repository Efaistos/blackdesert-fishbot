package ru.namibios.bdofishbot.gui;

import ru.namibios.bdofishbot.config.converter.TimeConverter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimeMouseConverter extends MouseAdapter {

    private JTextField field;

    public TimeMouseConverter(JTextField field) {
        this.field = field;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (field != null) {
            String text = field.getText();
            try {

                TimeConverter converter = new TimeConverter(text);
                field.setToolTipText(converter.getValue());

            } catch (NumberFormatException ignore) {
                
            }
        }
    }

}