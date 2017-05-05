package com.searcher.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by ss on 2017/5/5.
 */
public class SocketSender implements Runnable{


    private SimpMessagingTemplate simpMessagingTemplate;
    private Gson gson = new GsonBuilder().create();

    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void run() {
        while(true) {
            RealtimeDataGetter realtimeDataGetter = new RealtimeDataGetter();
            String socketMessagePacket = realtimeDataGetter.get();
            simpMessagingTemplate.convertAndSend("/topic/getResponse", socketMessagePacket);
            try {
                Thread.sleep(2000);
            }
            catch (Exception e) {

            }
        }
    }
}
