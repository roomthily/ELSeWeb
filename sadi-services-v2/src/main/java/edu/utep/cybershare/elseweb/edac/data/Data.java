package edu.utep.cybershare.elseweb.edac.data;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public abstract class Data {
	
	private Model model;
	private Resource dataResource;
	private Resource sourceResource;
	
	public Data(String dataURI, Model dataModel, Resource dataTypeResource, Resource dataSourceResource){
		model = dataModel;
		dataResource = model.createResource(dataURI, dataTypeResource);
		sourceResource = dataSourceResource;
		
		dataResource = model.createResource(dataURI, dataTypeResource);
	}
	

	public Resource getDataResource(){
		return dataResource;
	}
	
	public void addRegion(double llon, double rlon, double llat, double ulat, String regionURI){
		Resource regionResource = model.createResource(regionURI, Vocab.Region);
		Literal lit_llon = model.createTypedLiteral(llon);
		Literal lit_rlon = model.createTypedLiteral(rlon);
		Literal lit_llat = model.createTypedLiteral(llat);
		Literal lit_ulat = model.createTypedLiteral(llat);
		
		model.add(regionResource, Vocab.hasLeftLongitude, lit_llon);
		model.add(regionResource, Vocab.hasRightLongitude, lit_rlon);
		model.add(regionResource, Vocab.hasLowerLatitude, lit_llat);
		model.add(regionResource, Vocab.hasUpperLatitude, lit_ulat);		
		model.add(dataResource, Vocab.hasRegion, regionResource);
	}
	
	public void addDuration(String startDate, String endDate, String durationURI){
		Resource durationResource = model.createResource(durationURI, Vocab.Duration);
		Literal lit_startDate = model.createTypedLiteral(startDate);
		Literal lit_endDate = model.createTypedLiteral(endDate);
		model.add(durationResource, Vocab.hasStartDate, lit_startDate);
		model.add(durationResource, Vocab.hasEndDate, lit_endDate);
		model.add(dataResource, Vocab.hasDuration, durationResource);
	}
	
	public void addSource(){
		model.add(dataResource, Vocab.hasSource, sourceResource);
	}
	
	public static final class Vocab {

		protected static Model m_model = ModelFactory.createDefaultModel();

		// Data Properties
		public static final Property hasRegion = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasRegion");
		public static final Property hasSource = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasSource");
		
		// Region Properties
		public static final Property hasUpperLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasUpperLatitude");
		public static final Property hasDuration = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasDuration");
		public static final Property hasLeftLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasLeftLongitude");
		public static final Property hasLowerLatitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasLowerLatitude");

		// Duration Properties
		public static final Property hasEndDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasEndDate");
		public static final Property hasStartDate = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasStartDate");
		public static final Property hasRightLongitude = m_model.createProperty("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#hasRightLongitude");

		// Extents
		public static final Resource Region = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#Region");
		public static final Resource Duration = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#Duration");
		
		// Measurements
		public static final Resource FractionalSnowData = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#FractionalSnowCover");
		public static final Resource MinTemperatureNormals = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#MinTemperatureNormals");
		
		// Sources
		public static final Resource MODIS = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#MODIS");
		public static final Resource PRISM = m_model.createResource("https://raw.github.com/nicholasdelrio/ELSeWeb/master/documents/semantic-web/rdf/ontology/edac-v2.owl#PRISM");
	}
}