package ru.namibios.arduino.model.state.service;

import org.apache.log4j.Logger;
import ru.namibios.arduino.config.Application;
import ru.namibios.arduino.model.Touch;

import java.util.ArrayList;
import java.util.List;

public class RodService {

    private static final Logger LOG = Logger.getLogger(RodService.class);

    private static final int MAX_RODS = 8;

    private List<Touch> rots;

    private int current;

    public RodService(int count) {

        LOG.debug("Fishing rods count: " + count);
        if(count > MAX_RODS){
            throw new IllegalArgumentException("Rod count " + count + ". Max rod count = 8");
        }

        current = 0;

        this.rots = new ArrayList<>();

        int x = Application.getInstance().ROD_START_X();
        int y = Application.getInstance().ROD_START_Y();

        for (int i = 0; i < count; i++) {
            rots.add(new Touch(x, y));

            x += Application.getInstance().ROD_DX();
        }
    }

    public boolean hasNext(){
        return current < rots.size();
    }

    public String getNext(){
        Touch touch = rots.get(current);
        current++;

        return touch.toCommandRod();
    }

}
