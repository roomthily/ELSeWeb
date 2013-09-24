package edu.utep.cybershare.elseweb.ontology;

import com.hp.hpl.jena.ontology.Individual;

import edu.utep.cybershare.elseweb.model.Catalog;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.Dataset;
import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.model.Visitor;
import edu.utep.cybershare.elseweb.ontology.axioms.CatalogAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.CharacteristicAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.DatasetAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.DistributionAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.DurationAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.EntityAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.MeasurementAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.ObservationAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.RegionAxioms;

public class OWLVisitor implements Visitor{

	private OntologyToolset bundle;
	
	public OWLVisitor(OntologyToolset bundle){
		this.bundle = bundle;
	}
	
	public void visit(Characteristic characteristic) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(characteristic, bundle);
		CharacteristicAxioms axioms = new CharacteristicAxioms(characteristic, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Dataset dataset) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(dataset, bundle);
		DatasetAxioms axioms = new DatasetAxioms(dataset, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Measurement measurement) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(measurement, bundle);
		MeasurementAxioms axioms = new MeasurementAxioms(measurement, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Observation observation) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(observation, bundle);
		ObservationAxioms axioms = new ObservationAxioms(observation, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Entity entity) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(entity, bundle);
		EntityAxioms axioms = new EntityAxioms(entity, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Catalog catalog) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(catalog, bundle);
		CatalogAxioms axioms = new CatalogAxioms(catalog, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Distribution distribution) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(distribution, bundle);
		DistributionAxioms axioms = new DistributionAxioms(distribution, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Region region) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(region, bundle);
		RegionAxioms axioms = new RegionAxioms(region, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}

	public void visit(Duration duration) {
		// TODO Auto-generated method stub
		Individual individual = Individuals.getIndividual(duration, bundle);
		DurationAxioms axioms = new DurationAxioms(duration, individual, bundle);
		axioms.setAxioms();
		bundle.addStatements(axioms);
	}
}
