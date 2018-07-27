package ru.namibios.arduino;

import org.apache.log4j.Logger;

import ru.namibios.arduino.config.Application;
import ru.namibios.arduino.model.state.FishBot;
import ru.namibios.arduino.utils.DelayUtils;

public class Transfer extends Thread{ 
	
	private final static Logger LOG = Logger.getLogger(Transfer.class);

	private FishBot fishBot;
	
	public Transfer() {
		this.fishBot = new FishBot();
	}
	
	public FishBot getFishBot() {
		return fishBot;
	}
	
	public void restart(){
		LOG.info("Need Restart. Restarted after 15 second...");
		DelayUtils.delay(15000);
		fishBot.setRunned(true);
		fishBot.setRestart(false);
		run();
	}

	@Override
	public void run() {
		
		LOG.info("Start...");

		Application.getPhysicalPort().openPort();
		DelayUtils.delay(3000);
		
		if(!Application.getPhysicalPort().isOpen()) {
			LOG.info("Port is closed. Check you port in settings");
			return;
		} 
		
		LOG.info("Port is open...");
		
		while (fishBot.isRunned()) fishBot.getState().start();
		
		Application.getPhysicalPort().closePort();
		
		LOG.info("Port closed...");
		LOG.info("Thread stop.");
		
		if(fishBot.isRestart()) restart();

	}
	
}