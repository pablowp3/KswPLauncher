package com.wits.ksw.launcher.als_id7.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wits.ksw.R;
import com.wits.ksw.databinding.HicarCarInfo;
import com.wits.ksw.launcher.als_id7.model.AlsID7ViewModel;
import com.wits.ksw.launcher.model.McuImpl;

public class ZlinkWeatherFragment extends Fragment {
    private com.wits.ksw.databinding.ZlinkWeatherFragment binding;
    private HicarCarInfo hicarBinding;
    private AlsID7ViewModel viewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CarFragment", "onCreate: CarFragment");
        this.viewModel = (AlsID7ViewModel) ViewModelProviders.of(getActivity()).get(AlsID7ViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewModel.hicar.set(false);
        com.wits.ksw.databinding.ZlinkWeatherFragment zlinkWeatherFragment = (com.wits.ksw.databinding.ZlinkWeatherFragment) DataBindingUtil.inflate(inflater, R.layout.als_id7_fragment_zlink_weather, (ViewGroup) null, false);
        this.binding = zlinkWeatherFragment;
        return zlinkWeatherFragment.getRoot();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        com.wits.ksw.databinding.ZlinkWeatherFragment zlinkWeatherFragment = this.binding;
        if (zlinkWeatherFragment != null) {
            zlinkWeatherFragment.setZlinkWeatherViewModel(this.viewModel);
        }
    }

    public void onResume() {
        super.onResume();
        McuImpl.getInstance().setUint();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
