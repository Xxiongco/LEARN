package book;


import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class BookLearn04 {

    @Test
    public void parseXml() throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagram/hello.bpmn20.xml");

        InputStreamSource inputStreamSource = new InputStreamSource(inputStream);

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(inputStreamSource, true, false, "Utf-8");

        System.out.println(bpmnModel);

        assert inputStream != null;
        inputStream.close();


    }


}
