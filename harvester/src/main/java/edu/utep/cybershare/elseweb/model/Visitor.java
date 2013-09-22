package edu.utep.cybershare.elseweb.model;

public interface Visitor {
	
	public void visit(Agent agent);
	public void visit(Characteristic characteristic);
	public void visit(Dataset dataset);
	public void visit(Measurement measurement);
	public void visit(Observation observation);
	public void visit(Entity entity);
	
}
