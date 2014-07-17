package fi.testcenter.domain;

import java.io.Serializable;

public class Workshop implements Serializable {
	
	private String name;

	public Workshop(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
