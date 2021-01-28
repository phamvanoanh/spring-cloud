package com.edu;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class DemoHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        final Object payload = message.getPayload();

    }
}
