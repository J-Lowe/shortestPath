package tk.jlowe.smallroute.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class RouteChecker {
	private static ArrayList<City> cities = new ArrayList<City>();
	private static HashMap<String, City> cityNames = new HashMap<String, City>();
	private static City destinationCity;
	
	public static void main(String[] args) {
        BufferedReader fileReader = null;
        JFileChooser chooser = new JFileChooser();
        int okay = chooser.showOpenDialog(null);
        File file = null;
        if(okay == JFileChooser.APPROVE_OPTION) {
        	file = chooser.getSelectedFile();
        }
        else {
        	System.exit(0);
        }
		try {
			fileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        String dud;
		try {
			dud = fileReader.readLine();
	        while(dud != null) {
	        	String[] parts = dud.split(" ");
	        	City city1;
	        	City city2;
	        	if(!cityNames.containsKey(parts[0])) {
	        		city1 = new City(parts[0]);
	        		cityNames.put(parts[0], city1);
	        	}
	        	else {
	        		city1 = cityNames.get(parts[0]);
	        	}
	        	if(!cityNames.containsKey(parts[1])) {
	        		city2 = new City(parts[1]);
	        		cityNames.put(parts[1], city2);
	        	}
	        	else {
	        		city2 = cityNames.get(parts[1]);
	        	}
//	        	if(!cities.contains(city1)) {
//	        		cities.add(city1);
//	        	}
//	        	else
//	        		city1 = cities.get(cities.indexOf(city1));
//	        	if(!cities.contains(city2)) {
//	        		cities.add(city2);
//	        	}
//	        	else
//	        		city2 = cities.get(cities.indexOf(city2));
	        	//System.out.println(city1.getName() + " " + city2.getName() + "\n");
	        	double distance = Double.parseDouble(parts[2]);
	        	//System.out.println(distance + "\n");
	        	city1.addDistance(city2, distance);
	        	city2.addDistance(city1, distance);
	        	dud = fileReader.readLine();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		String city1 = JOptionPane.showInputDialog("Enter starting city:");
		String city2 = JOptionPane.showInputDialog("Enter destination city:");
		System.out.println(getShortestRoute(city1, city2));
    }
	
	public static String getShortestRoute(String start, String end) {
		City startCity = cityNames.get(start);
		destinationCity = cityNames.get(end);
		ArrayList<City> allRoutes = methodX(startCity);
		String shortestRoute = methodZ(allRoutes);
		return shortestRoute;
	}
	
	private static ArrayList<City> methodX(City city) {
		Set<City> connectedCities = city.getCities();
		ArrayList<City> route = new ArrayList<City>();
		for(City childCity : connectedCities) {
			ArrayList<City> currentRoute = new ArrayList<City>();
			//System.out.println("Adding city: " + city.getName());
			currentRoute.add(city);
			route.addAll(methodY(currentRoute, childCity));
		}
		return route;
	}
	
	private static ArrayList<City> methodY(ArrayList<City> previousRoute, City city) {
		Set<City> connectedCities = city.getCities();
		ArrayList<City> route = new ArrayList<City>();
		ArrayList<City> currentRoute = null;
		if(!city.equals(destinationCity)) {
			for(City childCity : connectedCities) {
				currentRoute = new ArrayList<City>();
				currentRoute.addAll(previousRoute);
//				for(City city1 : currentRoute) {
//					System.out.println(city1.getName());
//				}
				if(!previousRoute.contains(childCity)) {
					//System.out.println("Adding city: " + city.getName());
					currentRoute.add(city);
					route.addAll(0, methodY(currentRoute, childCity));
				}
			}
		}
		else {
			route.add(city);
			route.addAll(0, previousRoute);
			//System.out.println("Adding city: " + city.getName());
		}
//		for(City city1 : route) {
//			System.out.print(city1.getName());
//		}
//		System.out.println("");
		return route;
	}
	
	private static String methodZ(ArrayList<City> allRoutes) {
//		for(City city : allRoutes) {
//			System.out.println(city.getName());
//		}
		ArrayList<City> currentRoute = new ArrayList<City>();
		ArrayList<City> shortestRoute = new ArrayList<City>();
		double currentRouteDistance = 0;
		double shortestRouteDistance = Double.MAX_VALUE;
		String output;
		for(int i = 0; i < allRoutes.size(); i++) {
			City city = allRoutes.get(i);
//			System.out.println(city.getName());
			if(!city.equals(destinationCity)) {
				currentRouteDistance += city.getDistance(allRoutes.get(i + 1));
			}
			currentRoute.add(city);
//			for(City city1 : currentRoute) {
//				System.out.print(city.getName());
//			}
			if(city.equals(destinationCity)) {
//				System.out.println(generateOutput(currentRoute, currentRouteDistance));
				if(currentRouteDistance < shortestRouteDistance) {
					shortestRoute = currentRoute;
					shortestRouteDistance = currentRouteDistance;
				}
				currentRouteDistance = 0;
//				for(City city1 : currentRoute) {
//					System.out.print(city.getName());
//				}
//				System.out.println("");
				currentRoute = new ArrayList<City>();
			}
		}
//		System.out.println(shortestRouteDistance);
		output = generateOutput(shortestRoute, shortestRouteDistance);
		return output;
	}
	
	private static String generateOutput(ArrayList<City> cityList, double distance) {
		String output = "Shortest route from " + cityList.get(0).getName() + " to " + cityList.get(cityList.size() - 1).getName() + " is :\n";
		for(City city : cityList) {
			output += city.getName() + "\n";
		}
		output += "For a total distance of: " + distance;
		return output;
	}
//	public String getShortestRoute(String cityName1, String cityName2) {
//		String output = null;
//		if(!cities.contains(new City(cityName1)) && !cities.contains(new City(cityName2)))
//			output = "The database does not contain \"" + cityName1 + "\" nor \"" + cityName2 + "\".";
//		else if(!cities.contains(new City(cityName1)))
//			output = "The database does not contain \"" + cityName1 + "\".";
//		else if(!cities.contains(new City(cityName2)))
//			output = "The database does not contain \"" + cityName2 + "\".";
//		else {
//			City city1 = cities.get(cities.indexOf(new City(cityName1)));
//			City city2 = cities.get(cities.indexOf(new City(cityName2)));
//			if(city1.isConnectedTo(city2))
//				output = "The shortest path is: " + cityName1 + " to " + cityName2 + " for a total distance of " + city1.getDistance(city2);
//			else {
//				ArrayList<City> connectedCities = new ArrayList<City>();
//				int shortestLength = 2147483647;
//				for(City city3 : city1.getCities()) {
//					if(city3.isConnectedTo(city2)) {
//						connectedCities.add(city3);
//					}
//				}
//				while(connectedCities.size() < 1) {
//					
//				}
//				else {
//					for(City city3 : connectedCities) {
//						
//					}
//				}
//			}
//		}
//		return output;
//	}
//	
//	private static ArrayList<City> getShortestRoute(City city1, City city2) {
//		ArrayList<City> connectedCities = new ArrayList<City>();
//		for(City city3 : city1.getCities()) {
//			if(city3.isConnectedTo(city2)) {
//				connectedCities.add(city3);
//			}
//			//for(City city4: city2.getCities()) {
//				//if(city1.isConnectedTo(city2))
//					//connectedCities.add(city2);
//			//}
//			//whatTheCrapAmIDoing.add(connectedCities);
//		}
//		if(connectedCities.size() > 0) {
//			
//		}
//		else {
//			for(City city3 : city1.getCities()) {
//				getShortestRoute(city3, city2);
//			}
////			ArrayList<ArrayList<City>> whatTheCrapAmIDoing = new ArrayList<ArrayList<City>>();
////			for(City city3 : city1.getCities()) {
////				ArrayList<City> dudList = getShortestRoute(city2, city3);
////				if(dudList.size() > 0)
////					whatTheCrapAmIDoing.add(dudList);
////			}
////			if(whatTheCrapAmIDoing.size() == 0) {
////				for(City city3 : city1.getCities()) {
////					for(City city4: city2.getCities()) {
////						ArrayList<City> dudList = getShortestRoute(city3, city4);
////						if(dudList.size() > 0)
////							whatTheCrapAmIDoing.add(dudList);
////					}
////				}
////			}
////			else {
////				double distance = Double.MAX_VALUE;
////				for(ArrayList<City> cityList : whatTheCrapAmIDoing) {
////					double newDist = calculateDistance(cityList);
////					if(newDist < distance)
////						distance = newDist;
////				}
////			}
//		}
//		return null;
//	}
//	
//	private static double calculateDistance(ArrayList<City> cityList) {
//		double distance = 0;
//		for(int i = 0; i < cityList.size() - 1; i++) {
//			distance += cityList.get(i).getDistance(cityList.get(i + 1));
//		}
//		return distance;
//	}
//}