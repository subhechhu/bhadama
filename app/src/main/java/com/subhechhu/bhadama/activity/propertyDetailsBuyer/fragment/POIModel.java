package com.subhechhu.bhadama.activity.propertyDetailsBuyer.fragment;

import com.google.gson.annotations.SerializedName;

public class POIModel{

	@SerializedName("osm_type")
	private String osmType;

	@SerializedName("osm_id")
	private long osmId;

	@SerializedName("distance")
	private int distance;

	@SerializedName("tag_type")
	private String tagType;

	@SerializedName("name")
	private String name;

	@SerializedName("lon")
	private String lon;

	@SerializedName("lat")
	private String lat;

	public String getOsmType(){
		return osmType;
	}

	public long getOsmId(){
		return osmId;
	}

	public int getDistance(){
		return distance;
	}

	public String getTagType(){
		return tagType;
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