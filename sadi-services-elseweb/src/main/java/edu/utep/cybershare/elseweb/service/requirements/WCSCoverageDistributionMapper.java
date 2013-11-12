package edu.utep.cybershare.elseweb.service.requirements;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class WCSCoverageDistributionMapper {
	
	private static final String endpoint = "http://localhost:8890/sparql";
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
	private String characteristicClass;
	private String entityClass;
	
	//distribution related resources
	private Resource distributionResource;
	private Resource formatResource;
	private String accessURL;
	private String downloadURL;
	
	public void setWCSDistributionRequirement(Resource wcsDistributionRequirementResource){
		Resource regionResource = wcsDistributionRequirementResource.getPropertyResourceValue(Vocab.spatial);
		Resource durationResource = wcsDistributionRequirementResource.getPropertyResourceValue(Vocab.temporal);
		
		populateRegion(regionResource);
		populateDuration(durationResource);
		populateOBOE(wcsDistributionRequirementResource);
		
		setWCSCoverageDistributionResources();
	}
	
	private void populateRegion(Resource regionResource){
		llon = regionResource.getProperty(Vocab.hasLeftLongitude).getLiteral().getString();
		rlon = regionResource.getProperty(Vocab.hasRightLongitude).getLiteral().getString();
		llat = regionResource.getProperty(Vocab.hasLowerLatitude).getLiteral().getString();
		ulat = regionResource.getProperty(Vocab.hasUpperLatitude).getLiteral().getString();
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

	private void populateDuration(Resource durationResource){
		startDate = durationResource.getProperty(Vocab.hasStartDate).getLiteral().getString();
		endDate = durationResource.getProperty(Vocab.hasEndDate).getLiteral().getString();
	}
	
	private void populateOBOE(Resource wcsCoverageDistributionRequirementResource) {
		characteristicClass = wcsCoverageDistributionRequirementResource.getPropertyResourceValue(Vocab.hasCharacteristicClass).getURI();
		characteristicClass = wrapResource(characteristicClass);
	
		entityClass = wcsCoverageDistributionRequirementResource.getPropertyResourceValue(Vocab.hasEntityClass).getURI();
		entityClass = wrapResource(entityClass);
	}

	public Resource getDistribution(Model model){
		Literal accessURLLiteral = model.createLiteral(accessURL);
		Literal downloadURLLiteral = model.createLiteral(downloadURL);

		//add components to distribution
		model.add(distributionResource, Vocab.format, formatResource);		
		model.addLiteral(distributionResource, Vocab.accessURL, accessURLLiteral);
		model.addLiteral(distributionResource, Vocab.downloadURL, downloadURLLiteral);
		
		distributionResource.addProperty(Vocab.format, formatResource);
		distributionResource.addLiteral(Vocab.accessURL, accessURLLiteral);
		distributionResource.addLiteral(Vocab.downloadURL, downloadURLLiteral);
		
		return distributionResource;
	}
	
	private void setWCSCoverageDistributionResources(){
		String queryString = this.getDistributionSPARQL();

		System.out.println("distribution sparql query:");
		System.out.println(queryString);
		
		Query query = QueryFactory.create(queryString);

		QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
		ResultSet results = qexec.execSelect();
		
		QuerySolution solution;
			
		if(results.hasNext()){
			solution = results.next();
			distributionResource = solution.getResource("distribution");
			formatResource = solution.getResource("format");
			accessURL = solution.getLiteral("accessURL").getString();
			downloadURL = solution.getLiteral("downloadURL").getString();
		}
	}
	
	private String getDistributionSPARQL(){
		String prefixes = getPrefixes();

		String queryBody = 
		"select ?distribution ?format ?accessURL ?downloadURL" + newline +
		"from <http://gstore.unm.edu/apps/elseweb/search/datasets.json>" + newline +
		"{" + newline +
				"?wcsCoverageDataset dcmi:spatial ?region." + newline +
				"?wcsCoverageDataset dcmi:temporal ?duration." + newline +
				"?wcsCoverageDataset dcat:distribution ?distribution." + newline +
				"?wcsCoverageDataset provo:wasGeneratedBy ?measurement." + newline +
				
				"?distribution dcmi:format ?format." + newline +
				"?distribution dcat:accessURL ?accessURL." + newline +
				"?distribution dcat:downloadURL ?downloadURL." + newline +
				
				"?region elseweb:hasLeftLongitude ?llon." + newline +
				"?region elseweb:hasRightLongitude ?rlon." + newline +
				"?region elseweb:hasLowerLatitude ?llat." + newline +
				"?region elseweb:hasUpperLatitude ?ulat." + newline +

				"?duration elseweb:hasStartDate ?startDate." + newline +
				"?duration elseweb:hasEndDate ?endDate." + newline +

				"?measurement oboe:ofCharacteristic ?characteristic." + newline +
				"?characteristic a " + characteristicClass + "." + newline +
				"?measurement oboe:measurementFor ?observation." + newline +
				"?observation oboe:ofEntity ?entity." + newline +
				"?entity a " + entityClass + "." + newline +

				"FILTER(?llon >= " + this.getLlon() + ")" + newline +
				"FILTER(?rlon <= " + this.getRlon() + ")" + newline +
				"FILTER(?llat >= " + this.getLlat() + ")" + newline +
				"FILTER(?ulat <= " + this.getUlat() + ")" + newline +
				"FILTER(?startDate = " + this.getStartDate() + ")" + newline +
				"FILTER(?endDate = " + this.getEndDate() + ")" + newline +
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
	
	private static class Vocab{
		private static Model m_model = ModelFactory.createDefaultModel();

		//scenario properties
		private static final String scenario = "http://ontology.cybershare.utep.edu/ELSEWeb/scenario.owl#";
		public static final Property hasEntityClass = m_model.createProperty(scenario + "hasEntityClass");
		public static final Property hasCharacteristicClass = m_model.createProperty(scenario + "hasCharacteristicClass");
		
		//ELSEWEBDATA properties
		private static final String elseweb = "http://ontology.cybershare.utep.edu/ELSEWeb/elsewebdata.owl#";
		public static final Property hasLeftLongitude = m_model.createProperty(elseweb + "hasLeftLongitude");
		public static final Property hasRightLongitude = m_model.createProperty(elseweb + "hasRightLongitude");
		public static final Property hasLowerLatitude = m_model.createProperty(elseweb + "hasLowerLatitude");
		public static final Property hasUpperLatitude = m_model.createProperty(elseweb + "hasUpperLatitude");
		public static final Property hasStartDate = m_model.createProperty(elseweb + "hasStartDate");
		public static final Property hasEndDate = m_model.createProperty(elseweb + "hasEndDate");		
		
		//DCME properties
		private static final String dcmi = "http://purl.org/dc/terms/";
		public static final Property spatial = m_model.createProperty(dcmi + "spatial");
		public static final Property temporal = m_model.createProperty(dcmi + "temporal");
		public static final Property format = m_model.createProperty(dcmi + "format");
		
		//DCAT properties
		private static final String dcat = "http://www.w3.org/ns/dcat#";
		public static final Property accessURL = m_model.createProperty(dcat + "accessURL");
		public static final Property downloadURL = m_model.createProperty(dcat + "downloadURL");		
	}
}