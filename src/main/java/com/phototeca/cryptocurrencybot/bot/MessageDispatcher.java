package com.phototeca.cryptocurrencybot.bot;

import com.phototeca.cryptocurrencybot.domain.UserActivity;
import com.phototeca.cryptocurrencybot.service.UserActivityService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class MessageDispatcher {

    private final UserActivityService userActivityService;
    private final MessageSender messageSender;

    public MessageDispatcher(UserActivityService userActivityService, MessageSender messageSender) {
        this.userActivityService = userActivityService;
        this.messageSender = messageSender;
    }


    public void consume(Bot bot, Update update, long chatId) {
        UserActivity userActivity = readUserInfoFromDb(chatId, update);
        messageSender.sendState(bot,update,chatId);
        userActivityService.updateUserActivity(userActivity);

    }

    private UserActivity readUserInfoFromDb(long chatId, Update update) {
        return userActivityService.getUserActivity(chatId, update);
    }

}