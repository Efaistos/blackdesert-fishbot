package ru.namibios.arduino.model.command;

import org.apache.log4j.Logger;
import ru.namibios.arduino.config.Application;
import ru.namibios.arduino.config.Path;
import ru.namibios.arduino.model.*;
import ru.namibios.arduino.model.template.Loot;
import ru.namibios.arduino.utils.ExceptionUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FishLoot implements Command{

	private final static Logger LOG = Logger.getLogger(FishLoot.class);

	private static final String UNKNOWN_INDEX = "-1";

	private List<Screen> screens;

	public FishLoot(String... loot) throws IOException {
		if (loot.length <= 0 || loot.length > 8) {
			throw new IllegalArgumentException("Expected 1-8 files.. Actual " + loot.length);
		}

		this.screens = new ArrayList<>();
		for (String filename : loot) {
			this.screens.add(new Screen(filename));
		}

	}

	public FishLoot() {
		LOG.info("Init filter");

        Rectangle[] rectangles = Application.getInstance().LOOT_SLOT_LIST();

		this.screens = Arrays.stream(rectangles)
				.map(this::toScreen)
				.collect(Collectors.toList());

	}

    private Screen toScreen(Rectangle rectangle) {

	    try {

            return new Screen(rectangle);

        } catch (AWTException e) {
            LOG.error(ExceptionUtils.getString(e));
        }

        return null;
    }

    private String[] getLootIndices() {
		StringBuilder loots = new StringBuilder();
		for (Screen screen : screens) {

			ImageParser imageParser = new ImageParser(screen, Loot.values());
			imageParser.parse(Screen.GRAY);

			String key = imageParser.getKey();
			if (key.equals("-1,")) {
				double coefWhite = imageParser.getCoefWhite();
				LOG.debug("Loot cell is not defined");
				if (coefWhite == 0) {
					LOG.debug("CoefWhite = " + coefWhite + ". Replaced on empty cell");
					key = Loot.EMPTY.ordinal() + ",";
				}
			}

			loots.append(key);

		}

		LOG.debug("Loot indexes: " + loots);
		return loots.toString().split(",");
	}

	private void saveLoot(String[] arrayLoots){

		if (Application.getInstance().DEBUG_SCREEN() || Application.getInstance().DEBUG_FILTER_LOOT()) {
			screens.forEach(screen -> screen.saveImage(Path.DEBUG_FILTERLOOT));
		}

		if(Application.getInstance().SAVE_UNSORT()) {
			screens.forEach(screen -> screen.saveImage(Path.LOOT_UNSORT));
		}

		if (Application.getInstance().SAVE_UNKNOWN()) {
			for (int i = 0; i < arrayLoots.length; i++) {
				if (arrayLoots[i].equals(UNKNOWN_INDEX)) {
					screens.get(i).saveImage(Path.LOOT_UNKNOWN);
				}
			}
		}

	}
	
	@Override
	public String getKey(){
		
		String[] arrayLoots = getLootIndices();

		saveLoot(arrayLoots);

		boolean isTakeUnknown = Application.getInstance().TAKE_UNKNOWN();
		Looter looter = new Looter(arrayLoots, isTakeUnknown);

		if(looter.isTakeAll()) {
			LOG.info("Loot ok. Take all..");
			return ShortCommand.TAKE.getKey();
		}

        if(looter.isIgnoreAll()) {
            LOG.info("Trash. Throw out all..");
            return ShortCommand.IGNORE.getKey();
        }

        if(looter.isTakeByIndex()) {
            LOG.info("Take by index");
            String command = "";
            for(LootType type : looter.getLootTypeList()) {
                if(type.isOk() || type.isConfirm()) {
                    int index = type.getIndex();
                    Touch touch = Application.getInstance().LOOT_TOUCH()[index];
                    command+= type.isOk() ? touch.toCommandLoot() : touch.toCommandConfirmLoot();
                }
            }
            LOG.info(command);
            return command;
        }
		
		LOG.info("Strategy is not defined. Take..");
		return ShortCommand.TAKE.getKey();
	}

}