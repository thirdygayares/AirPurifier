package com.airpurifier.airpurifier.API;

public interface EndPoint {
    public static final String BASE_URL = "http://192.168.68.102/AirPurifierApi/";
    public static final String LOGIN_URL = BASE_URL + "User/api.php?apicall=login";
    public static final String CREATE_USER = BASE_URL + "User/api.php?apicall=createUser";
    public static final String GET_ALL_USERS = BASE_URL + "User/api.php?apicall=getUsers";


    public static final String GET_AIR_PURIFIER_DATA = BASE_URL + "FunctionApi/api.php?apicall=getAirPurifierData";
    public static final String UPDATE_AIR_PURIFIER_POWER = BASE_URL + "FunctionApi/api.php?apicall=updateAirPurifierPower";
    public static final String UPDATE_AIR_PURIFIER_LEVEL = BASE_URL + "FunctionApi/api.php?apicall=updateAirPurifierLevel";
    public static final String UPDATE_AIR_PURIFIER_HUMIDITY = BASE_URL + "FunctionApi/api.php?apicall=updateAirPurifierHumidity";

}
