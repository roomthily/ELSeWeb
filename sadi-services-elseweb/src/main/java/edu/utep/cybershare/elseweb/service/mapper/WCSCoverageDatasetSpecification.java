package edu.utep.cybershare.elseweb.service.mapper;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class WCSCoverageDatasetSpecification {
	
	private static final String endpoint = "http://129.108.193.15:8890/sparql";
	private static final String newline = "\n";
	
	//region
	private String llat;
	private String ulat;
	private String llon;
	private String rlon;
	
	//duration
	private String startDate;
	private String endDate;
	
	//oboe
	private String characteristicURI;
	private String entityURI;
	
	public void setWCSDatasetSpecification(Resource wcsCoverageDatasetSpecificationResource){
		Resource regionResource = wcsCoverageDatasetSpecificationResource.getPropertyResourceValue(Vocab.spatial).asResource();
		Resource durationResource = wcsCoverageDatasetSpecificationResource.getPropertyResourceValue(Vocab.temporal).asResource();
		Resource measurementResource = wcsCoverageDatasetSpecificationResource.getPropertyResourceValue(Vocab.wasGeneratedBy).asResource();
		
		populateRegion(regionResource);
		populateDuration(durationResource);
		populateOBOE(measurementResource);
	}
	
	private void populateRegion(Resource regionResource){
		llon = regionResource.getPropertyResourceValue(Vocab.hasLeftLongitude).asLiteral().getString();
		rlon = regionResource.getPropertyResourceValue(Vocab.hasRightLongitude).asLiteral().getString();
		llat = regionResource.getPropertyResourceValue(Vocab.hasLowerLatitude).asLiteral().getString();
		ulat = regionResource.getPropertyResourceValue(Vocab.hasUpperLatitude).asLiteral().getString();
	}

	private String wrapXSDDateTime(String value){return "\"" + value + "\"^^<http://www.w3.org/2001/XMLSchema#dateTime>";}
	private String wrapXSDDouble(String value){return "\"" + value + "\"^^<http://www.w3.org/2001/XMLSchema#double>";}
	private String wrapResource(String value){return "<" + value + ">";}
	
	public String getLlat() {return wrapXSDDouble(llat);}
	public String getUlat() {return wrapXSDDouble(ulat);}
	public String getLlon() {return wrapXSDDouble(llon);}
	public String getRlon() {return wrapXSDDouble(rlon);}

	public String getStartDate() {return wrapXSDDateTime(startDate);}
	public String getEndDate() {return wrapXSDDateTime(endDate);}

	public String getCharacteristicURI() {return wrapResource(characteristicURI);}
	public String getEntityURI() {return wrapResource(entityURI);}

	private void populateDuration(Resource durationResource){
		startDate = durationResource.getPropertyResourceValue(Vocab.hasStartDate).asLiteral().getString();
		endDate = durationResource.getPropertyResourceValue(Vocab.hasEndDate).asLiteral().getString();
	}
	
	private void populateOBOE(Resource measurementResource){
		characteristicURI = measurementResource.getPropertyResourceValue(Vocab.ofCharacteristic).asResource().getURI();
		
		Resource observationResource = measurementResource.getPropertyResourceValue(Vocab.measurementFor).asResource();
		entityURI = observationResource.getPropertyResourceValue(Vocab.ofEntity).asResource().getURI();
	}

	private static class Vocab{
		private static Model m_model = ModelFactory.createDefaultModel();

		//ELSEWEBDATA properties
		private static final String elseweb = "http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#";
		
		//region
		public static final Property hasLeftLongitude = m_model.createProperty(elseweb + "hasLeftLongitude");
		public static final Property hasRightLongitude = m_model.createProperty(elseweb + "hasRightLongitude");
		public static final Property hasLowerLatitude = m_model.createProperty(elseweb + "hasLowerLatitude");
		public static final Property hasUpperLatitude = m_model.createProperty(elseweb + "hasUpperLatitude");

		//duration
		public static final Property hasStartDate = m_model.createProperty(elseweb + "hasStartDate");
		public static final Property hasEndDate = m_model.createProperty(elseweb + "hasEndDate");		
		
		//DCME properties
		private static final String dcmi = "http://purl.org/dc/terms/";
		public static final Property spatial = m_model.createProperty(dcmi + "spatial");
		public static final Property temporal = m_model.createProperty(dcmi + "temporal");

		//PROVO properties
		private static final String provo = "http://www.w3.org/ns/prov#";
		public static final Property wasGeneratedBy = m_model.createProperty(provo + "wasGeneratedBy");
		
		//OBOE properties
		private static final String oboe = "http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#";
		public static final Property measurementFor = m_model.createProperty(oboe + "measurementFor");
		public static final Property ofCharacteristic = m_model.createProperty(oboe + "ofCharacteristic");
		public static final Property ofEntity = m_model.createProperty(oboe + "ofEntity");
	}
	
	public Resource getWCSCoverageDistribution(){
		String queryString = this.getDistributionSPARQL();
		Query query = QueryFactory.create(queryString);

		QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
		ResultSet results = qexec.execSelect();
		
		QuerySolution solution;
		Resource distributionResource = null;
		if(results.hasNext()){
			solution = results.next();
			distributionResource = solution.getResource("?distribution");
		}
		
		return distributionResource;
	}
	
	private String getDistributionSPARQL(){
		String prefixes = getPrefixes();

		String queryBody = 
		"select ?distribution" + newline +
		"from <http://gstore.unm.edu/apps/elseweb/search/datasets.json>" + newline +
		"{" + newline +
				"?wcsCoverageDataset dcmi:spatial ?region." + newline +
				"?wcsCoverageDataset dcmi:temporal ?duration." + newline +
				"?wcsCoverageDataset dcat:distribution ?distribution." + newline +
				"?wcsCoverageDataset provo:wasGeneratedBy ?measurement." + newline +
				
				"?region elseweb:hasLeftLongitude ?llon." + newline +
				"?region elseweb:hasRightLongitude ?rlon." + newline +
				"?region elseweb:hasLowerLatitude ?llat." + newline +
				"?region elseweb:hasUpperLatitude ?ulat." + newline +

				"?duration elseweb:hasStartDate ?startDate." + newline +
				"?duration elseweb:hasEndDate ?endDate." + newline +

				"?measurement oboe:ofCharacteristic ?characteristic." + newline +
				"?measurement oboe:measurementFor ?observation." + newline +
				"?observation oboe:ofEntity ?entity." + newline +

				"FILTER(?llon = " + this.getLlon() + ")" + newline +
				"FILTER(?rlon = " + this.getRlon() + ")" + newline +
				"FILTER(?llat = " + this.getLlat() + ")" + newline +
				"FILTER(?ulat = " + this.getUlat() + ")" + newline +
				"FILTER(?startDate = " + this.getStartDate() + ")" + newline +
				"FILTER(?endDate = " + this.getEndDate() + ")" + newline +
				"FILTER(?characteristic = " + this.getCharacteristicURI() + ")" + newline +
				"FILTER(?entity = " + this.getEntityURI() + ")" + newline +
		"}";
		
		return prefixes + newline + queryBody;
	}
	
	private String getPrefixes(){
		String prefixes =
				"prefix elseweb: <http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#>" + newline +
				"prefix dcat: <http://www.w3.org/ns/dcat#>" + newline +
				"prefix provo: <http://www.w3.org/ns/prov#>" + newline +
				"prefix oboe: <http://ecoinformatics.org/oboe/oboe.1.0/oboe-core.owl#>" + newline +
				"prefix dcmi: <http://purl.org/dc/terms/>";
		return prefixes;
	}
}
