package com.example.sonamessteetenzin.perfectplate;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kinley on 4/12/2018.
 */

public class DataParser {
    public HashMap<String,String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String,String> googlePlacesMap = new HashMap<>();
        String placeName="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";
        try {
            if(!googlePlaceJson.isNull("name"))
            {
                placeName=googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity"))
            {
                vicinity=googlePlaceJson.getString("vicinity");
            }
            latitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJson.getString("reference");
            googlePlacesMap.put("place_name",placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat",latitude);
            googlePlacesMap.put("lng",longitude);
            googlePlacesMap.put("reference",reference);

        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSon",e.toString());
        }
        Log.d("result",googlePlacesMap.toString());
        return googlePlacesMap;
    }
    private List<HashMap<String,String>>getPlaces(JSONArray jsonArray)//To store multiple nearby places
    {
        int count=jsonArray.length();
        List<HashMap<String, String>> placesList=new ArrayList<>();//to store all the hashmaps
        HashMap<String, String> placeMap=null;//to store each place that is fetched
        for (int i=0;i<count;i++)
        {
            //use getPlace method to fetch one place then it will be added to a list of hashmap getPlcaes
            try {
                placeMap=getPlace((JSONObject) jsonArray.get(i));
                //adding it to places list
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }
    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
