package ru.namibios.bdofishbot.bot.state.service;

import ru.namibios.bdofishbot.bot.status.Status;

public class StatusService<T> {

    public T getTemplate(Status status) {

        return (T) status.getNameTemplate();
    }
}
