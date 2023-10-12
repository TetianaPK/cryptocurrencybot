package com.phototeca.cryptocurrencybot.service;

import com.phototeca.cryptocurrencybot.domain.State;
import com.phototeca.cryptocurrencybot.domain.UserActivity;
import com.phototeca.cryptocurrencybot.repository.UserActivityRepository;
import com.phototeca.cryptocurrencybot.util.JsonParserUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;
    private final PriceChecker priceChecker;

    public UserActivityService(UserActivityRepository userActivityRepository, PriceChecker priceChecker) {
        this.userActivityRepository = userActivityRepository;
        this.priceChecker = priceChecker;
    }

    public UserActivity getUserActivity(long chatId, Update update) {
        return userActivityRepository.findById(chatId).orElse(createNewUserActivity(update));
    }

    private UserActivity createNewUserActivity(Update update) {
        UserActivity userActivity = new UserActivity();
        userActivity.setState(State.DEFAULT);
        if (update.hasMessage()) {
            userActivity.setFirstName(update.getMessage().getFrom().getFirstName());
            userActivity.setLastName(update.getMessage().getFrom().getLastName());
            userActivity.setUserName(update.getMessage().getFrom().getUserName());
            userActivity.setChatId(update.getMessage().getChatId());
            return userActivityRepository.save(userActivity);
        }
        return userActivity;
    }

    public void updateUserActivity(UserActivity userActivity) {
        userActivityRepository.save(userActivity);
    }

    public void updateUserPrice(long chatId, Update update) {
        UserActivity userActivity = getUserActivity(chatId, update);
        String jsonData = JsonParserUtil.serialize(priceChecker.getActualPrice());
        userActivity.setUserPrice(jsonData);
        updateUserActivity(userActivity);
    }
}
