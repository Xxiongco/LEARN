package second;

import com.sun.org.apache.xerces.internal.impl.XMLStreamReaderImpl;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.apache.commons.io.input.XmlStreamReader;
import org.junit.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class LearnBookTest {

    /**
     * 根据流程文件，获取BpmnbModel对象
     */
    @Test
    public void getBpmnFromResource() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagram/hello.bpmn20.xml");

        InputStreamSource inputStreamSource = new InputStreamSource(inputStream);

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(inputStreamSource, true, false, "Utf-8");

        System.out.println(bpmnModel);

        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel);

        System.out.println(new String(bytes));

    }

    @Test
    public void xmlParse() throws XMLStreamException, IOException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagram/hello.bpmn20.xml");
        Reader reader = new XmlStreamReader(in);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(reader);

        while (xmlStreamReader.hasNext()) {
            int event = xmlStreamReader.next();
            if (event == XMLStreamConstants.START_DOCUMENT) {

            }else if (event == XMLStreamConstants.END_DOCUMENT){

            }else if(event == XMLStreamConstants.START_ELEMENT) {
                String localName = xmlStreamReader.getLocalName();
                System.out.println(localName);
                if ("userTask".equals(localName) || "endEvent".equals(localName)) {
                    for ( int i = 0; i< xmlStreamReader.getAttributeCount( ); i++){
                        if (xmlStreamReader.getAttributeName(i).toString().startsWith("{")) {
                            System.out.print( " --->>>" + xmlStreamReader.getAttributeValue("http://activiti.org/bpmn","assignee"));
                        }else {
                            System.out.println(xmlStreamReader.getAttributeName(i) + ":" + xmlStreamReader.getAttributeValue(i));
                        }

                    }

                }

            }else if(event == XMLStreamConstants.END_ELEMENT) {


            }

        }



    }
}
