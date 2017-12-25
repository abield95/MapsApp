package com.codebrain.minato.mapsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class TravelActivity extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST_ORIGIN = 1;
    private static final int PLACE_PICKER_REQUEST_DESTINY = 2;
    private EditText place_origin_name, place_destiny_name;
    Activity activity;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        activity = this;

        Toolbar toolbar = findViewById(R.id.personal_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }

        place_origin_name = findViewById(R.id.travel_place_name_origin);
        place_destiny_name = findViewById(R.id.travel_place_name_destiny);

        ImageView origin = findViewById(R.id.travel_pick_origin);
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try
                //{
//                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                    startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
                    Intent intent = new Intent(getApplicationContext(), PlacePickerActivity.class);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST_ORIGIN);
//                }
//                catch (GooglePlayServicesRepairableException e)
//                {
//                    Log.d("Exception: ", e.getMessage());
//                }
//                catch (GooglePlayServicesNotAvailableException e)
//                {
//                    Log.d("Exception: ", e.getMessage());
//                }
            }
        });

        ImageView destiny = findViewById(R.id.travel_pick_destiny);
        destiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlacePickerActivity.class);
                startActivityForResult(intent, PLACE_PICKER_REQUEST_DESTINY);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        Intent res = new Intent();
        setResult(RESULT_CANCELED, res);
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PLACE_PICKER_REQUEST_ORIGIN:
                if (resultCode == RESULT_OK) {
                    //Place place = PlacePicker.getPlace(data, this);
                    Bundle bundle = data.getExtras();
                    String toastMsg = "Name" + bundle.getString("place_name") +
                            "place id " + bundle.getString("place_id") +
                            "Latitude: " + bundle.getDouble("location_lat") +
                            " longitude: " + bundle.getDouble("location_lon");//String.format("Place: %s %s", place.getAddress(), place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                    place_origin_name.setText(bundle.getString("place_name"));
                }
                break;
            case PLACE_PICKER_REQUEST_DESTINY:
                if (resultCode == RESULT_OK) {
                    //Place place = PlacePicker.getPlace(data, this);
                    Bundle bundle = data.getExtras();
                    String toastMsg = "Name" + bundle.getString("place_name") +
                            "place id " + bundle.getString("place_id") +
                            "Latitude: " + bundle.getDouble("location_lat") +
                            " longitude: " + bundle.getDouble("location_lon");//String.format("Place: %s %s", place.getAddress(), place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                    place_destiny_name.setText(bundle.getString("place_name"));
                }
                break;
        }
    }
}
