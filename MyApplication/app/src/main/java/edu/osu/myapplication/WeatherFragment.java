package edu.osu.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class WeatherFragment extends Fragment implements View.OnClickListener{

    public TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    public ProgressBar loader;
    public Typeface weatherFont;
    public String city = "Columbus,US";
    public String OPEN_WEATHER_MAP_API = "2d19f5e9eb5f8e1698ce84001ccbeddf";
//

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    String requiredPermission = "android.permission.ACCESS_FINE_LOCATION";
    String requiredPermission2 = "android.permission.ACCESS_COARSE_LOCATION";
    int hasLocationPerm = getActivity().checkCallingOrSelfPermission(requiredPermission);
    int hasLocationPerm2 = getActivity().checkCallingOrSelfPermission(requiredPermission2);
    if(hasLocationPerm==PackageManager.PERMISSION_GRANTED && hasLocationPerm2 == PackageManager.PERMISSION_GRANTED){
        LocationService locationService = LocationService.getLocationManager(getActivity());
    }else{
        Toast.makeText(getActivity().getApplicationContext(), "Location not enabled, using default", Toast.LENGTH_LONG).show();
    }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_weather, container, false);

        loader = v.findViewById(R.id.loader);
        cityField = v.findViewById(R.id.city_field);
        updatedField = v.findViewById(R.id.updated_field);
        detailsField = v.findViewById(R.id.details_field);
        currentTemperatureField = v.findViewById(R.id.current_temperature_field);
        humidity_field = v.findViewById(R.id.humidity_field);
        pressure_field = v.findViewById(R.id.pressure_field);
        weatherIcon = v.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);
        taskLoadUp(city , v);

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }


    @Override
    public void onClick(View v) {

    }


    public void taskLoadUp(String query, View v) {
        if (Function.isNetworkAvailable(getActivity().getApplicationContext())) {
            DownloadWeather task = new DownloadWeather(getActivity(), v);
            task.execute(query);
        } else {
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
           loader.setVisibility(View.VISIBLE);

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
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));

                   loader.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }


        }



    }



}
