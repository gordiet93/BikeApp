package com.example.projects.rest;

/**
 * Created by GTaggart on 15/03/2018.
 */
//import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
    private Set<Object> singletons;

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> clazzes = new HashSet<>();
        clazzes.add(BikeRestService.class);
        clazzes.add(JourneyRestService.class);
        clazzes.add(StationRestService.class);
        clazzes.add(ConstraintViolationMapper.class);
        //clazzes.add(EventUpdateRestService.class);
        return clazzes;
    }

//    @Override
//    public Set<Object> getSingletons() {
//        if (singletons == null) {
//            CorsFilter corsFilter = new CorsFilter();
//            corsFilter.getAllowedOrigins().add("*");
//
//            singletons = new LinkedHashSet<Object>();
//            singletons.add(corsFilter);
//        }
//        return singletons;
//    }
}
