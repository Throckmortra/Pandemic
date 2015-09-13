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
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;
import com.reimaginebanking.api.java.models.Geocode;
import com.reimaginebanking.api.java.models.Merchant;
import com.reimaginebanking.api.java.models.Purchase;

import java.util.ArrayList;
import java.util.List;

import ramhacks.pandemic.Models.MerchantWeight;

public class HeatMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private TileOverlay mOverlayz;
    private List<WeightedLatLng> mLocations;
    private List<Purchase> mPurchases;
    private List<Merchant> mMerchants;
    private List<MerchantWeight> mMerchantWeight;
    private NessieClient nessieClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        setUpMapIfNeeded();
        mLocations = new ArrayList<>();
        mPurchases = new ArrayList<>();
        mMerchants = new ArrayList<>();
        mMerchantWeight = new ArrayList<>();
        nessieClient = NessieClient.getInstance();
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

    private void createHeatMap(List<WeightedLatLng> locations) {
        mProvider = new HeatmapTileProvider.Builder()
                .weightedData(locations)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    private void getLocations() {
        final NessieClient nessieClient = NessieClient.getInstance();
        nessieClient.setAPIKey("3a942e124e29f5d830e92f88808e096b");

        nessieClient.getMerchants("37", "-77", "1000", new NessieResultsListener() {
            @Override
            public void onSuccess(Object result, NessieException e) {
                if (e == null) {
                    //There is no error, do whatever you need here.
                    // Cast the result object to the type that you are requesting and you are good to go
                    if (result == null) {
                        return;
                    }

                    mMerchants = (ArrayList<Merchant>) result;

                    getPurchases();
                } else {
                    //There was an error. Handle it here
                    Log.e("Error", e.toString());
                }
            }
        });

    }

    private void getPurchases() {
        nessieClient.getPurchases("55e94a6cf8d8770528e6169b", new NessieResultsListener() {
            @Override
            public void onSuccess(Object result, NessieException e) {
                if (e == null) {
                    //There is no error, do whatever you need here.
                    // Cast the result object to the type that you are requesting and you are good to go
                    if (result == null) {
                        return;
                    }

                    mPurchases = (ArrayList<Purchase>) result;

                    computeWeight();
                } else {
                    //There was an error. Handle it here
                    Log.e("Error", e.toString());
                }
            }
        });
    }

    private void computeWeight() {
        int counter = 0;

        for (Merchant m : mMerchants) {
            counter++;
            if (counter < 1500) {
                continue;
            }
            
            if (m == null) {
                continue;
            }

            String id = m.get_id();
            String name = m.getName();

            Geocode geocode = m.getGeocode();

            if (geocode == null) {
                continue;
            }

            mMerchantWeight.add(new MerchantWeight(id, name, geocode, 1));
        }

        for (MerchantWeight mw : mMerchantWeight) {
            for (Purchase p : mPurchases) {
                if (mw.getId().equals(p.getMerchant_id())) {
                    mw.incrementWeight();
                }
            }
        }

        for (MerchantWeight mw : mMerchantWeight) {
            double lat =  mw.getGeocode().getLat();
            double lng = mw.getGeocode().getLng();
            double weight = mw.getWeight();
            mLocations.add(new WeightedLatLng(new LatLng(lat, lng), weight));
        }

        createHeatMap(mLocations);
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
