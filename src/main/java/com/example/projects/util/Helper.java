package com.example.projects.util;

import com.example.projects.model.Bike;
import com.example.projects.model.Markers;
import com.example.projects.model.Station;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GTaggart on 02/03/2018.
 */
public class Helper {

    private static final String BELFAST_BIKES_URL = "https://iframe.nextbike.net/maps/nextbike-live.xml?&city=238";

    public static Markers getData() throws IOException, JAXBException {

//        SocketAddress addr = new
//                InetSocketAddress("bfs-admin-003", 3128);
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
//
//        URL url = new URL("https://iframe.nextbike.net/maps/nextbike-live.xml?city=238");
//        URLConnection conn = url.openConnection(proxy);
//
        File file = new File("D:\\Documents\\Projects\\BikeWebapp\\src\\main\\resources\\bikes.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Markers.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//        URL url = new URL(BELFAST_BIKES_URL);
//        JAXBContext jaxbContext = JAXBContext.newInstance(Markers.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (Markers) unmarshaller.unmarshal(file);
    }

    public static int calDurationInMinutes(long time) {
        //long seconds = time / 1000 % 60;
        return (int) Math.ceil(time / (60 * 1000) % 60);
    }
}
