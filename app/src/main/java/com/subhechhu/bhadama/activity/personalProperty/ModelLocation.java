package com.subhechhu.bhadama.activity.personalProperty;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelLocation {

	@SerializedName("coordinates")
	private List<String> coordinates;

	@SerializedName("type")
	private String type;

	public List<String> getCoordinates(){
		return coordinates;
	}

	public String getType(){
		return type;
	}

	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ModelLocation{" +
				"coordinates=" + coordinates +
				", type='" + type + '\'' +
				'}';
	}
}