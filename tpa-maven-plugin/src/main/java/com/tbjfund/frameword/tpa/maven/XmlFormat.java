package com.tbjfund.frameword.tpa.maven;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;

/**
 * Created by sidawei on 16/4/17.
 */
public class XmlFormat {

    public String formatXML(String buffer) throws IOException {
        ByteArrayInputStream inputTmp = new ByteArrayInputStream(buffer.getBytes());
        SAXBuilder sb = new SAXBuilder();
        Document doc = null;
        try {
            doc = sb.build(inputTmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        out.output(doc, outBuffer);
        return new String(outBuffer.toByteArray());
    }

}
