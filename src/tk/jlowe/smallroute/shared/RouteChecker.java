package tk.jlowe.smallroute.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class RouteChecker {
	private static ArrayList<City> cities = new ArrayList<City>();
	
	public static void main(String[] args) {
        BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(new File("easyDistances.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        String dud;
		try {
			dud = fileReader.readLine();
	        while(dud != null) {
	        	String[] parts = dud.split("	");
	        	City city1 = new City(parts[0]);
	        	City city2 = new City(parts[1]);
	        	if(!cities.contains(city1)) {
	        		cities.add(city1);
	        	}
	        	else
	        		city1 = cities.get(cities.indexOf(city1));
	        	if(!cities.contains(city2)) {
	        		cities.add(city2);
	        	}
	        	else
	        		city2 = cities.get(cities.indexOf(city2));
	        	city1.addDistance(city2, Integer.parseInt(parts[2]));
	        	city2.addDistance(city1, Integer.parseInt(parts[2]));
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public String getShortestRoute(String cityName1, String cityName2) {
		String output = null;
		if(!cities.contains(new City(cityName1)) && !cities.contains(new City(cityName2)))
			output = "The database does not contain \"" + cityName1 + "\" nor \"" + cityName2 + "\".";
		else if(!cities.contains(new City(cityName1)))
			output = "The database does not contain \"" + cityName1 + "\".";
		else if(!cities.contains(new City(cityName2)))
			output = "The database does not contain \"" + cityName2 + "\".";
		else {
			City city1 = cities.get(cities.indexOf(new City(cityName1)));
			City city2 = cities.get(cities.indexOf(new City(cityName2)));
			if(city1.isConnectedTo(city2))
				output = "The shortest path is: " + cityName1 + " to " + cityName2 + " for a total distance of " + city1.getDistance(city2);
			else {
				ArrayList<City> connectedCities = new ArrayList<City>();
				int shortestLength = 2147483647;
				for(City city3 : city1.getCities()) {
					if(city3.isConnectedTo(city2)) {
						connectedCities.add(city3);
					}
				}
				while(connectedCities.size() < 1) {
					
				}
				else {
					for(City city3 : connectedCities) {
						
					}
				}
			}
		}
		return output;
	}
	
	private static ArrayList<City> getShortestRoute(City city1, City city2) {
		ArrayList<City> connectedCities = new ArrayList<City>();
		for(City city3 : city1.getCities()) {
			if(city3.isConnectedTo(city2)) {
				connectedCities.add(city3);
			}
			//for(City city4: city2.getCities()) {
				//if(city1.isConnectedTo(city2))
					//connectedCities.add(city2);
			//}
			//whatTheCrapAmIDoing.add(connectedCities);
		}
		if(connectedCities.size() > 0) {
			
		}
		else {
			ArrayList<ArrayList<City>> whatTheCrapAmIDoing = new ArrayList<ArrayList<City>>();
			for(City city3 : city1.getCities()) {
				ArrayList<City> dudList = getShortestRoute(city2, city3);
				if(dudList.size() > 0)
					whatTheCrapAmIDoing.add(dudList);
			}
			if(whatTheCrapAmIDoing.size() == 0) {
				for(City city3 : city1.getCities()) {
					
				}
			}
			else {
				double distance = Double.MAX_VALUE;
				for(ArrayList<City> cityList : whatTheCrapAmIDoing) {
					double newDist = calculateDistance(cityList);
					if(newDist < distance)
						distance = newDist;
				}
			}
		}
		return null;
	}
	
	private static double calculateDistance(ArrayList<City> cityList) {
		double distance = 0;
		for(int i = 0; i < cityList.size() - 1; i++) {
			distance += cityList.get(i).getDistance(cityList.get(i + 1));
		}
		return distance;
	}
}