package edu.utep.cybershare.elseweb.ontology;

import org.semanticweb.owlapi.model.OWLIndividual;

import edu.utep.cybershare.elseweb.model.Algorithm;
import edu.utep.cybershare.elseweb.model.WCSCoverageSet;
import edu.utep.cybershare.elseweb.model.Characteristic;
import edu.utep.cybershare.elseweb.model.WCSCoverageDataset;
import edu.utep.cybershare.elseweb.model.WCSCoverageDistribution;
import edu.utep.cybershare.elseweb.model.Duration;
import edu.utep.cybershare.elseweb.model.Entity;
import edu.utep.cybershare.elseweb.model.Measurement;
import edu.utep.cybershare.elseweb.model.Observation;
import edu.utep.cybershare.elseweb.model.Region;
import edu.utep.cybershare.elseweb.model.Visitor;
import edu.utep.cybershare.elseweb.ontology.axioms.WCSCoverageSetAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.CharacteristicAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.WCSCoverageDatasetAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.WCSCoverageDistributionAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.DurationAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.EntityAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.MeasurementAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.ObservationAxioms;
import edu.utep.cybershare.elseweb.ontology.axioms.AlgorithmAxioms;

public class OWLVisitor implements Visitor{

	private OntologyToolset bundle;
	
	public OWLVisitor(OntologyToolset bundle){
		this.bundle = bundle;
	}
	
	public void visit(Algorithm algorithm) {
		// TODO Auto-generated method stub
		OWLIndividual individual = Individuals.getIndividual(characteristic, bundle);
		CharacteristicAxioms axioms = new CharacteristicAxioms(characteristic, individual, bundle);
		axioms.setAxioms();
		bundle.addAxioms(axioms);
	}
}
