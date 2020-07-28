package laby3;

import java.util.ArrayList;

public class GeoRoute implements Comparable<GeoRoute>{
	// define Attributes
	private String name;
	private ArrayList <GeoPosition> waypoints = new ArrayList<GeoPosition>();
	
	public GeoRoute(String name) {
		super();
		this.name = name;
	}
	
	public GeoRoute(String name, ArrayList<GeoPosition> waypoints) {
		super();
		this.name = name;
		this.waypoints = waypoints;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addWaypoint(GeoPosition waypoint){
		waypoints.add(waypoint);
	}
	
	public void removeWaypoint(int index){
		if(getNumberWaypoints()>0) waypoints.remove(index);			
	}
	
	public void removeAllWaypoints(){
		waypoints = new ArrayList<GeoPosition>();	
	}

	public int getNumberWaypoints(){
		return waypoints.size();
	}
	
	public GeoPosition getWaypoint(int index) {
		return waypoints.get(index);
	}
	
	public GeoPosition[] getWaypoints(){		
		return waypoints.toArray(new GeoPosition[getNumberWaypoints()]);		
	}
	
	public double getDistance(){
		double totalDistance = 0;
		if(getNumberWaypoints()>1) {
			for (int i = 0; i< getNumberWaypoints()-1; i++){ 
				totalDistance += getWaypoint(i).distanceInKm(getWaypoint(i+1));
			}
			return totalDistance;
		} else return 0;
	}
	
	public int compareTo(GeoRoute other) {
		if (getDistance() < other.getDistance()) {
			return -1;
		}else if (getDistance() > other.getDistance()) {
			return 1;
		}else return 0;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%.1f km)", this.name, this.getDistance());
	}

}
