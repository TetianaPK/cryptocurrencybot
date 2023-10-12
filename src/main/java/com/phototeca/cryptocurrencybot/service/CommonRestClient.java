package com.phototeca.cryptocurrencybot.service;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
@Component
public class CommonRestClient {

    public String readUrl(String urlString) throws IOException {
        try (Scanner scanner = new Scanner(new URL(urlString).openStream(), String.valueOf(StandardCharsets.UTF_8))) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (FileNotFoundException e) {
            return "";
        }
    }
}
