package edu.utep.cybershare.elseweb.model;

public class Agent extends Element{

	public Agent(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
