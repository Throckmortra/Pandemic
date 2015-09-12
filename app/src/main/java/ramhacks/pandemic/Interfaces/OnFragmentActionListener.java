package ramhacks.pandemic.Interfaces;

/**
 * Created by aaron on 9/12/15.
 */
import android.support.v4.app.Fragment;

public interface OnFragmentActionListener {
    void dismissProgressDialog();
    void replaceFragment(Fragment fragment);
    void showProgressDialog();
}