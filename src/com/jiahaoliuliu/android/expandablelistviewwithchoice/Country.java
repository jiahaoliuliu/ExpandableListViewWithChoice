package com.jiahaoliuliu.android.expandablelistviewwithchoice;

import java.util.List;

public class Country {

	private String Name;
	private List<String> Cities;
	public Country(String name, List<String> cities) {
		super();
		Name = name;
		Cities = cities;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<String> getCities() {
		return Cities;
	}
	public void setCities(List<String> cities) {
		Cities = cities;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Cities == null) ? 0 : Cities.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (Cities == null) {
			if (other.Cities != null)
				return false;
		} else if (!Cities.equals(other.Cities))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Country [Name=" + Name + ", Cities=" + Cities + "]";
	}
}
