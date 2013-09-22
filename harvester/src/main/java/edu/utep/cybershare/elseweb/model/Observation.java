package edu.utep.cybershare.elseweb.model;

public class Observation extends Element {

	private Measurement measurement;
	private Entity entity;
	
	public Observation(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isSet_measurement(){
		return this.getMeasurement() != null;
	}
	
	public boolean isSet_entity(){
		return this.getEntity() != null;
	}
	
	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
}
