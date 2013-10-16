package tk.jlowe.smallroute.shared;

import java.util.HashMap;
import java.util.Set;

public class City {
	private String name;
	HashMap<City, Double> distances;
	
	public City(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addDistance(City city, double distance) {
		distances.put(city, distance);
	}
	
	public double getDistance(City city) {
		return distances.get(city);
	}
	
	public Set<City> getCities() {
		return distances.keySet();
	}
	
	public boolean equals(City city) {
		if(this.name.equals(city.name))
			return true;
		else
			return false;
	}
	
	public boolean isConnectedTo(City city) {
		if(distances.containsKey(city))
			return true;
		else
			return false;
	}
}