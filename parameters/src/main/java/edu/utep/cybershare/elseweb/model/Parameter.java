package edu.utep.cybershare.elseweb.model;

public class Parameter extends Element {
	
	private String name;
	private int min;
	private int max;
	private String defaultValue;
	private String type;
	
	private static final int default_Value = -101010101;
	
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
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
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
