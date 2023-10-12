package com.phototeca.cryptocurrencybot.bot;


import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    private final MessageDispatcher messageDispatcher;

    public Bot(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getFrom().getId();

        }
        System.out.println(update);
        messageDispatcher.consume(this, update, chatId);

    }


    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @PostConstruct
    public void botConnect() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
//          httpSender.sendNotify(admin_id, "Bot unavailable");
        }
    }
}
