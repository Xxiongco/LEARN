package learn;

import javax.xml.stream.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * StreamFilter示例程序
 *
 * @author zangweiren 2010-4-19
 * 
 */
public class TestStreamFilter implements StreamFilter {

	@Override
	public boolean accept(XMLStreamReader reader) {
		try {
			while (reader.hasNext()) {
				int event = reader.next();
				// 只接受元素的开始
				if (event == XMLStreamConstants.START_ELEMENT) {
					// 只保留user元素
					if ("user".equalsIgnoreCase(reader.getLocalName())) {
						return true;
					}
				}
				if (event == XMLStreamConstants.END_DOCUMENT) {
					return true;
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return false;
	}

}