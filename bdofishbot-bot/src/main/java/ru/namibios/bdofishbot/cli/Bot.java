package ru.namibios.bdofishbot.cli;

import com.sun.jna.platform.win32.WinDef;
import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.bot.state.DeferredStartState;
import ru.namibios.bdofishbot.bot.state.FishBot;
import ru.namibios.bdofishbot.bot.state.SlotTaskModeState;
import ru.namibios.bdofishbot.cli.config.Message;
import ru.namibios.bdofishbot.utils.DelayUtils;
import ru.namibios.bdofishbot.utils.WinAPI;

public class Bot extends Thread{
	
	private final static Logger LOG = Logger.getLogger(Bot.class);

	private FishBot fishBot;
	
	public Bot() {}

	public FishBot getFishBot() {
		return fishBot;
	}

	private void restart(){
		LOG.info("Need Restart. Restarted after 10 second...");
		DelayUtils.delay(10000);

		fishBot.setRunned(true);
		fishBot.setRestart(false);

		process();
	}

	private void process(){

		LOG.info("Start...");

		WinDef.HWND windowGame = WinAPI.findWindow(Application.getConfig().GAME_TITLE());
		if (windowGame == null) {
			LOG.info("The game is not running");
			Application.closeBot(Application.CODE_GAME_NOT_RUNNING);
		}
		if (fishBot == null) {
			fishBot = new FishBot(true);
		}

		DelayUtils.delay(3000);

		switch (Application.getConfig().MODE()) {
			case FISHING:
				LOG.info("Bot started on FISHING mode..");
				fishBot.setState(new DeferredStartState(fishBot));
				break;
			case TASK_SLOT:
				LOG.info("Bot started on TASK/SLOT mode..");
				fishBot.setState(new SlotTaskModeState(fishBot));
				break;
			default:
				LOG.info("Undefined mode.. Started FISHING mode by default..");
		}

		while (fishBot.isRunned()) fishBot.getState().process();

		LOG.info("Thread stop.");

		if(fishBot.isRestart()) restart();

	}

	@Override
	public void run() {

		process();

		fishBot.notifyUser(Message.END_WORK);
		fishBot.stopExecutors();

		LOG.info("End work.");

	}

}