package edu.utep.cybershare.elseweb.prov.namedGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.prov.ModelUtils;

public class NamedGraph {
	private static final String literalURI = "http://www.w3.org/2000/01/rdf-schema#Literal";
	
	private Resource contents;
	private OntModel model;
	private OntClass graphClass;
	private String graphURI;
	private String graphFilePath;
	private String graphClassURI;
	private boolean isDumped;
	private List<NamedGraphComponent> graphComponents;
	
	public NamedGraph(Resource graphContents, String graphURI, String graphClassURI, String graphFilePath){
		this.contents = graphContents;
		this.graphURI = graphURI;
		this.graphFilePath = graphFilePath;
		this.graphClassURI = graphClassURI;
		
		if(graphClassURI == null)
			throw new IllegalArgumentException("Class URI must not be null");
		
		this.isDumped = false;
		setModel();
		delineateGraphComponents();
	}
	public void setDumped(){isDumped = false;}
	public boolean isDumped(){return isDumped;}
	public Resource getContents(){return contents;}
	public String getURI(){return graphURI;}
	public String getGraphFilePath(){return graphFilePath;}
	public String getGraphClassURI(){return graphClassURI;}
	public OntClass getGraphClass(){return graphClass;}
	public List<NamedGraphComponent> getComponents(){return this.graphComponents;}
	public String getRootNodeURI(){return contents.getURI();}
	
	private void setModel(){
		model = ModelUtils.getEmptyReasoningModel();

		String ontologyURI = getOntologyURI(graphClassURI);
		
		System.out.println("reading in: " + ontologyURI);
		model.read(ontologyURI);
		
		graphClass = model.getOntClass(graphClassURI);

	}
	
	private String getOntologyURI(String classURI){
		return classURI.substring(0, classURI.indexOf("#"));
	}
	
	private void delineateGraphComponents(){
		graphClass = model.getOntClass(graphClassURI);
		
		
		List<OntClass> necessaryClasses = getSomeValuesFromClasses(graphClass);
		
		if(necessaryClasses.size() == 0)
			necessaryClasses = this.getSomeValues(graphClass);
		
		//add components
		graphComponents = new ArrayList<NamedGraphComponent>();
		NamedGraphComponent component;
		for(OntClass necessaryClass : necessaryClasses){
			component = new NamedGraphComponent(necessaryClass, this);
			graphComponents.add(component);
		}
	}
	
	public Individual getIndividualOf(OntClass ontClass){
		for(NamedGraphComponent graphComponent : this.graphComponents){
			if(graphComponent.getOntClass().getURI().equals(ontClass.getURI()))
				return graphComponent.getClassIndividual();
		}
		return null;
	}
	
	/**
	 * Right now only checking for common URIs, but need to investigate whether we need to check to see that the
	 * individual's classes are identical or subclasses
	 * @param someGraph
	 * @return
	 */
	public boolean hasCommonComponents(NamedGraph someGraph){
		for(NamedGraphComponent aComponent : this.getComponents()){
			for(NamedGraphComponent bComponent : someGraph.getComponents()){
				if(aComponent.getClassIndividual().getURI().equals(bComponent.getClassIndividual().getURI()))
						return true;
			}
		}
		return false;
	}
	
	public boolean hasComponent(NamedGraph namedGraph){
		for(NamedGraphComponent component : this.getComponents())
			if(component.getClassIndividual().equals(namedGraph.getRootNodeURI()))
				return true;
		return false;
	}
	
	private List<OntClass> getSomeValues(OntClass aClass){
		System.out.println("geting value from: " + aClass.getURI());

		ArrayList<OntClass> partOfIntersectionClasses = new ArrayList<OntClass>();
		System.out.println("populated array list");
		
		Restriction res = aClass.getEquivalentClass().asRestriction();
		
		SomeValuesFromRestriction restriction = res.asSomeValuesFromRestriction();
	    	  
		OntClass valuesFromClass;
		try{
			valuesFromClass = restriction.getSomeValuesFrom().as(OntClass.class);
			if(!valuesFromClass.getURI().equals(literalURI))
				partOfIntersectionClasses.add(valuesFromClass);
			}catch(Exception e){e.printStackTrace();}
		return partOfIntersectionClasses;
	}
	
	private List<OntClass> getSomeValuesFromClasses(OntClass aClass){
		System.out.println("geting some values from: " + aClass.getURI());
		ArrayList<OntClass> partOfIntersectionClasses = new ArrayList<OntClass>();
		
		try{
			IntersectionClass intersection = aClass.getEquivalentClass().asIntersectionClass();
		 
			for (Iterator<? extends OntClass> i = intersection.listOperands(); i.hasNext(); ) {
					OntClass partOfIntersectionClass = (OntClass) i.next();

					if (partOfIntersectionClass.isRestriction()) {
						Restriction res = partOfIntersectionClass.asRestriction();
						SomeValuesFromRestriction restriction = res.asSomeValuesFromRestriction();
		    	  
						OntClass valuesFromClass;
						try{
							valuesFromClass = restriction.getSomeValuesFrom().as(OntClass.class);
							if(!valuesFromClass.getURI().equals(literalURI))
								partOfIntersectionClasses.add(valuesFromClass);
						}catch(Exception e){e.printStackTrace();}
					}
		      //if is a named class rather than anonymous restriction class
					else
						partOfIntersectionClasses.add(partOfIntersectionClass);
			}
		}catch(Exception e){e.printStackTrace();}
		return partOfIntersectionClasses;
	}
	
	public boolean isEquivalentTo(NamedGraph namedGraph){
		Model inputModel = namedGraph.getContents().getModel();
		return this.getContents().getModel().isIsomorphicWith(inputModel);
	}
}