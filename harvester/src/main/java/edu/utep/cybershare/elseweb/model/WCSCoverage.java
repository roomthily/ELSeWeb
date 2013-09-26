package edu.utep.cybershare.elseweb.model;

public class WCSCoverage extends Element {

	private Region region;
	private Duration duration;
	private Measurement measurement;
	private Distribution distribution;
	
	public WCSCoverage(String identification) {
		super(identification);
		// TODO Auto-generated constructor stub
	}

	public boolean isSet_region(){
		return this.getRegion() != null;
	}
	
	public boolean isSet_distribution(){
		return this.getDistribution() != null;
	}
	
	public boolean isSet_duration(){
		return this.getDuration() != null;
	}
	
	public boolean isSet_measurement(){
		return this.getMeasurement() != null;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
		
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}
}
