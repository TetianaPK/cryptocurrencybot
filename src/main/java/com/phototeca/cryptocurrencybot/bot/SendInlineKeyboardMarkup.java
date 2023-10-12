package com.phototeca.cryptocurrencybot.bot;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SendInlineKeyboardMarkup {


    public void sendLanguageInlineKeyboardMarkup(Long chatId, Bot bot) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Update price time point");
        inlineKeyboardButton1.setCallbackData("update");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton1);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Click button for update price time point");
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
