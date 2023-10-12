package com.phototeca.cryptocurrencybot.bot;

import com.phototeca.cryptocurrencybot.service.UserActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class MessageSender {
    private UserActivityService userActivityService;

    public void sendState(Bot bot, Update update, long chatId) {

        SendInlineKeyboardMarkup sendInlineKeyboardMarkup = new SendInlineKeyboardMarkup();
        sendInlineKeyboardMarkup.sendLanguageInlineKeyboardMarkup(chatId, bot);
        if (update.hasCallbackQuery()&&update.getCallbackQuery().getData().equals("update")) {
            userActivityService.updateUserPrice(chatId,update);
            SendTextMessage sendTextMessage = new SendTextMessage();
            sendTextMessage.sendTextMessage("Price point updated", chatId, bot);
        }

    }
}
