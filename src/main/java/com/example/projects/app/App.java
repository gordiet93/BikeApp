package com.example.projects.app;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.ejb.Singleton;
import java.util.logging.Logger;
import com.example.projects.service.StationService;
import javax.ejb.Timer;

/**
 * Created by GTaggart on 02/03/2018.
 */
@Singleton
@Startup
public class App {

    @Inject
    private StationService stationService;

    @Inject
    private Logger log;

    @Resource
    private TimerService timerService;

    @Timeout
    public void scheduler() {
        stationService.checkArrivalsAndDepartures();
    }

    @PostConstruct
    public void initialize() {

        stationService.registerStations();

        ScheduleExpression se = new ScheduleExpression();
        // Set schedule to every min (starting at second 0 of every minute).
        se.hour("*").minute("*");//.second("0/1");
        timerService.createCalendarTimer(se, new TimerConfig("EJB timer service timeout at ", false));
    }

    @PreDestroy
    public void stop() {
        System.out.println("EJB Timer: Stop timers.");
        for (Timer timer : timerService.getTimers()) {
            System.out.println("Stopping timer: " + timer.getInfo());
            timer.cancel();
        }
    }
}
