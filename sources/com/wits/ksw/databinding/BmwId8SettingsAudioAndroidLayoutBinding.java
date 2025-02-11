package com.wits.ksw.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.wits.ksw.R;
import com.wits.ksw.launcher.bmw_id8_ui.view.ID8ProgressBar;
import com.wits.ksw.settings.bmw_id8.vm.BmwId8SettingsViewModel;

public abstract class BmwId8SettingsAudioAndroidLayoutBinding extends ViewDataBinding {
    public final ImageButton bmwId8SettingsAndroidCallAddBtn;
    public final ID8ProgressBar bmwId8SettingsAndroidCallSeekbar;
    public final ImageButton bmwId8SettingsAndroidCallSubBtn;
    public final RelativeLayout bmwId8SettingsAudioAndroidLay;
    public final ImageButton bmwId8SettingsMeidaAddBtn;
    public final ID8ProgressBar bmwId8SettingsMeidaSeekbar;
    public final ImageButton bmwId8SettingsMeidaSubBtn;
    @Bindable
    protected BmwId8SettingsViewModel mViewModel;

    public abstract void setViewModel(BmwId8SettingsViewModel bmwId8SettingsViewModel);

    protected BmwId8SettingsAudioAndroidLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageButton bmwId8SettingsAndroidCallAddBtn2, ID8ProgressBar bmwId8SettingsAndroidCallSeekbar2, ImageButton bmwId8SettingsAndroidCallSubBtn2, RelativeLayout bmwId8SettingsAudioAndroidLay2, ImageButton bmwId8SettingsMeidaAddBtn2, ID8ProgressBar bmwId8SettingsMeidaSeekbar2, ImageButton bmwId8SettingsMeidaSubBtn2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bmwId8SettingsAndroidCallAddBtn = bmwId8SettingsAndroidCallAddBtn2;
        this.bmwId8SettingsAndroidCallSeekbar = bmwId8SettingsAndroidCallSeekbar2;
        this.bmwId8SettingsAndroidCallSubBtn = bmwId8SettingsAndroidCallSubBtn2;
        this.bmwId8SettingsAudioAndroidLay = bmwId8SettingsAudioAndroidLay2;
        this.bmwId8SettingsMeidaAddBtn = bmwId8SettingsMeidaAddBtn2;
        this.bmwId8SettingsMeidaSeekbar = bmwId8SettingsMeidaSeekbar2;
        this.bmwId8SettingsMeidaSubBtn = bmwId8SettingsMeidaSubBtn2;
    }

    public BmwId8SettingsViewModel getViewModel() {
        return this.mViewModel;
    }

    public static BmwId8SettingsAudioAndroidLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BmwId8SettingsAudioAndroidLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (BmwId8SettingsAudioAndroidLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.bmw_id8_settings_audio_android_layout, root, attachToRoot, component);
    }

    public static BmwId8SettingsAudioAndroidLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BmwId8SettingsAudioAndroidLayoutBinding inflate(LayoutInflater inflater, Object component) {
        return (BmwId8SettingsAudioAndroidLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.bmw_id8_settings_audio_android_layout, (ViewGroup) null, false, component);
    }

    public static BmwId8SettingsAudioAndroidLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BmwId8SettingsAudioAndroidLayoutBinding bind(View view, Object component) {
        return (BmwId8SettingsAudioAndroidLayoutBinding) bind(component, view, R.layout.bmw_id8_settings_audio_android_layout);
    }
}
