package edu.utep.cybershare.elseweb.model;

public class Entity extends Element{

	public Entity(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
