package com.subhechhu.bhadama.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationModel implements Parcelable {

    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("osm_id")
    @Expose
    private String osmId;
    @SerializedName("osm_type")
    @Expose
    private String osmType;
    @SerializedName("licence")
    @Expose
    private String licence;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("boundingbox")
    @Expose
    private List<String> boundingbox = null;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("display_place")
    @Expose
    private String displayPlace;
    @SerializedName("display_address")
    @Expose
    private String displayAddress;
    @SerializedName("address")
    @Expose
    private com.subhechhu.bhadama.Model.Address address;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getOsmId() {
        return osmId;
    }

    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public List<String> getBoundingbox() {
        return boundingbox;
    }

    public void setBoundingbox(List<String> boundingbox) {
        this.boundingbox = boundingbox;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayPlace() {
        return displayPlace;
    }

    public void setDisplayPlace(String displayPlace) {
        this.displayPlace = displayPlace;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public com.subhechhu.bhadama.Model.Address getAddress() {
        return address;
    }

    public void setAddress(com.subhechhu.bhadama.Model.Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "LocationResponse{" +
                "placeId='" + placeId + '\'' +
                ", osmId='" + osmId + '\'' +
                ", osmType='" + osmType + '\'' +
                ", licence='" + licence + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", boundingbox=" + boundingbox +
                ", _class='" + _class + '\'' +
                ", type='" + type + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayPlace='" + displayPlace + '\'' +
                ", displayAddress='" + displayAddress + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeId);
        parcel.writeString(osmId);
        parcel.writeString(osmType);
        parcel.writeString(licence);
        parcel.writeString(lat);
        parcel.writeString(lon);
        parcel.writeStringList(boundingbox);
        parcel.writeString(_class);
        parcel.writeString(type);
        parcel.writeString(displayName);
        parcel.writeString(displayPlace);
        parcel.writeString(displayAddress);
    }

    public LocationModel(){

    }

    protected LocationModel(Parcel in) {
        placeId = in.readString();
        osmId = in.readString();
        osmType = in.readString();
        licence = in.readString();
        lat = in.readString();
        lon = in.readString();
        boundingbox = in.createStringArrayList();
        _class = in.readString();
        type = in.readString();
        displayName = in.readString();
        displayPlace = in.readString();
        displayAddress = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}