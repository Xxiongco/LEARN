package learn;

import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;


/**
 * 学习解析xml
 * STAX解析XML有两种方式：
 * - 基于指针的API
 * - 基于迭代器的API
 */
public class ParseXmlTest {
    /**
     * 基于指针
     *
     * @throws Exception
     *
     */
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


    /**
     * 基于迭代器
     * <p>
     * 解析user
     * 1. StartDocumentEvent （文档的开始，<xml>）
     * 2. StartElementEvent (<company>)
     * 3. CharacterEvent
     * 4. StartElementEvent (<depart>)
     * 5. CharacterEvent
     * 6. StartElementEvent (<user>)
     * <p>
     * 也就是说，每一个事件之后，都是CharacterEvent，除了StartDocumentEvent
     */
    @Test
    public void test2() throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("users.xml");
        XMLEventReader eventReader = factory.createXMLEventReader(in);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            String name = event.getClass().getName();
            if (event.isStartElement()) {
                // 转换成开始元素事件对象
                StartElement start = event.asStartElement();
                // 打印元素标签的本地名称
                System.out.print(start.getName().getLocalPart());
                // 取得所有属性
                Iterator attrs = start.getAttributes();

                while (attrs.hasNext()) {
                    // 打印所有属性信息
                    Attribute attr = (Attribute) attrs.next();
                    System.out.print(" : " + attr.getName().getLocalPart()
                            + "=" + attr.getValue());
                }
                System.out.println();
            }
        }
    }

    /**
     *  使用filter 处理只需要处理的元素
     * @throws Exception
     */
    @Test
    public void testWithFilter() throws Exception {

        XMLInputFactory factory = XMLInputFactory.newInstance();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("users.xml");
        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(in);
        // 创建带有过滤器的读取器实例

        XMLStreamReader filteredReader = factory.createFilteredReader(xmlStreamReader, new TestStreamFilter());
        while (filteredReader.hasNext()) {
            // 过滤工作已交由过滤器完成，这里不需要再做
            System.out.println("Name="
                    + filteredReader.getAttributeValue(null, "name"));

            if (filteredReader.getEventType() != XMLStreamConstants.END_DOCUMENT) {
                filteredReader.next();
            }
        }
        assert in != null;
        in.close();
        filteredReader.close();
    }


}