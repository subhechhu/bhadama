package com.subhechhu.bhadama.activity.propertyDetailsBuyer.map;

public class MapModel{
	String name, lon, lat;

	public MapModel(String name, String lon, String lat) {
		this.name = name;
		this.lon = lon;
		this.lat = lat;
	}

	public String getName(){
		return name;
	}

	public String getLon(){
		return lon;
	}

	public String getLat(){
		return lat;
	}
}