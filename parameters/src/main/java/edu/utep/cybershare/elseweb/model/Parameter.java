package edu.utep.cybershare.elseweb.model;

public class Parameter extends Element {
	
	private String name;
	private double min;
	private double max;
	private String defaultValue;
	private String type;
	
	public static final String Integer = "Integer";
	public static final String Float = "Float";
	
	private static final double default_Value = -101010101.0;
	
	public Parameter(String name){
		super(name);

		min = default_Value;
		max = default_Value;
	}
	
	public boolean isSet_name(){return this.getName() != null;}
	public boolean isSet_min(){return this.getMin() != default_Value;}
	public boolean isSet_max(){return this.getMax() != default_Value;}
	public boolean isSet_type(){return this.getType() != null;}
	public boolean isSet_defaultValue(){return this.getDefaultValue() != null;}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIntegerMin(){
		return (int)min;
	}
	
	public int getIntegerMax(){
		return (int)max;
	}
	
	public double getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(int max) {
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

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
	

}
