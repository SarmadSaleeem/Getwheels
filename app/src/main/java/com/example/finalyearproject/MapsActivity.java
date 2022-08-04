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
import android.os.Handler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    List<Address> list1;
    List<Address> list2;

    GoogleMap map;
    MarkerOptions current_marker;
    MarkerOptions destination_marker;

    Polyline polyline=null;

    String Name="";
    String Dp_Uri="";

    String Current_Location;
    String Destination_Location;

    LatLng current;
    LatLng destination;

    double lat_C;
    double Long_C;
    double Lat_D;
    double Long_D;

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

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger").child("Basic Info")
                .child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger").child("Basic Info")
                .child("DP Uri").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Dp_Uri=dataSnapshot.getValue(String.class);
            }
        });

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

                    Geocoder geocoder=new Geocoder(MapsActivity.this);

                    try {
                        list1=geocoder.getFromLocationName(Current_Location,1);

                        if(Current_Location!=null) {
                            lat_C = list1.get(0).getLatitude();
                            Long_C = list1.get(0).getLongitude();
                            current = new LatLng(lat_C, Long_C);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    try {
                        list2=geocoder.getFromLocationName(Destination_Location,1);
                        Lat_D=list2.get(0).getLatitude();
                        Long_D=list2.get(0).getLongitude();
                        destination=new LatLng(Lat_D,Long_D);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                            /*.observe(MapsActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {

                            firebaseDatabase.getReference("Booking Requests").child(firebaseAuth.getCurrentUser().getUid())
                                    .child("Passenger_Name").setValue(Name);

                            firebaseDatabase.getReference("Booking Requests").child(firebaseAuth.getCurrentUser().getUid())
                                    .child("Passenger_DP").setValue(Dp_Uri);

                            firebaseDatabase.getReference("Booking Requests").child(firebaseAuth.getCurrentUser().getUid()).child("Pick_Up_Location")
                                    .setValue(s);
                            Current_Location=s;

                            Current=s;

                            try {
                                current_database_location=geocoder.getFromLocationName(s,1);

                                if (current_database_location != null){
                                    pick_up_location_latitude= current_database_location.get(0).getLatitude();
                                    pick_up_location_longitude=current_database_location.get(0).getLongitude();

                                    latLng_current_for_database= new LatLng(pick_up_location_latitude,pick_up_location_longitude);

                                    //firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid())
                                      //      .child("Pick_Up_Location_Coordinates").setValue(latLng_current_for_database);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                             */





                        /*    .observe(MapsActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Destination=s;

                            firebaseDatabase.getReference("Booking Requests").child(firebaseAuth.getCurrentUser().getUid()).child("Drop_Location")
                                    .setValue(s);

                            String id=firebaseAuth.getCurrentUser().getUid();

                            adaptorRequests adaptor =new adaptorRequests(id);

                            try {
                                destination_database_location=geocoder.getFromLocationName(s,1);

                                if(destination_database_location != null){
                                    drop_location_latitude=destination_database_location.get(0).getLatitude();
                                    drop_location_longitude=destination_database_location.get(0).getLongitude();

                                    latLng_destination_for_database= new LatLng(drop_location_latitude,drop_location_longitude);

                                    //firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid())
                                     //       .child("Drop_Location_Coordinates").setValue(latLng_destination_for_database);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });




                         */

                    firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid()).child("Pick_Up_Location")
                            .setValue(Current_Location);

                    firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid()).child("Drop_Location")
                            .setValue(Destination_Location);

                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger")
                            .child("Basic Info").child("username").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Name=dataSnapshot.getValue(String.class);
                        }
                    });

                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger")
                            .child("Basic Info").child("DP Uri").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Dp_Uri = dataSnapshot.getValue(String.class);
                        }
                    });

                    firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger_Name")
                            .setValue(Name);

                    firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger_DP")
                            .setValue(Dp_Uri);

                    List<PatternItem> pattern=Arrays.<PatternItem> asList(new Dash(30),new Gap(20));

                    if(polyline != null) polyline.remove();

                    PolylineOptions polylineOptions=new PolylineOptions().add(current,destination).clickable(true);

                    polyline=map.addPolyline(polylineOptions.width(10).color(Color.MAGENTA).geodesic(false).pattern(pattern));

                    final Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firebaseDatabase.getReference("Booking Details").child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                        }
                    },15000);
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

                        /*
                        MarkerOptions one =new MarkerOptions().position(current).title("Pick_Up_Location");
                        map.addMarker(one);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current,15));




                        MarkerOptions two=new MarkerOptions().position(destination).title("Drop_Location");
                        map.addMarker(two);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(destination,15));
                        
                         */

                        location_data.getCurrent_location().observe(MapsActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                try {

                                    Current_Location=s;
                                    addressList_current=geocoder.getFromLocationName(s,1);
                                    if(addressList_current != null){

                                        double latitude_Current_Location= addressList_current.get(0).getLatitude();
                                        double longitude_Current_Location=addressList_current.get(0).getLongitude();

                                        latLng_current=new LatLng(latitude_Current_Location,longitude_Current_Location);

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
                                Destination_Location=s;
                                try {
                                    addressList_destination=geocoder.getFromLocationName(s,1);

                                    if(addressList_destination != null){
                                        double latitude_destination=addressList_destination.get(0).getLatitude();
                                        double longitude_destination=addressList_destination.get(0).getLongitude();

                                        latLng_destination=new LatLng(latitude_destination,longitude_destination);

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