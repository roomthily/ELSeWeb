package edu.utep.cybershare.elseweb.prov;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.utep.cybershare.elseweb.build.source.edac.WCSDigest;

public class Agent {
	
	public static Resource getAgentResource(String baseURI, Model aModel, WCSDigest digest){

		String agentMODIS_URI = baseURI + "MODIS";
		String agentPRISM_URI = baseURI + "PRISM";
		
		if(digest.getFGDCThemes().getTheme_EDAC_Prism() != null)
			return aModel.createResource(agentPRISM_URI, Vocab.Agent);
		else
			return aModel.createResource(agentMODIS_URI, Vocab.Agent);
	}
	
	private static final class Vocab{
		
		private static Model m_model = ModelFactory.createDefaultModel();
		
		private static final Resource Agent = m_model.createResource("http://www.w3.org/ns/prov-o/Agent");
	}	
}
