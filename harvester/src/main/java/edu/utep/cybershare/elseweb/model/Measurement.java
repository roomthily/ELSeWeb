package edu.utep.cybershare.elseweb.model;

public class Measurement extends Element {

	public Measurement(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}
	private Characteristic measuresCharacteristic;
	private Agent responsibleAgent;

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
}
