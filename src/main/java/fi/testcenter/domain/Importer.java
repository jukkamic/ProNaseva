package fi.testcenter.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Importer implements Serializable {

	@Id
	private Long id;

	private String name;

	public Importer() {

	}

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
