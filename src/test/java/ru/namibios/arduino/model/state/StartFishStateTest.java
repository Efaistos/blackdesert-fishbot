package ru.namibios.arduino.model.state;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ru.namibios.arduino.model.command.Command;
import ru.namibios.arduino.model.state.service.input.InputService;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class StartFishStateTest {

    @Mock
    private FishBot fishBot;

    @Mock
    private InputService inputService;

    @InjectMocks
    private StartFishState startFishState;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStart() throws IOException {

        Mockito.when(inputService.send(any(Command.class))).thenReturn(true);

        startFishState.onStep();

        Mockito.verify(inputService).send(any(Command.class));
        Mockito.verify(fishBot).setState(isA(PersonalMessageState.class));

    }

}