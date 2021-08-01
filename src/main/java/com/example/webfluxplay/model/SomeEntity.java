package com.example.webfluxplay.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

public class SomeEntity implements Persistable<String> {
	@Transient
	private boolean isNew; 
	@Id
    private String name;
    @NotNull
    private String athing;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAthing() {
		return athing;
	}

	public void setAthing(String athing) {
		this.athing = athing;
	}

	@Override
	public String getId() {
		return name;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}
	
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
}
