package edu.osu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class WeatherFragment extends Fragment implements View.OnClickListener{

    public TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    public ProgressBar loader;
    public Typeface weatherFont;
    private String TAG = getClass().getSimpleName();
    public String city = "Columbus,US";
    public String OPEN_WEATHER_MAP_API = "2d19f5e9eb5f8e1698ce84001ccbeddf";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationService locationService;
    private View view;
    private static DateTime lastFetched;
    public static int temperatureValue;
    public static boolean hasRained;
    public static boolean hasSnowed;
    private Bundle savedBundle;


    /*
    private static final String city_field_bundle_id = "city_field";
    private static final String detail_field_bundle_id = "detail_field";
    private static final String current_temperature_bundle_id = "current_temp_field";
    private static final String humidity_field_bundle_id = "humidity_filed";
    private static final String pressure_field_bundle_id = "pressure_field";
    private static final String updated_field_bundle_id = "update_field";
    private static final String weather_icon_bundle_id = "weather_icon";
     */
    private static String city_field_bundle_id;
    private static String detail_field_bundle_id;
    private static String current_temperature_bundle_id;
    private static String humidity_field_bundle_id;
    private static String pressure_field_bundle_id;
    private static String updated_field_bundle_id;
    private static String weather_icon_bundle_id;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_weather, container, false);

        loader = view.findViewById(R.id.loader);
        cityField = view.findViewById(R.id.city_field);
        updatedField = view.findViewById(R.id.updated_field);
        detailsField = view.findViewById(R.id.details_field);
        currentTemperatureField = view.findViewById(R.id.current_temperature_field);
        humidity_field = view.findViewById(R.id.humidity_field);
        pressure_field = view.findViewById(R.id.pressure_field);
        weatherIcon = view.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);
        checkLocationPermission();

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("No Location Available");
            dialog.setMessage("We can't find your location. Weather information may be inaccurate.")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
        }

        Log.d(TAG,"onCreateView() called");
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated() called");
//        if (savedInstanceState != null) {
//            cityField.setText(savedInstanceState.getString(city_field_bundle_id));
//            detailsField.setText(savedInstanceState.getString(detail_field_bundle_id));
//            currentTemperatureField.setText(savedInstanceState.getString(current_temperature_bundle_id));
//            humidity_field.setText(savedInstanceState.getString(humidity_field_bundle_id));
//            pressure_field.setText(savedInstanceState.getString(pressure_field_bundle_id));
//            updatedField.setText(savedInstanceState.getString(updated_field_bundle_id));
//            weatherIcon.setText(savedInstanceState.getString(weather_icon_bundle_id));
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString(city_field_bundle_id,cityField.getText().toString());
//        outState.putString(detail_field_bundle_id, detailsField.getText().toString());
//        outState.putString(current_temperature_bundle_id, currentTemperatureField.getText().toString());
//        outState.putString(humidity_field_bundle_id, humidity_field.getText().toString());
//        outState.putString(pressure_field_bundle_id, pressure_field.getText().toString());
//        outState.putString(updated_field_bundle_id, updatedField.getText().toString());
//        outState.putString(weather_icon_bundle_id, weatherIcon.getText().toString());

        city_field_bundle_id = cityField.getText().toString();
        detail_field_bundle_id = detailsField.getText().toString();
        current_temperature_bundle_id =  currentTemperatureField.getText().toString();
        humidity_field_bundle_id =  humidity_field.getText().toString();
        pressure_field_bundle_id =  pressure_field.getText().toString();
        updated_field_bundle_id = updatedField.getText().toString();

        Log.d(TAG,"onSaveInstanceState() called");
        //Save the fragment's state here
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        Log.d(TAG,"onCreate() called");
//    }

    @Override
    public void onResume(){
        super.onResume();
        if(lastFetched==null){
            checkLocationPermission();
        }else{
            DateTime currDate = new DateTime();
            Interval interval = new Interval(lastFetched,currDate);
            if (interval.toDuration().getStandardMinutes() > 30) {
                checkLocationPermission();
            }
        }

        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder dialog =  new AlertDialog.Builder(getActivity());
                dialog.setTitle(R.string.title_location_permission);
                dialog.setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                requestPermissions(
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            if(locationService==null) {
                locationService = LocationService.getLocationManager(getActivity());
            }
            locationService.getUpdateLocation(getContext());
            taskLoadUp(city, view);
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationService = LocationService.getLocationManager(getActivity());
                        locationService.getUpdateLocation(getContext());
                        taskLoadUp(city, view);
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Location not enabled, using default", Toast.LENGTH_LONG).show();
                    taskLoadUp(city, view);
                }
                return;
            }
        }
    }
    @Override
    public void onClick(View v) {

    }


    public void taskLoadUp(String query, View v) {

        if (Function.isNetworkAvailable(getActivity().getApplicationContext())) {
            DownloadWeather task = new DownloadWeather(getActivity(), v);
            task.execute(query);
            lastFetched = new DateTime();
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadWeather extends AsyncTask< String, Void, String > {
        private Context activity;
        private View rootView;
        public DownloadWeather(Activity activity , View v){
            this.activity = activity;
            this.rootView = v;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String...args) {
            String xml = "";
            if(LocationService.longitude!=null && LocationService.latitude!=null){
                xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?lat="+LocationService.latitude+ "&lon="+LocationService.longitude + "&units=imperial&appid=" + OPEN_WEATHER_MAP_API);
            }else{
                xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                        "&units=imperial&appid=" + OPEN_WEATHER_MAP_API);
            }

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {
            super.onPostExecute(xml);
            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {

                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    JSONObject snow = json.has("rain")? json.getJSONObject("rain") : null;
                    JSONObject rain = json.has("rain")? json.getJSONObject("snow") : null;
                    if(snow!=null){
                        String snowValue = snow.getString("3h");
                        if(snowValue.equals("0") || snowValue.equals("")){
                            hasSnowed = false;
                        }else{
                            hasSnowed = true;
                        }
                    }
                    if(rain!=null) {
                        String rainValue = rain.getString("3h");
                        if(rainValue.equals("0") || rainValue.equals("")){
                            hasRained = false;
                        }else{
                            hasRained = true;
                        }
                    }

                    DateFormat df = DateFormat.getDateTimeInstance();

                    loader = getView().findViewById(R.id.loader);
//                    selectCity = getView().findViewById(R.id.selectCity);
                    cityField = getView().findViewById(R.id.city_field);
                    updatedField = getView().findViewById(R.id.updated_field);
                    detailsField = getView().findViewById(R.id.details_field);
                    currentTemperatureField = getView().findViewById(R.id.current_temperature_field);
                    humidity_field = getView().findViewById(R.id.humidity_field);
                    pressure_field = getView().findViewById(R.id.pressure_field);
                    weatherIcon = getView().findViewById(R.id.weather_icon);
                    weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
                    weatherIcon.setTypeface(weatherFont);

                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "°");
                    temperatureValue = (int) main.getDouble("temp");
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    weather_icon_bundle_id = Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000);
                    weatherIcon.setText(Html.fromHtml(weather_icon_bundle_id));

                    loader.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }


        }

    }



}