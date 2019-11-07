package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private final static String TAG ="Utility";

    /**
     * 解析和处理服务器返回的省级数据
     */



    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty (response)){
            try {
                JSONArray allProvinces = new JSONArray (response);
                for (int i = 0 ; i < allProvinces.length (); i++){
                    JSONObject provincesObject = allProvinces.getJSONObject (i);
                    Province province = new Province ();
                    province.setProvinceName (provincesObject.getString ("name"));
                    province.setProvinceCode (provincesObject.getInt ("id"));
                    province.save ();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace ();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty (response)){
            try {
                JSONArray allCities = new JSONArray (response);
                for (int i = 0; i < allCities.length ();i++){
                    JSONObject citiesJSONObject = allCities.getJSONObject (i);
                    City city = new City ();
                    city.setCityName (citiesJSONObject.getString ("name"));
                    city.setCityCode (citiesJSONObject.getInt ("id"));
                    city.setProvinceId (provinceId);
                    city.save ();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace ();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountryResponse(String response,int cityId){
        if (!TextUtils.isEmpty (response)){
            try {
                JSONArray allCounties = new JSONArray (response);
                for (int i = 0; i < allCounties.length (); i++){
                    JSONObject countryObject = allCounties.getJSONObject (i);
                    County county = new County ();
                    county.setCountyName (countryObject.getString ("name"));
                    county.setWeatherId (countryObject.getString ("weather_id"));
                    county.setCityId (cityId);
                    county.save ();
                    Log.e (TAG, "handleCountryResponse:cityId = "+cityId);
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace ();
            }
        }
        return false;
    }
}
