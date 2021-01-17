package com.subhechhu.bhadama.util;

public class GetUrl {
    private static final String BASE_URL = "http://3.16.160.46:80/";

    public static final String SIGNUP = BASE_URL + "users";
    public static final String LOGIN = BASE_URL + "auth";
    public static final String FORGET_PASSWORD = BASE_URL + "users/forgotPassword";
    public static final String VERIFY_OTP = BASE_URL + "users/verifyOtp";
    public static final String UPDATE_PIN = BASE_URL + "users/setNewPin";
    public static final String VERIFY_PROPERTY = BASE_URL + "properties";
    public static final String UPLOAD_IMAGE = BASE_URL + "files/uploads";
    public static final String GET_PROPERTIES = BASE_URL + "properties";
    public static final String MODIFY_PROPERTY = BASE_URL + "properties/";


    public static final String LOCATION_URL = "https://api.locationiq.com/v1/autocomplete.php?key=pk.e9c44f4c1263e101648dec5cba7ca4b3&countrycodes=NP&q=";
    public static final String POI_HOSPITAL = "https://locationiq.com/v1/nearby.php?key=pk.e9c44f4c1263e101648dec5cba7ca4b3&tag=hospital&radius=1500&format=json";
    public static final String POI_PHARMACY = "https://locationiq.com/v1/nearby.php?key=pk.e9c44f4c1263e101648dec5cba7ca4b3&tag=pharmacy&radius=1500&format=json";
    public static final String POI_MART = "https://locationiq.com/v1/nearby.php?key=pk.e9c44f4c1263e101648dec5cba7ca4b3&tag=supermarket&radius=1500&format=json";
    public static final String POI_PETROLPUMP = "https://locationiq.com/v1/nearby.php?key=pk.e9c44f4c1263e101648dec5cba7ca4b3&tag=fuel&radius=1500&format=json";
}
