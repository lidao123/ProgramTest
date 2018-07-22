package Dao;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import Entity.Product;
import Util.XMLUtil;


public class ProductOperator {
	public List<Product> findAll(){
		Document doc = XMLUtil.getDocument();
		List<Element> elements = doc.getRootElement().elements("product");
		List<Product> list = new ArrayList();
		for (Element proEle : elements) {
			Product pro = new Product();
			pro.setId(proEle.attributeValue("id"));
			pro.setName(proEle.elementText("name"));
			pro.setType(proEle.elementText("type"));
			pro.setPrice(proEle.elementText("price"));
			list.add(pro);
		}
		return list;
		
	}
	
	public Product findById(String id){
		Document doc = XMLUtil.getDocument();
		Element element = (Element)doc.selectSingleNode("//product[@id='"+id+"']");
		Product pro = null;
		if(element != null){
			pro = new Product();
			pro.setId(element.attributeValue("id"));
			pro.setName(element.elementText("name"));
			pro.setType(element.elementText("type"));
			pro.setPrice(element.elementText("price"));
		}
		return pro;
	}
	
	
	public static void main(String[] args) {
		ProductOperator dao = new ProductOperator();
		List<Product> list = dao.findAll();
		for (Product product : list) {
			System.out.println(product);
		}
		Product pro0 = dao.findById("1");
		System.out.println(pro0);
	}
	
}
