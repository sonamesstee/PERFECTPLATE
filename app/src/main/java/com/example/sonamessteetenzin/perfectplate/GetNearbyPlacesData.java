package com.example.sonamessteetenzin.perfectplate;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kinley on 4/12/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
    String googlePlacesData;
    GoogleMap mMap;
    String url;
    TextView tf;
    @Override
    protected String doInBackground(Object... objects) {
        Log.d("GetNearbyPlacesData","doInBackgroud entered");
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask","doInBackground Exit");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("GooglePlacesReadTask",e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("GooglePlaceReadTask","onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlaceList=null;
        DataParser parser=new DataParser();
        nearbyPlaceList=parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);

        Log.d("GooglePlaceReadTask","onPostExecute Exit");
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)//this methd is going to show all the nearby places
    {
        for (int i=0;i<nearbyPlaceList.size();i++)
        {
            Log.d("onPostExecute","Entered Into showing locations");
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            String placeName=googlePlace.get("place_name");
            String vicinity=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            double lng=Double.parseDouble(googlePlace.get("lng"));
            LatLng latLng=new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+" : "+ vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }

    }
}
