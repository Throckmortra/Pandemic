package ramhacks.pandemic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.radius_spinner)
    public Spinner mSpinner;

    @Bind(R.id.btn_getlocation)
    public Button mLocationBtn;

    private String[] radiusNums;
    private String mSelectedRadius;
    private ProgressDialog mProgressDialog;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private Context mContext;
    private boolean mFirstLocation;
    private double mLat;
    private double mLng;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final long MIN_TIME = 10000;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLocationRequest = LocationRequest.create();
        buildGoogleApi();
        mFirstLocation = false;
        mContext = this;
        setSpinner();
        mSpinner.setOnItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, OrangeTabsActivity.class);
//                intent.putExtra("buttonID", 2 + "");
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick(R.id.btn_heatmap)
    public void segueToHeatActivity(){
        Intent intent = new Intent(this, HeatMapActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_getlocation)
    public void locationCheck(){
        googleCallback();
    }

    public void buildGoogleApi(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }


    public void googleCallback(){
        Log.d("Testing GoogleCallBack", "The Streets is watching");
        mGoogleApiClient.connect();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            //status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private void getLocation(){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(MIN_TIME);
        mLocationRequest.setFastestInterval(MIN_TIME);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d("Result", "Okay");
                        getLocation();
                        showProgressDialog();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Log.d("RESULT", "NOT OKAY");
                        break;
                    default:
                        Log.d("RESULT", "DEFAULT");
                        break;
                }
                break;
        }
    }


    private void setSpinner(){
        radiusNums = new String[5];
        radiusNums[0] = "10 miles";
        radiusNums[1] = "25 miles";
        radiusNums[2] = "100 miles";
        radiusNums[3] = "500 miles";
        radiusNums[4] = "1000 miles";


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, radiusNums);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setSelection(4, false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectedRadius = radiusNums[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading data...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.getLongitude() != 0.0 && location.getLatitude() != 0.0) {
            LatLng latLng = new LatLng(location.getLongitude(), location.getLatitude());
            Log.d("Test callback", "" + latLng.latitude + "  " + latLng.longitude);
            if(!mFirstLocation) {
                mLocationBtn.setText("" + latLng.latitude + "  " + latLng.longitude);
                mLat = latLng.latitude;
                mLng = latLng.longitude;
                mFirstLocation = true;
            }
            try{
                dismissProgressDialog();
            } catch(NullPointerException e){}
        }
        Log.d("the location", location.getLongitude() + "  " + location.getLatitude());
    }

    @Override
    public void onConnected(Bundle bundle) {
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        try{
            fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch(NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}