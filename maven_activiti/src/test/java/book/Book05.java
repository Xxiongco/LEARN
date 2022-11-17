package book;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Book05 {

    @Test
    public void parseXml() throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("book/book05.bpmn20.xml");
        InputStreamSource inputStreamSource = new InputStreamSource(inputStream);
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(inputStreamSource, true, false, "Utf-8");

        List<Process> processes = bpmnModel.getProcesses();

        Process process = processes.get(0);

        FlowElement flowElement = process.getFlowElement("userTask");

        Map<String, List<ExtensionElement>> extensionElements = flowElement.getExtensionElements();

        for (Map.Entry<String, List<ExtensionElement>> entry : extensionElements.entrySet()) {
            String key = entry.getKey();
            List<ExtensionElement> extensionElementList = entry.getValue();
            for (ExtensionElement extensionElement : extensionElementList) {
                Map<String, List<ExtensionAttribute>> attributes = extensionElement.getAttributes();
                for (Map.Entry<String, List<ExtensionAttribute>> attribute: attributes.entrySet()) {
                    System.out.println(key + ":" + attribute.getKey() + ":" + attribute.getValue());
                }
            }
        }



        assert inputStream != null;
        inputStream.close();


    }



}
