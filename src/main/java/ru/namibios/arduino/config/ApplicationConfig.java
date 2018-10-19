package ru.namibios.arduino.config;

import com.fazecast.jSerialComm.SerialPort;
import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;
import ru.namibios.arduino.model.HotSlot;
import ru.namibios.arduino.model.Touch;

import java.awt.*;

@Sources("file:resources/application.properties")
public interface ApplicationConfig extends Accessible, Mutable{

	@Key("bot.port")
	@DefaultValue("")
	String PORT();

	///////////////////////////////////////////////////////////////////////////
	// RODS
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.rod.count")
	@DefaultValue("0")
	int COUNT_ROD();
	
	@Key("bot.rod.changetime")
	@DefaultValue("180000")
	int TIME_CHANGE_ROD();
	
	@Key("bot.rod.x")
	@DefaultValue("1532")
	int ROD_START_X();
	
	@Key("bot.rod.y")
	@DefaultValue("355")
	int ROD_START_Y();
	
	@Key("bot.rod.dx")
	@DefaultValue("48")
	int ROD_DX();
	
	@Key("bot.rod.dy")
	@DefaultValue("0")
	int ROD_DY();
	
	@Key("bot.delay.rod.before")
	@DefaultValue("0")
	int DELAY_BEFORE_CHANGE_ROD();
	
	@Key("bot.delay.rod.after")
	@DefaultValue("15000")
	int DELAY_AFTER_CHANGE_ROD();

	///////////////////////////////////////////////////////////////////////////
	//	CAPTCHA
	///////////////////////////////////////////////////////////////////////////
	
	@DefaultValue("50")
	@Key("bot.kapcha.noise.iteration")
	int CNT_KAPCHA();
	
	@DefaultValue("0.88")
	@Key("bot.parsing.coefidentity")
	double COEF_IDENTITY();
	
	@Key("bot.port.serial")
	@DefaultValue("${bot.key}")
	@ConverterClass(SerialPortConverter.class)
	SerialPort physicalPort();
	
	@Key("bot.loot.touch")
	@Separator(";")
	@DefaultValue("{1561,616};{1608,616}")
	@ConverterClass(LootTouchConverter.class)
	Touch[] LOOT_TOUCH();

	///////////////////////////////////////////////////////////////////////////
	// LOOT FILTER
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.loot.fish")
	@DefaultValue("true")
	boolean FISH();
	
	@Key("bot.loot.key")
	@DefaultValue("true")
	boolean KEY();
	
	@Key("bot.loot.rock")
	@DefaultValue("true")
	boolean ROCK();
	
	@Key("bot.loot.event")
	@DefaultValue("true")
	boolean EVENT();
	
	@Key("bot.loot.trash")
	@DefaultValue("false")
	boolean TRASH();
	
	@Key("bot.loot.unknown")
	@DefaultValue("false")
	boolean TAKE_UNKNOWN();
	
	@Key("bot.loot.save_unsort")
	@DefaultValue("true")
	boolean SAVE_UNSORT();

	///////////////////////////////////////////////////////////////////////////
	// TASKS
	///////////////////////////////////////////////////////////////////////////
	
	@Key("bot.autouse.beer")
	@DefaultValue("false")
	boolean BEER();
	
	@Key("bot.autouse.minigame")
	@DefaultValue("false")
	boolean MINIGAME();

	///////////////////////////////////////////////////////////////////////////
	// STATE DELAYS
	///////////////////////////////////////////////////////////////////////////
	
	@Key("bot.delay.start.before")
	@DefaultValue("3000")
	int DELAY_BEFORE_START();
	
	@Key("bot.delay.start.after")
	@DefaultValue("5000")
	int DELAY_AFTER_START();
	
	@Key("bot.delay.waitfish.before")
	@DefaultValue("3000")
	int DELAY_BEFORE_WAIT_FISH();
	
	@Key("bot.delay.waitfish.after")
	@DefaultValue("0")
	int DELAY_AFTER_WAIT_FISH();

	@Key("bot.delay.cutfish.before")
	@DefaultValue("0")
	int DELAY_BEFORE_CUT_FISH();
	
	@Key("bot.delay.cutfish.after")
	@DefaultValue("0")
	int DELAY_AFTER_CUT_FISH();
	
	@Key("bot.delay.statuscut.before")
	@DefaultValue("0")
	int DELAY_BEFORE_STATUS_CUT();
	
	@Key("bot.delay.statuscut.after")
	@DefaultValue("1500")
	int DELAY_AFTER_STATUS_CUT();

	@Key("bot.delay.kapcha.before")
	@DefaultValue("0")
	int DELAY_BEFORE_KAPCHA();
	
	@Key("bot.delay.kapcha.after")
	@DefaultValue("10000")
	int DELAY_AFTER_KAPCHA();
	
	@Key("bot.delay.statuskapcha.before")
	@DefaultValue("0")
	int DELAY_BEFORE_STATUS_KAPCHA();
	
	@Key("bot.delay.statuskapcha.after")
	@DefaultValue("0")
	int DELAY_AFTER_STATUS_KAPCHA();

	@Key("bot.delay.filterloot.before")
	@DefaultValue("5000")
	int DELAY_BEFORE_FILTER_LOOT();
	
	@Key("bot.delay.filterloot.after")
	@DefaultValue("0")
	int DELAY_AFTER_FILTER_LOOT();

	///////////////////////////////////////////////////////////////////////////
	// NOTIFICATION
	///////////////////////////////////////////////////////////////////////////
	
	@Key("bot.notification.telegram")
	@DefaultValue("false")
	boolean TELEGRAM();
	
	@Key("bot.notification.telegram.key")
	String TELEGRAM_KEY();


	///////////////////////////////////////////////////////////////////////////
	// SLOTS
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.slot.one")
	@DefaultValue("true, 1, 0s, 10s")
	@ConverterClass(HotSlotConverter.class)
	HotSlot SLOT_ONE();

	@Key("bot.slot.two")
	@DefaultValue("true, 2, 0s, 15s")
	@ConverterClass(HotSlotConverter.class)
	HotSlot SLOT_TWO();

	@Key("bot.slot.three")
	@DefaultValue("true, 3, 0s, 20s")
	@ConverterClass(HotSlotConverter.class)
	HotSlot SLOT_THREE();


	///////////////////////////////////////////////////////////////////////////
	// FIRST SLOT
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.slot.first")
	@DefaultValue("true")
	boolean FIRST_SLOT();
	
	@Key("bot.slot.first.key")
	@DefaultValue("9")
	String FIRST_KEY_NUMBER();
	
	@Key("bot.slot.first.delay")
	@DefaultValue("0")
	int FIRST_SLOT_USE_DELAY();

	@Key("bot.slot.first.period")
	@DefaultValue("5460000")
	int FIRST_SLOT_USE_PERIOD();

	///////////////////////////////////////////////////////////////////////////
	// SECOND SLOT
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.slot.second")
	@DefaultValue("true")
	boolean SECOND_SLOT();

	@Key("bot.slot.second.key")
	@DefaultValue("8")
	String SECOND_KEY_NUMBER();

	@Key("bot.slot.second.delay")
	@DefaultValue("1860000")
	int SECOND_SLOT_USE_DELAY();

	@Key("bot.slot.second.period")
	@DefaultValue("6660000")
	int SECOND_SLOT_USE_PERIOD();

	///////////////////////////////////////////////////////////////////////////
	// THIRD SLOT
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.slot.third")
	@DefaultValue("true")
	boolean THIRD_SLOT();

	@Key("bot.slot.third.key")
	@DefaultValue("7")
	String THIRD_KEY_NUMBER();

	@Key("bot.slot.third.delay")
	@DefaultValue("3720000")
	int THIRD_SLOT_USE_DELAY();

	@Key("bot.slot.third.period")
	@DefaultValue("5460000")
	int THIRD_SLOT_USE_PERIOD();

	///////////////////////////////////////////////////////////////////////////
	// NOTIFICATION
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.pm.event.coef")
	@DefaultValue("0.1")
	double PM_COEF();
	
	@Key("bot.pm.event.exitgame")
	@DefaultValue("false")
	boolean PM_EXIT_GAME();
	
	@Key("bot.pm.event.autofish")
	@DefaultValue("false")
	boolean PM_AUTOFISH();
	
	@Key("bot.pm.event.nothing")
	@DefaultValue("true")
	boolean PM_NOTHING();

	///////////////////////////////////////////////////////////////////////////
	// SCREEN
	///////////////////////////////////////////////////////////////////////////

	@Key("bot.screen.fullscreen")
	@DefaultValue("0, 0, 1920, 1080")
	@ConverterClass(RectangleConverter.class)
	Rectangle FULL_SCREEN();

	@Key("bot.screen.space")
	@DefaultValue("928, 194, 63, 25")
	@ConverterClass(RectangleConverter.class)
	Rectangle SPACE();

	@Key("bot.screen.line")
	@DefaultValue("820, 402, 278, 25")
	@ConverterClass(RectangleConverter.class)
	Rectangle LINE();

	@Key("bot.screen.subline")
	@DefaultValue("997, 402, 10, 25")
	@ConverterClass(RectangleConverter.class)
	Rectangle SUB_LINE();

	@Key("bot.screen.statuscut")
	@DefaultValue("874, 480, 171, 33")
	@ConverterClass(RectangleConverter.class)
	Rectangle STATUS_CUT();

	@Key("bot.screen.captcha")
	@DefaultValue("780, 350, 372, 58")
	@ConverterClass(RectangleConverter.class)
	Rectangle KAPCHA();

	@Key("bot.screen.statuscaptcha")
	@DefaultValue("810, 495, 295, 85")
	@ConverterClass(RectangleConverter.class)
	Rectangle STATUS_KAPCHA();

	@Key("bot.screen.lootslotone")
	@DefaultValue("1537, 592, 47, 48")
	@ConverterClass(RectangleConverter.class)
	Rectangle LOOT_SLOT_ONE();

	@Key("bot.screen.lootslottwo")
	@DefaultValue("1584, 592, 47, 48")
	@ConverterClass(RectangleConverter.class)
	Rectangle LOOT_SLOT_TWO();

	@Key("bot.screen.chat")
	@DefaultValue("5, 1000, 355, 40")
	@ConverterClass(RectangleConverter.class)
	Rectangle CHAT();
}