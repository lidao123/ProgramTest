package Util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XMLUtil {
	//读取文件
	public static Document getDocument() {
		try {
			Document document = new SAXReader().read("d:/ProgramTest/CookieTest/product.xml");
			return document;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//写出文件
	public static void writexml(Document doc){
		try {
			OutputStream out = new FileOutputStream("d:/ProgramTest/CookieTest/product.xml");
			//规定格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
