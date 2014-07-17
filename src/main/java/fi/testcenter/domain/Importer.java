package fi.testcenter.domain;

import java.io.Serializable;

public class Importer implements Serializable {

	private String name;
	
	public Importer(String name) {
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
