package learn;

import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * 学习解析xml
 */
public class ParseXmlTest {

    @Test
    public void test1() throws Exception {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        // 或者
        // XMLInputFactory factory = XMLInputFactory.newFactory();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("users.xml");
        XMLStreamReader xmlReader = factory.createXMLStreamReader(in);

        while (xmlReader.hasNext()) {

            //int event = xmlReader.nextTag();
            int event = xmlReader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {
                if ("user".equalsIgnoreCase(xmlReader.getLocalName())) {
                    System.out.println("Name:"
                            + xmlReader.getAttributeValue(null, "name")
                            + ";Age:"
                            + xmlReader.getAttributeValue(null, "age"));
                }
            }


/**
             int event = xmlReader.next();

             String eventCode = eventCode(event);
             if (!"CHARACTERS".equals(eventCode) && !"END_DOCUMENT".equals(eventCode)) {
             System.out.println(eventCode + ":" + xmlReader.getLocalName());
             }else {
             System.out.println(eventCode);
             }
 */


/**
 if (event == XMLStreamConstants.START_ELEMENT) {
 if ("user".equalsIgnoreCase(xmlReader.getLocalName())) {
 System.out.println("Name:" + xmlReader.getAttributeValue(null, "name"));
 }
 }
 */
        }
        xmlReader.close();
    }


    private String eventCode(int event) {

        switch (event) {
            case XMLStreamConstants.START_ELEMENT:
                return "START_ELEMENT";
            case XMLStreamConstants.END_ELEMENT:
                return "END_ELEMENT";
            case XMLStreamConstants.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLStreamConstants.CHARACTERS:
                return "CHARACTERS";
            case XMLStreamConstants.COMMENT:
                return "COMMENT";
            case XMLStreamConstants.SPACE:
                return "SPACE";
            case XMLStreamConstants.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLStreamConstants.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLStreamConstants.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLStreamConstants.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLStreamConstants.DTD:
                return "DTD";
            case XMLStreamConstants.CDATA:
                return "CDATA";
            case XMLStreamConstants.NAMESPACE:
                return "NAMESPACE";
            case XMLStreamConstants.NOTATION_DECLARATION:
                return "NOTATION_DECLARATION";
            case XMLStreamConstants.ENTITY_DECLARATION:
                return "ENTITY_DECLARATION";
            default:
                return "code not exists";
        }
    }


}
