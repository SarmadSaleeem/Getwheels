package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.RoutingSessionInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    EditText from_location;
    EditText to_location;

    Button submit_Request;

    List<Place.Field> fields;
    LatLng latLng_current;
    LatLng latLng_destination;
    Geocoder geocoder;

    LatLng latLng;

    List<Address> addressList_destination;
    List<Address> addressList_current;

    public Location_Data location_data;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    List<Address> current_database_location;
    List<Address> destination_database_location;

    GoogleMap map;

    LatLng latLng_current_for_database;
    LatLng latLng_destination_for_database;

    double pick_up_location_latitude;
    double pick_up_location_longitude;

    double drop_location_latitude;
    double drop_location_longitude;

    String Current;
    String Destination;

    MarkerOptions current_marker;
    MarkerOptions destination_marker;

    Polyline polyline=null;

    List<LatLng> latLngList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        location_data = new ViewModelProvider(this).get(Location_Data.class);
        location_data.initiliza();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);

        from_location=findViewById(R.id.from_location);
        to_location=findViewById(R.id.to_location);
        submit_Request=findViewById(R.id.submit_request);

        geocoder=new Geocoder(MapsActivity.this);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        Places.initialize(getApplicationContext(),"AIzaSyCBTXJYqQUzL1vaqJc7FQ4aUl0L3KIfWGE");

        PlacesClient placesClient=Places.createClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        from_location.setFocusable(false);
        from_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fields=Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(MapsActivity.this);
                startActivityForResult(intent,100);

                location_data.setCurrent_location(from_location.getText().toString());

            }
        });

        to_location.setFocusable(false);
        to_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fields=Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(MapsActivity.this);
                startActivityForResult(intent,200);

                location_data.setDestination_location(to_location.getText().toString());
            }
        });

        submit_Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(from_location.getText().toString().isEmpty()){
                    Toast.makeText(MapsActivity.this, "Location Required", Toast.LENGTH_SHORT).show();
                }

                else if(to_location.getText().toString().isEmpty()){
                    Toast.makeText(MapsActivity.this, "Location Required", Toast.LENGTH_SHORT).show();
                }

                else {
                    location_data.getCurrent_location().observe(MapsActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("User_Locations").child("Pick_Up_Location")
                                    .setValue(s);

                            Current=s;

                            try {
                                current_database_location=geocoder.getFromLocationName(s,1);

                                if (current_database_location != null){
                                    pick_up_location_latitude= current_database_location.get(0).getLatitude();
                                    pick_up_location_longitude=current_database_location.get(0).getLongitude();

                                    latLng_current_for_database= new LatLng(pick_up_location_latitude,pick_up_location_longitude);

                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("User_Locations")
                                            .child("Pick_Up_Location_Coordinates").setValue(latLng_current_for_database);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    location_data.getDestination_location().observe(MapsActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Destination=s;

                            firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("User_Locations").child("Drop_Location")
                                    .setValue(s);

                            try {
                                destination_database_location=geocoder.getFromLocationName(s,1);

                                if(destination_database_location != null){
                                    drop_location_latitude=destination_database_location.get(0).getLatitude();
                                    drop_location_longitude=destination_database_location.get(0).getLongitude();

                                    latLng_destination_for_database= new LatLng(drop_location_latitude,drop_location_longitude);

                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("User_Locations")
                                            .child("Drop_Location_Coordinates").setValue(latLng_destination_for_database);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    List<PatternItem> pattern=Arrays.<PatternItem> asList(new Dash(30),new Gap(20));

                   // DisplayTrack(Current,Destination);

                    if(polyline != null) polyline.remove();

                    PolylineOptions polylineOptions=new PolylineOptions().add(latLng_current_for_database,latLng_destination_for_database).clickable(true);

                    polyline=map.addPolyline(polylineOptions.width(10).color(Color.MAGENTA).geodesic(false).pattern(pattern));
                }
            }
        });

    }
  /*  private void DisplayTrack(String current, String destination) {

        try {

           Uri uri= Uri.parse("https://www.google.co.in/maps/dir/" + current + "/" + destination);

            Intent intent=new Intent(Intent.ACTION_VIEW, uri);

            intent.setPackage("com.google.android.apps.maps");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


        }

        catch (ActivityNotFoundException e){

            //Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.maps");

            //Intent intent=new Intent(Intent.ACTION_VIEW, uri);

            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //startActivity(intent);

            Toast.makeText(this, "Maps Not Installed", Toast.LENGTH_SHORT).show();
        }


    }

   */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            from_location.setText(place.getAddress());

        }

        else if(requestCode==200 && resultCode==RESULT_OK)
        {
            Place place=Autocomplete.getPlaceFromIntent(data);
            to_location.setText(place.getAddress());
        }
    }

    private void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {

                        map=googleMap;

                       latLng=new LatLng(location.getLatitude(),location.getLongitude());
                       map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));

                        location_data.getCurrent_location().observe(MapsActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                try {
                                    addressList_current=geocoder.getFromLocationName(s,1);
                                    if(addressList_current != null){

                                        double latitude_Current_Location= addressList_current.get(0).getLatitude();
                                        double longitude_Current_Location=addressList_current.get(0).getLongitude();

                                        latLng_current=new LatLng(latitude_Current_Location,longitude_Current_Location);
                                        //latLngList.add(latLng_current);

                                        current_marker=new MarkerOptions().position(latLng_current).title("Pick_Up_Location");
                                        map.addMarker(current_marker);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_current,16));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Location_Error", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        location_data.getDestination_location().observe(MapsActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                try {
                                    addressList_destination=geocoder.getFromLocationName(s,1);

                                    if(addressList_destination != null){
                                        double latitude_destination=addressList_destination.get(0).getLatitude();
                                        double longitude_destination=addressList_destination.get(0).getLongitude();

                                        latLng_destination=new LatLng(latitude_destination,longitude_destination);
                                       // latLngList.add(latLng_destination);

                                        destination_marker=new MarkerOptions().position(latLng_destination).title("Destination_Location");
                                        map.addMarker(destination_marker);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_destination,14));
                                    }

                                    else {
                                        Toast.makeText(MapsActivity.this, "Location Error", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });




            }
        });
    }
}