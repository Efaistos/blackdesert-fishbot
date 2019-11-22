package ru.namibios.bdofishbot.bot.command;

import ru.namibios.bdofishbot.bot.ImageParser;
import ru.namibios.bdofishbot.bot.Screen;
import ru.namibios.bdofishbot.bot.template.Chars;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.cli.config.Path;

import java.awt.*;

public class Line implements Command {

    private Screen screen;
	
	private ImageParser imageParser;
	
	public Line() throws AWTException {

	    screen = new Screen(Application.getConfig().SUB_LINE());

        if (Application.getConfig().DEBUG_SCREEN() || Application.getConfig().DEBUG_SUBLINE()) {
            screen.saveImage(Path.DEBUG_SUBLINE);
        }
		
		imageParser = new ImageParser(screen, Chars.values());
		imageParser.parse(Screen.GRAY);
		
	}

	@Override
	public String getKey() {
		return imageParser.getNumber();
	}
	
}