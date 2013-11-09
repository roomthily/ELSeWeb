package edu.utep.cybershare.elseweb.model;

public class Parameter extends Element {
	
	private String name;
	private String min;
	private String max;
	private String defaultValue;
	private String type;
	
	public static final String Integer_Label = "Integer";
	public static final String Float_Label = "Float";
		
	public Parameter(String name){
		super(name);
	}
	
	public boolean isSet_name(){return this.getName() != null;}
	public boolean isSet_min(){return this.getMin() != null && !this.getMin().isEmpty();}
	public boolean isSet_max(){return this.getMax() != null && !this.getMax().isEmpty();}
	public boolean isSet_type(){return this.getType() != null;}
	public boolean isSet_defaultValue(){return this.getDefaultValue() != null && !this.getDefaultValue().isEmpty();}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIntegerMin(){
		int intMin = Integer.valueOf(min);
		return intMin;
	}
	
	public int getIntegerMax(){
		int intMax = Integer.valueOf(max);
		return intMax;
	}
	
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public double getDoubleMax(){
		double doubleMax = Double.valueOf(max);
		return doubleMax;
	}
	
	public double getDoubleMin(){
		double doubleMin = Double.valueOf(min);
		return doubleMin;
	}

	@Override
	public void accept(Visitor visitor) {
	}
}
