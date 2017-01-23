package com.yiran.ruozhuo.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruozhuo on 2017/1/21.
 */
public class MySAXHandler extends DefaultHandler {

    private Map<String, Object> map = new HashMap<String, Object>();

    private String preTag = null;

    public Map<String, Object> getMap(String xmlStr) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        MySAXHandler handler = new MySAXHandler();
        InputStream xmlStream = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));
        parser.parse(xmlStream, handler);
        return handler.getMap();
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        preTag = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        preTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String content = new String(ch, start, length);
        if (preTag != null && !preTag.equals("xml")) {
            map.put(preTag, content);
        }
    }
}
