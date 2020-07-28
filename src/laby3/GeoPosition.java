package laby3;

public class GeoPosition {
	
	// define Attribute 
	private double latitude;
	private double longitude;
	
	//define Constructor 
	public GeoPosition(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// define getters for the attributes 
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	// Hemisphere checker 
	public boolean isNorthernHemisphere(){
		if( getLatitude() > 0) return true;
		else return false;
	}
	
	public boolean isSouthernHemisphere(){
		if( getLatitude() < 0) return true;
		else return false;
	}
	
	// Calculate the local distance between two GeoPositions
	public static double localDistanceInKm(GeoPosition a, GeoPosition b){
		double distanceX = 111.3*(a.getLatitude()-b.getLatitude());
		double distanceY = 111.3*Math.cos(Math.toRadians((a.getLatitude()+b.getLatitude())/2))*(a.getLongitude()-b.getLongitude());
		
		return Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2));
	}
	
	// Calculate the more precise calculation 
	public static double distanceInKm(GeoPosition a, GeoPosition b){
		double distance = 6378.388*
		Math.acos(Math.sin(Math.toRadians(a.getLatitude()))*
				Math.sin(Math.toRadians(b.getLatitude())) + 
				Math.cos(Math.toRadians(a.getLatitude()))*
				Math.cos(Math.toRadians(b.getLatitude()))*
				Math.cos(Math.toRadians(b.getLongitude()-a.getLongitude())));
		return distance;
	}
	
	// Calculate the distance between this class GeoPosition and the other given GeoPosition
	public double distanceInKm(GeoPosition other){
		double distance = 6378.388*
		Math.acos(Math.sin(Math.toRadians(other.getLatitude()))*
				Math.sin(Math.toRadians(this.getLatitude())) + 
				Math.cos(Math.toRadians(other.getLatitude()))*
				Math.cos(Math.toRadians(this.getLatitude()))*
				Math.cos(Math.toRadians(other.getLongitude()-this.getLongitude())));
		return distance;
	}
	 
	@Override
	public String toString() {
		return "(<" + latitude + ">, <" + longitude
				+ ">)";
	}
	
	
}