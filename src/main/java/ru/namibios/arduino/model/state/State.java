package ru.namibios.arduino.model.state;

import ru.namibios.arduino.model.Timer;
import ru.namibios.arduino.model.state.service.CommandSender;
import ru.namibios.arduino.utils.DelayUtils;

public abstract class State {
	
	public FishBot fishBot;
	
	protected long beforeStart;
	protected long afterStart;

	protected Timer timer;
	protected CommandSender commandSender;

	public State(FishBot fishBot) {
		this.fishBot = fishBot;
		this.timer = new Timer();
		this.commandSender = new CommandSender();

	}
	
	public State(FishBot fishBot, long beforeStart, long afterStart) {
		this(fishBot);
		this.beforeStart = beforeStart;
		this.afterStart = afterStart;
	}

	private static final int OVERFLOW = 300;
	private int step = 0;

	protected void ifBreak() {
		step++;
		if(step >= OVERFLOW){
			onOverflow();
		}
	}

	public void start(){
		DelayUtils.delay(beforeStart);
		onStep();
		DelayUtils.delay(afterStart);
	}

	public void onOverflow() {

	}

	public abstract void onStep();
	
	public FishBot getFishBot() {
		return fishBot;
	}
}