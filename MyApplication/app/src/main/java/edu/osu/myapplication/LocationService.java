package edu.osu.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class LocationService implements LocationListener {

    private static LocationService instance = null;
    private String tag = getClass().getSimpleName();
    private LocationManager locationManager;;
    public static String longitude;
    public static String latitude;


    public static LocationService getLocationManager(Context context)     {
        if (instance == null) {
            instance = new LocationService(context);
        }
        return instance;
    }

    @Override
    public void onLocationChanged(Location location)     {
       longitude = location.getLongitude()+"";
       latitude = location.getLatitude()+"";
        longitude = longitude.substring(0,6);
        latitude = latitude.substring(0,6);
    }

    /**
     * Local constructor
     */
    private LocationService( Context context ) {
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Log.d(tag,"LocationService created");
    }
    public void getUpdateLocation(Context context){
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Criteria criteria = new Criteria();
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String bestprovider = locationManager.getBestProvider(criteria,false);
            Location lastknownlocation = locationManager.getLastKnownLocation(bestprovider);

            if(lastknownlocation!=null){
                longitude = lastknownlocation.getLongitude()+"";
                latitude = lastknownlocation.getLatitude()+"";
                longitude = longitude.substring(0,6);
                latitude = latitude.substring(0,6);
            }
        }catch (SecurityException e){
            Toast.makeText(context, "Location service not granted", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d(tag,"disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(tag,"status");
    }
}
