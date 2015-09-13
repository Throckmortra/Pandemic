package ramhacks.pandemic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.reimaginebanking.api.java.Models.Merchant;
import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ramhacks.pandemic.Interfaces.OnFragmentActionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentActionListener,
        AdapterView.OnItemSelectedListener {

    @Bind(R.id.radius_spinner)
    public Spinner mSpinner;
    private List<LatLng> mLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSpinner();

//        NessieClient nessieClient = NessieClient.getInstance();
//        nessieClient.setAPIKey("3a942e124e29f5d830e92f88808e096b");
//
//        nessieClient.getMerchants(new NessieResultsListener() {
//            @Override
//            public void onSuccess(Object result, NessieException e) {
//                if(e == null){
//                    //There is no error, do whatever you need here.
//                    // Cast the result object to the type that you are requesting and you are good to go
//                    ArrayList<Merchant> customers = (ArrayList<Merchant>) result;
//
//                    for (int i = 0; i < customers.size(); i++) {
//                        float lat = customers.get(i).getGeocode().getLat();
//                        float lng = customers.get(i).getGeocode().getLng();
//                        mLocations.add(new LatLng(lat, lng));
//                    }
//
//                } else {
//                    //There was an error. Handle it here
//                    Log.e("Error", e.toString());
//                }
//            }
//        });
//
//
//        //replaceWithParameterFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OnClick(R.id.btn_heatmap)
    public void segueToHeatActivity(){
        Log.d("ayy", "lmao");
        Intent intent = new Intent(this, HeatMapActivity.class);
        this.startActivity(intent);
    }

    private void setSpinner(){
        String[] radiusNums = new String[5];
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
        mSpinner.setSelection(5, false);
        //mSpinner.setOnItemClickListener(this);
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
    public void dismissProgressDialog() {

    }

    @Override
    public void replaceFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void showProgressDialog() {

    }

    private void replaceWithParameterFragment(){
        ParametersFragment fragment = ParametersFragment.newInstance();
        replaceFragment(fragment);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("ayy", "lmao");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}