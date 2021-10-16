package com.mycompany.frs_maven.service;

import java.io.FileInputStream;

import java.lang.reflect.Constructor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.frs_maven.exceptions.ServiceLoadException;

public class Factory {
	
	static private Logger logger = LogManager.getLogger();
	
	/* Singleton Design Pattern */
	private static Factory factory = new Factory();
	private Factory() {};
	public static Factory getInstance() { 
		return factory; 
	}
	
	public IService getService(String name) throws ServiceLoadException {
		try {
			Class<?> cls = Class.forName(getImplName(name));
			Constructor<?> constructor = cls.getConstructor();
			IService obj = (IService) constructor.newInstance();
			return obj;
		}
		catch (Exception e) {
			throw new ServiceLoadException(name + " not loaded");
		}
	}
	
	private String getImplName(String name) throws Exception {
		String result = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new FileInputStream("src/main/java/com/mycompany/frs_maven/service/services.xml"));
			Element root = document.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if(node instanceof Element) {
					Element element = (Element) node;
					String interf = element.getAttribute("interface");
					String impl = element.getAttribute("implementation");
					if(interf.equals(name)) {
						result = impl;
					}
				}
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
}
