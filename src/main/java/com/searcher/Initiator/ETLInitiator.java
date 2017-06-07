package com.searcher.Initiator;

import com.searcher.model.virtualizationModule.ETLDumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

@Controller
class ETLInitiator implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    ETLDumper etlDumper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ETLDumper ETLDumper = new ETLDumper();
        Thread dumperThread = new Thread(ETLDumper);
        dumperThread.start();
    }
}