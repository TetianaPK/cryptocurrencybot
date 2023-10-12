package com.phototeca.cryptocurrencybot.service;

import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@NoArgsConstructor
public class HttpSender {


    @Value("${bot.token}")
    private String token;

    private static final Logger log = LoggerFactory.getLogger(HttpSender.class);

    private final OkHttpClient httpClient = new OkHttpClient();

    public boolean sendNotify(long chatId, String text) {
        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + text)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            boolean successful = response.isSuccessful();
            log.info("sendNotify {} {} {} {}", successful, response.code(), chatId, text);
            return successful;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
