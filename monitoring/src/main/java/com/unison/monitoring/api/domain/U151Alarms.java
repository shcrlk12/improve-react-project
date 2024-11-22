package com.unison.monitoring.api.domain;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class U151Alarms {
    private final Map<String, String> map = new HashMap<>();

    @PostConstruct
    void init(){
        try {
            // DocumentBuilderFactory 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML 파일 로드
            File file = new File("src/main/resources/config/u151-wppis-config.xml");
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
    }
}
