package com.subhechhu.bhadama.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

	@SerializedName("country")
	private String country;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("city")
	private String city;

	@SerializedName("neighbourhood")
	private String neighbourhood;

	@SerializedName("name")
	private String name;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("suburb")
	private String suburb;

	@SerializedName("state")
	private String state;

	protected Address(Parcel in) {
		country = in.readString();
		countryCode = in.readString();
		city = in.readString();
		neighbourhood = in.readString();
		name = in.readString();
		postcode = in.readString();
		suburb = in.readString();
		state = in.readString();
	}

	public static final Creator<Address> CREATOR = new Creator<Address>() {
		@Override
		public Address createFromParcel(Parcel in) {
			return new Address(in);
		}

		@Override
		public Address[] newArray(int size) {
			return new Address[size];
		}
	};

	public String getCountry(){
		return country;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getCity(){
		return city;
	}

	public String getNeighbourhood(){
		return neighbourhood;
	}

	public String getName(){
		return name;
	}

	public String getPostcode(){
		return postcode;
	}

	public String getSuburb(){
		return suburb;
	}

	public String getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"Address{" + 
			"country = '" + country + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",city = '" + city + '\'' + 
			",neighbourhood = '" + neighbourhood + '\'' + 
			",name = '" + name + '\'' + 
			",postcode = '" + postcode + '\'' + 
			",suburb = '" + suburb + '\'' + 
			",state = '" + state + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(country);
		parcel.writeString(countryCode);
		parcel.writeString(city);
		parcel.writeString(neighbourhood);
		parcel.writeString(name);
		parcel.writeString(postcode);
		parcel.writeString(suburb);
		parcel.writeString(state);
	}
}