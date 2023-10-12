package com.phototeca.cryptocurrencybot.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TwoElementsQueue<T> {
    private Queue<List<T>> queue = new LinkedList<>();

    public void enqueue(List<T> element) {
        if (queue.size() == 2) {
            queue.poll();
        }
        queue.offer(element);
    }

    public Queue<List<T>> getQueue() {
        return queue;
    }
}