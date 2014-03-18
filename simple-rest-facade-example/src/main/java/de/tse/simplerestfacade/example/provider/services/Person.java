package de.tse.simplerestfacade.example.provider.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person {

	private Integer id;
	private String firstname;
	private String lastname;
	private Integer age;
	
	public Person() {
		
	}
	
	public Person(final String firstname, final String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "[Person: id=" + id + ", name="+ firstname + " " + lastname + "]";
	}
}
