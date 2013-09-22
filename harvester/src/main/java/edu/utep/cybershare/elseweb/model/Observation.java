package edu.utep.cybershare.elseweb.model;

public class Observation extends Element {

	public Observation(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}
	private Entity observesEntity;
	private Measurement hasMeasurement;
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
}
