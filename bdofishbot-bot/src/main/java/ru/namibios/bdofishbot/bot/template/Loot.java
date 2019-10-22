package ru.namibios.bdofishbot.bot.template;

import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.bot.ImageParser;
import ru.namibios.bdofishbot.bot.Screen;
import ru.namibios.bdofishbot.cli.Application;
import ru.namibios.bdofishbot.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum Loot implements MatrixTemplate {
	
	SCALA("resources/loot/ok/scala"),
	
	KEY("resources/loot/ok/key"),
	
	FISH("resources/loot/ok/fish"),
	
	TRASH("resources/loot/trash"),
	
	EVENT("resources/loot/ok/event"),

	CONFIRM("resources/loot/ok/confirm"),

	EMPTY("resources/loot/ok/empty");
	
	private final List<int[][]> templates;

	private final Logger LOG = Logger.getLogger(Loot.class);
	
	public List<int[][]> getTemplates() {
		return templates;
	}
	
	Loot(String fileFolderName) {
		this.templates = new ArrayList<>();

		File[] files = new File(fileFolderName).listFiles();
		if (files != null && files.length == 0) {
			LOG.error("Folder is empty: " + fileFolderName);
			Application.closeBot(Application.CODE_INIT_LOOT);
		}

		for (File file : files) {
			BufferedImage image = ImageUtils.read(file);
			ImageParser parser = new ImageParser(image);
			parser.parse(Screen.GRAY);

			templates.add(parser.getImageMatrix());
		}
	}

}