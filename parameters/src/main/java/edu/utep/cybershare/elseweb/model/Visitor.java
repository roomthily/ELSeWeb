package edu.utep.cybershare.elseweb.model;

public interface Visitor {
	
	public void visit(Algorithm algorithm);
	public void visit(Parameter parameter);

}
