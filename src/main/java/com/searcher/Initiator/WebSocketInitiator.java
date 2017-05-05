package com.searcher.Initiator;

import com.searcher.model.SocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by ss on 2017/4/12.
 */

@Controller
class WebSocketInitiator implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        SocketSender socketSender = new SocketSender();
        socketSender.setSimpMessagingTemplate(template);
        Thread socketSenderThread = new Thread(socketSender);
        socketSenderThread.start();
    }
}