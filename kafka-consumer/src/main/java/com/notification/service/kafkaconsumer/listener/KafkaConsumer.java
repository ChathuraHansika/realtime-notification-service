package com.notification.service.kafkaconsumer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.service.kafkaconsumer.exception.MapperException;
import com.notification.service.kafkaconsumer.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "notification", groupId = "notification-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) {

        Notification dataConsumer = fromJson(data, Notification.class);
        log.info("Consumed message: " + data);

    }

    /**
     * Convert json to Object
     * @param json String json value
     * @param clazz Instances of the class
     * @param <T> Object Class
     * @return Object class
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }
}
