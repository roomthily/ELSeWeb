package edu.utep.cybershare.elseweb.model;

public interface Visitor {
	
	public void visit(Characteristic characteristic);
	public void visit(WCSCoverage dataset);
	public void visit(Measurement measurement);
	public void visit(Observation observation);
	public void visit(Entity entity);
	public void visit(WCSCoverageSet catalog);
	public void visit(Distribution distribution);
	public void visit(Region region);
	public void visit(Duration duration);
	
}
