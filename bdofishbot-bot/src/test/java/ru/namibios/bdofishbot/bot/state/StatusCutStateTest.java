package ru.namibios.bdofishbot.bot.state;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ru.namibios.bdofishbot.bot.service.StatusService;
import ru.namibios.bdofishbot.bot.status.Status;
import ru.namibios.bdofishbot.bot.status.StatusCut;
import ru.namibios.bdofishbot.bot.template.StatusCutTemplate;
import ru.namibios.bdofishbot.cli.Application;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatusCutStateTest {

    @Mock
    private FishBot fishBot;

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusCutState statusCutState;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testPerfect() {

        when(statusService.getTemplate(any(Status.class))).thenReturn(StatusCutTemplate.PERFECT);

        statusCutState.onStep();

        verify(fishBot).setState(isA(FilterLootState.class));
        verify(statusService).getTemplate(isA(Status.class));
    }

    @Test
    public void testGood() {

        when(statusService.getTemplate(any(Status.class))).thenReturn(StatusCutTemplate.GOOD);

        statusCutState.onStep();

        verify(fishBot).setState(isA(CaptchaState.class));
        verify(statusService).getTemplate(isA(Status.class));

    }

    @Test
    public void testBad() {

        when(statusService.getTemplate(any(Status.class))).thenReturn(StatusCutTemplate.BAD);

        statusCutState.onStep();

        verify(fishBot).setState(isA(StartFishState.class));
        verify(statusService).getTemplate(isA(Status.class));

    }

    @Test
    public void testOverflow() {

        int overflow = Application.getInstance().STATE_CUT_OVERFLOW() + 1;

        when(statusService.getTemplate(any(Status.class))).thenReturn(null);

        for (int i = 0; i < overflow; i++) {
            statusCutState.onStep();
        }

        verify(statusService, times(overflow)).getTemplate(isA(StatusCut.class));
        verify(fishBot, atLeastOnce()).setState(any(FilterLootState.class));

    }

    @Test
    public void testThrowException(){

        when(statusService.getTemplate(any(Status.class))).thenThrow(new NullPointerException("Test Exception"));

        statusCutState.onStep();

        verify(fishBot).setState(isA(CaptchaState.class));

    }

}