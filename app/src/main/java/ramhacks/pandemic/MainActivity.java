package ramhacks.pandemic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ramhacks.pandemic.Interfaces.OnFragmentActionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentActionListener {

//    @Bind(R.id.fragment_container)
//    public FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        NessieClient nessieClient = NessieClient.getInstance();
//        nessieClient.setAPIKey("ea4cc6f6e2755426e5e060913d3d1ef0");


        //replaceWithParameterFragment();
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


}