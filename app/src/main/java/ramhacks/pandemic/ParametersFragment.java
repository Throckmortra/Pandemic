package ramhacks.pandemic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import butterknife.Bind;
import butterknife.OnClick;
import ramhacks.pandemic.Interfaces.OnFragmentActionListener;


public class ParametersFragment extends Fragment {

    //@Bind(R.id.btn_heatmap) public Button mBtnHeatMap;
    private OnFragmentActionListener mFragmentActionCallback;

    public static ParametersFragment newInstance() {
        ParametersFragment fragment = new ParametersFragment();
        return fragment;
    }

    public ParametersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @OnClick(R.id.btn_heatmap)
//    public void segueToHeatActivity(){
//        Log.d("ayyy", "lmao");
//        Intent intent = new Intent(getActivity(), HeatMapActivity.class);
//        getActivity().startActivity(intent);
//    }

    @OnClick(R.id.btn_heatmap)
    public void segueToHeatActivity(){
        Log.d("ayy","lmao");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parameters, container, false);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentActionCallback = (OnFragmentActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentActionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
