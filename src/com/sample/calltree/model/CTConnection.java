package com.sample.calltree.model;

public class CTConnection extends CTElement {

	private CTConnectionEndPoint source;
	private CTConnectionEndPoint target;
	
	private CTConnection(String name) {
		super(name);
	}
	
	public static CTConnection newInstance(String name) {
		return new CTConnection(name);
	}
	
	public int hashCode() {
		return source.hashCode() + target.hashCode();
	}
	
    public boolean equals(Object obj) {
    	if ( obj instanceof CTConnection ) {
    		CTConnection rightSide = (CTConnection)obj;
    		return source.hashCode() == rightSide.source.hashCode() 
    				&& target.hashCode() == rightSide.target.hashCode();
    	}
    	return false;
    }
	
	public CTConnectionEndPoint getSource() {
		return source;
	}

	public void setSource(CTConnectionEndPoint source) {
		this.source = source;
	}

	public CTConnectionEndPoint getTarget() {
		return target;
	}

	public void setTarget(CTConnectionEndPoint target) {
		this.target = target;
	}
}
