package com.example.crud.demo.model;

public class Students {
	
	protected int id;
    protected String name;
    protected String email;
    protected String country;
    
	public Students() {}
	
	public Students(String name, String email, String country) {
        super();
        this.name = name;
        this.email = email;
        this.country = country;
    }
    
	public Students(int id, String name, String email, String country) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
    
    

}
