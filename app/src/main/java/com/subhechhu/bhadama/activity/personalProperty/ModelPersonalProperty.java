package com.subhechhu.bhadama.activity.personalProperty;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelPersonalProperty {

	@SerializedName("furnishing")
	private String furnishing;

	@SerializedName("city")
	private String city;

	@SerializedName("facing")
	private String facing;

	@SerializedName("profileImage")
	private String profileImage;

	@SerializedName("rent")
	private int rent;

	@SerializedName("availableFrom")
	private String availableFrom;

	@SerializedName("advance")
	private int advance;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("approved")
	private boolean approved;

	@SerializedName("roomSize")
	private String roomSize;

	@SerializedName("__v")
	private int V;

	@SerializedName("waterSupplyOther")
	private boolean waterSupplyOther;

	@SerializedName("sqft")
	private String sqft;

	@SerializedName("place")
	private String place;

	@SerializedName("roomType")
	private String roomType;

	@SerializedName("updatedAt")
	private String updatedAt;

	@SerializedName("waterSupplyUnderground")
	private boolean waterSupplyUnderground;

	@SerializedName("tenants")
	private String tenants;

	@SerializedName("images")
	private List<String> images;

	@SerializedName("waterSupplyNwscc")
	private boolean waterSupplyNwscc;

	@SerializedName("userId")
	private String userId;

	@SerializedName("twoWheeler")
	private boolean twoWheeler;

	@SerializedName("location")
	private ModelLocation location;

	@SerializedName("fourWheeler")
	private boolean fourWheeler;

	@SerializedName("_id")
	private String id;

	@SerializedName("age")
	private String age;

	public String getFurnishing(){
		return furnishing;
	}

	public String getCity(){
		return city;
	}

	public String getFacing(){
		return facing;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public int getRent(){
		return rent;
	}

	public String getAvailableFrom(){
		return availableFrom;
	}

	public int getAdvance(){
		return advance;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public boolean isApproved(){
		return approved;
	}

	public String getRoomSize(){
		return roomSize;
	}

	public int getV(){
		return V;
	}

	public boolean isWaterSupplyOther(){
		return waterSupplyOther;
	}

	public String getSqft(){
		return sqft;
	}

	public String getPlace(){
		return place;
	}

	public String getRoomType(){
		return roomType;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isWaterSupplyUnderground(){
		return waterSupplyUnderground;
	}

	public String getTenants(){
		return tenants;
	}

	public List<String> getImages(){
		return images;
	}

	public boolean isWaterSupplyNwscc(){
		return waterSupplyNwscc;
	}

	public String getUserId(){
		return userId;
	}

	public boolean isTwoWheeler(){
		return twoWheeler;
	}

	public ModelLocation getLocation(){
		return location;
	}

	public boolean isFourWheeler(){
		return fourWheeler;
	}

	public String getId(){
		return id;
	}

	public String getAge(){
		return age;
	}

	@Override
	public String toString() {
		return "PersonalPropertyModel{" +
				"furnishing='" + furnishing + '\'' +
				", city='" + city + '\'' +
				", facing='" + facing + '\'' +
				", profileImage='" + profileImage + '\'' +
				", rent=" + rent +
				", availableFrom='" + availableFrom + '\'' +
				", advance=" + advance +
				", createdAt='" + createdAt + '\'' +
				", approved=" + approved +
				", roomSize='" + roomSize + '\'' +
				", V=" + V +
				", waterSupplyOther=" + waterSupplyOther +
				", sqft='" + sqft + '\'' +
				", place='" + place + '\'' +
				", roomType='" + roomType + '\'' +
				", updatedAt='" + updatedAt + '\'' +
				", waterSupplyUnderground=" + waterSupplyUnderground +
				", tenants='" + tenants + '\'' +
				", images=" + images +
				", waterSupplyNwscc=" + waterSupplyNwscc +
				", userId='" + userId + '\'' +
				", twoWheeler=" + twoWheeler +
				", location=" + location +
				", fourWheeler=" + fourWheeler +
				", id='" + id + '\'' +
				", age='" + age + '\'' +
				'}';
	}
}