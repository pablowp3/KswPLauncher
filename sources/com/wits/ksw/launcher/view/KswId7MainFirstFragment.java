package com.wits.ksw.launcher.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wits.ksw.BuildConfig;
import com.wits.ksw.R;
import com.wits.ksw.databinding.KswId7MainPage1Fragment;
import com.wits.ksw.launcher.model.LauncherViewModel;
import com.wits.ksw.launcher.model.MediaImpl;
import com.wits.pms.IContentObserver;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class KswId7MainFirstFragment extends Fragment {
    private static final String TAG = "KswId7MainFirstFragment";
    private KswId7MainPage1Fragment binding;
    private IContentObserver.Stub topAppContentObserver = new IContentObserver.Stub() {
        public void onChange() throws RemoteException {
            try {
                String topApp = PowerManagerApp.getStatusString("topApp");
                Log.i(KswId7MainFirstFragment.TAG, "onChange: topApp=" + topApp);
                if (TextUtils.equals(topApp, BuildConfig.APPLICATION_ID)) {
                    MediaImpl.getInstance().initData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private LauncherViewModel viewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = (LauncherViewModel) ViewModelProviders.of(getActivity()).get(LauncherViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KswId7MainPage1Fragment kswId7MainPage1Fragment = (KswId7MainPage1Fragment) DataBindingUtil.inflate(inflater, R.layout.ksw_id7_main_page1, (ViewGroup) null, false);
        this.binding = kswId7MainPage1Fragment;
        return kswId7MainPage1Fragment.getRoot();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.binding.setMediaViewModel(this.viewModel);
        PowerManagerApp.registerIContentObserver("topApp", this.topAppContentObserver);
        Log.i(TAG, "onActivityCreated: registerIContentObserver topApp ");
    }

    public void onDestroy() {
        super.onDestroy();
        PowerManagerApp.unRegisterIContentObserver(this.topAppContentObserver);
        Log.i(TAG, "onDestroy: unRegisterIContentObserver topApp");
    }

    public void hideArrow() {
        KswId7MainPage1Fragment kswId7MainPage1Fragment = this.binding;
        if (kswId7MainPage1Fragment != null) {
            kswId7MainPage1Fragment.ivArrow.setVisibility(4);
        }
    }

    public void showArrow() {
        KswId7MainPage1Fragment kswId7MainPage1Fragment = this.binding;
        if (kswId7MainPage1Fragment != null) {
            kswId7MainPage1Fragment.ivArrow.setVisibility(0);
        }
    }
}
