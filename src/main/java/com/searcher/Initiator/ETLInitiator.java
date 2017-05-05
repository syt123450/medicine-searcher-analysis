package com.searcher.Initiator;

import com.searcher.model.ETLDumper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

@Controller
class ETLInitiator implements ApplicationListener<ContextRefreshedEvent>{

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ETLDumper ETLDumper = new ETLDumper();
        Thread dumperThread = new Thread(ETLDumper);
        dumperThread.start();
    }
}