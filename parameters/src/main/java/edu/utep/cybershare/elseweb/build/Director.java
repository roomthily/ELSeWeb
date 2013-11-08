package edu.utep.cybershare.elseweb.build;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Director {
	
	private Builder builder;
		
	public Director(Builder builder){
		this.builder = builder;
	}

	public void construct(Document xmlDocument){
		xmlDocument.getElementsByTagName("");
		
		NodeList algorithms = getAlgorithms(xmlDocument);
		Element algorithm;
		for(int i = 0; i < algorithms.getLength(); i ++){
			algorithm = (Element) algorithms.item(i);
			buildAlgorithm(algorithm);
			builder.assemble();
		}
	}
	
	private void buildAlgorithm(Element algorithm){
		String name = this.getAlgorithmName(algorithm);
		String code = this.getAlgorithmCode(algorithm);
		
		builder.buildAlgorithm(code, name);
		
		NodeList parameters = this.getParameters(algorithm);
		Element parameter;
		for(int i = 0; i < parameters.getLength(); i ++){
			parameter = (Element)parameters.item(i);
			buildParameter(parameter);
		}
	}
	
	private void buildParameter(Element parameter){
		String name = this.getParameterName(parameter);
		int min = Integer.valueOf(this.getParameterMinValue(parameter));
		int max = Integer.valueOf(this.getParameterMaxValue(parameter));
		String type = this.getParameterType(parameter);
		String defaultValue = this.getParameterDefaultValue(parameter);
		
		builder.buildParameter(name, min, max, type, defaultValue);
	}
	
	private NodeList getAlgorithms(Document doc){
		return doc.getElementsByTagName("algorithm");
	}
	
	private String getAlgorithmCode(Element algorithm){
		return algorithm.getAttribute("code");
	}
	
	private String getAlgorithmName(Element algorithm){
		return algorithm.getAttribute("name");
	}
	
	private NodeList getParameters(Element algorithm){
		return algorithm.getElementsByTagName("parameter");
	}
	
	private String getParameterName(Element parameter){
		return parameter.getAttribute("name");
	}
	
	private String getParameterMinValue(Element parameter){
		return parameter.getAttribute("min");
	}
	private String getParameterMaxValue(Element parameter){
		return parameter.getAttribute("min");
	}
	private String getParameterDefaultValue(Element parameter){
		return parameter.getAttribute("min");
	}
	private String getParameterType(Element parameter){
		return parameter.getAttribute("type");
	}
}
