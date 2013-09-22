package edu.utep.cybershare.elseweb.model;

public class Dataset extends Element {

	public Dataset(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}

	private Measurement measurement;

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
		
	}
}
