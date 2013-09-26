package edu.utep.cybershare.elseweb.ontology;

import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.WCSCoverageSet;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.WCSCoverage;
import edu.utep.cybershare.elseweb.model.Distribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.model.Visitor;
import edu.utep.cybershare.elseweb.ontology.axioms.WCSCoverageSetAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.CharacteristicAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.WCSCoverageAxioms;
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
		OWLIndividual individual = Individuals.getIndividual(characteristic, bundle);
		CharacteristicAxioms axioms = new CharacteristicAxioms(characteristic, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(WCSCoverage dataset) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(dataset, bundle);
		WCSCoverageAxioms axioms = new WCSCoverageAxioms(dataset, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Measurement measurement) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(measurement, bundle);
		MeasurementAxioms axioms = new MeasurementAxioms(measurement, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Observation observation) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(observation, bundle);
		ObservationAxioms axioms = new ObservationAxioms(observation, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Entity entity) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(entity, bundle);
		EntityAxioms axioms = new EntityAxioms(entity, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(WCSCoverageSet catalog) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(catalog, bundle);
		WCSCoverageSetAxioms axioms = new WCSCoverageSetAxioms(catalog, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Distribution distribution) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(distribution, bundle);
		DistributionAxioms axioms = new DistributionAxioms(distribution, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Region region) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(region, bundle);
		RegionAxioms axioms = new RegionAxioms(region, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}

	public void visit(Duration duration) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(duration, bundle);
		DurationAxioms axioms = new DurationAxioms(duration, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}
}
