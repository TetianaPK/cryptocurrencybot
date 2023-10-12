package com.phototeca.cryptocurrencybot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.phototeca.cryptocurrencybot.dto.TwoElementsQueue;
import com.phototeca.cryptocurrencybot.dto.PriceDTO;
import com.phototeca.cryptocurrencybot.repository.UserActivityRepository;
import com.phototeca.cryptocurrencybot.util.JsonParserUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PriceChecker {
    private HttpSender httpSender;
    TwoElementsQueue<PriceDTO> priceQueue = new TwoElementsQueue<>();
    private CommonRestClient restClient;
    private final UserActivityRepository userActivityRepository;

    @Scheduled(fixedRate = 8_000)
    private void priceLoader() {
        priceQueue.enqueue(getActualPrice()); //convert to map before enqueue
    }

    //    TODO- use in memory db  for fast test
//    need migrate to db for do smt. with data
//    split the code into separate services
    @Scheduled(fixedRate = 20_000)
    private void priceChecker() {
        Queue<List<PriceDTO>> queue = priceQueue.getQueue();

        List<Map<String, Double>> maps = new ArrayList<>();

        for (List<PriceDTO> prices : queue) {
            Map<String, Double> priceMap = prices.stream()
                    .collect(Collectors.toMap(
                            PriceDTO::getSymbol,
                            PriceDTO::getPrice,
                            (existing, replacement) -> replacement
                    ));
            maps.add(priceMap);
        }
        if (maps.size() == 2) {
            Map<String, Double> differences = calculateDifferences(maps);
            Map<String, Double> filteredMap = differences.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 5)//>5%  change to N percent
                    .collect(HashMap::new, (m, entry) -> m.put(entry.getKey(), entry.getValue()), HashMap::putAll);

            if (filteredMap.size() > 0) {
                userActivityRepository.findAll().forEach(e -> {
                    boolean resultNotification = httpSender.sendNotify(e.getChatId(), "Price change > 5% for currency in % : " + filteredMap);
                    if (!resultNotification) {
                        //notify admin or do other things
                    }
                });
            }

        }

    }

    private static Map<String, Double> calculateDifferences(List<Map<String, Double>> maps) {

        Set<String> allKeys = maps.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());


        Map<String, Double> differences = new HashMap<>();
        for (String key : allKeys) {
            differences.put(key, calculateDifferenceForKey(maps, key));
        }

        return differences;
    }


    private static Double calculateDifferenceForKey(List<Map<String, Double>> maps, String key) {
        if (maps.size() < 2) {
            return 0.0;
        }

        double firstValue = maps.get(0).getOrDefault(key, 0.0);
        double lastValue = maps.get(maps.size() - 1).getOrDefault(key, 0.0);

        if (firstValue == 0.0) {
            return 0.0;
        }

        return ((lastValue - firstValue) / Math.abs(firstValue)) * 100.0;
    }

    @SneakyThrows
    public List<PriceDTO> getActualPrice() {
        String jsonData = restClient.readUrl("https://api.mexc.com/api/v3/ticker/price");//move to property
        return JsonParserUtil.deSerialize(jsonData, new TypeReference<List<PriceDTO>>() {
        });
    }
}
