package ru.namibios.bdofishbot.bot.state;

import org.apache.log4j.Logger;
import ru.namibios.bdofishbot.config.Message;
import ru.namibios.bdofishbot.config.Path;
import ru.namibios.bdofishbot.utils.ExceptionUtils;
import ru.namibios.bdofishbot.utils.PythonExec;

import java.io.File;
import java.io.IOException;

public class WarmUpJvmState extends State {

    private static final Logger LOG = Logger.getLogger(ChangeRodState.class);

    WarmUpJvmState(FishBot fishBot) {
        super(fishBot);

        this.beforeStart = 0;
        this.afterStart = 0;
    }

    @Override
    public void onStep() {

        try {

            LOG.info("Start warm up..");

            File[] files = new File(Path.TEST_RESOURCES + "parsing/captcha").listFiles();

            for (File file : files) {
                String absolute = file.getAbsolutePath();
                PythonExec.exec(absolute);
            }

            fishBot.setState(new UseSlotState(fishBot));

        } catch (IOException e) {
            LOG.info(String.format(Message.LOG_FORMAT_ERROR, e));
            LOG.error(ExceptionUtils.getString(e));
        }

    }
}
