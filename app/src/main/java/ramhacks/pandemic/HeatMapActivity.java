package ramhacks.pandemic;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.reimaginebanking.api.java.Models.Geocode;
import com.reimaginebanking.api.java.Models.Merchant;
import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HeatMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private TileOverlay mOverlayz;
    private ArrayList<LatLng> mLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        setUpMapIfNeeded();
        mLocations = new ArrayList<>();

        getLocations();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void createHeatMap(List<LatLng> locations){

        mProvider = new HeatmapTileProvider.Builder()
                .data(locations)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    private void getLocations(){
        NessieClient nessieClient = NessieClient.getInstance();
        nessieClient.setAPIKey("3a942e124e29f5d830e92f88808e096b");

        HashMap body = new HashMap<>(3);
        body.put("lat", 33);
        body.put("lng", -77);
        body.put("rad", 100);


        nessieClient.getMerchants(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result, NessieException e) {
                if (e == null) {
                    //There is no error, do whatever you need here.
                    // Cast the result object to the type that you are requesting and you are good to go
                    if(result == null){
                        return;
                    }

                    ArrayList<Merchant> customers = (ArrayList<Merchant>) result;

                    for (int i = 0; i < customers.size(); i++) {
                        LatLng temp;
                        Merchant merchant = customers.get(i);

                        if(merchant==null){
                            continue;
                        }

                        Geocode geocode = merchant.getGeocode();

                        if(geocode == null){
                            continue;
                        }
                        double lat = geocode.getLat();
                        double lng = geocode.getLng();

                        if(lat != 0.0 && lng != 0.0){
                            temp = new LatLng(lat,lng);
                            mLocations.add(temp);
                        }
                        //LatLng temp = new LatLng(latz,lngz);
//                        if(temp != null){
//                            mLocations.add(i, temp);
//                        }

                        Log.d("ayyy", customers.get(i).getName() + "   " + lat + "  " + lng + "      #"+ i);
                    }
                    createHeatMap(mLocations);

                } else {
                    //There was an error. Handle it here
                    Log.e("Error", e.toString());
                }
            }
        });

    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
