package com.unison.monitoring.api.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AlarmsConfig {
    private Map<String, String> u151Map = new HashMap<>();
    private Map<String, String> u113Map = new HashMap<>();
    private Map<String, String> u120Map = new HashMap<>();

    @PostConstruct
     void init() {
        //u151
        u151Map = configAlarmsMap("src/main/resources/config/u151-wppis-config.xml");

        //u113
        u113Map = configAlarmsMap("src/main/resources/config/u113-wppis-config.xml");

        //u120 test
        u120Map = configAlarmsMap("src/main/resources/config/u113-wppis-config.xml");
    }

    @Bean
    public Map<String, String> u151AlarmMap() {
        return Collections.unmodifiableMap(u151Map);
    }

    @Bean
    public Map<String, String> u113AlarmMap() {
        return Collections.unmodifiableMap(u113Map);
    }

    @Bean
    public Map<String, String> u120AlarmMap() {
        return Collections.unmodifiableMap(u120Map);
    }

    private Map<String, String> configAlarmsMap(String path){
        Map<String, String> map = new HashMap<>();

        try {
            // DocumentBuilderFactory 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML 파일 로드
            File file = new File(path);
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            Element wppis = document.getDocumentElement();
            Element windTurbine = (Element) wppis.getElementsByTagName("WindTurbine").item(0);
            Element walm = (Element) windTurbine.getElementsByTagName("WALM").item(0);
            Element alarmMsg = (Element) walm.getElementsByTagName("AlarmMsg").item(0);

            NodeList alarms = alarmMsg.getChildNodes();
            for (int i = 0; i < alarms.getLength(); i++) {
                Node node = alarms.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    map.put(element.getNodeName(), element.getAttribute("description"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
