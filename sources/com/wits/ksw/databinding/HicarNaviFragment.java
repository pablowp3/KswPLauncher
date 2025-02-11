package com.wits.ksw.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wits.ksw.R;
import com.wits.ksw.launcher.model.LauncherViewModel;

public abstract class HicarNaviFragment extends ViewDataBinding {
    public final Id7SubHicarViewBinding hicarLayout;
    @Bindable
    protected LauncherViewModel mNaviViewModel;
    public final NaviSubView naviLayout;

    public abstract void setNaviViewModel(LauncherViewModel launcherViewModel);

    protected HicarNaviFragment(Object _bindingComponent, View _root, int _localFieldCount, Id7SubHicarViewBinding hicarLayout2, NaviSubView naviLayout2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.hicarLayout = hicarLayout2;
        this.naviLayout = naviLayout2;
    }

    public LauncherViewModel getNaviViewModel() {
        return this.mNaviViewModel;
    }

    public static HicarNaviFragment inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HicarNaviFragment inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (HicarNaviFragment) ViewDataBinding.inflateInternal(inflater, R.layout.id7_fragment_navi_hicar, root, attachToRoot, component);
    }

    public static HicarNaviFragment inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HicarNaviFragment inflate(LayoutInflater inflater, Object component) {
        return (HicarNaviFragment) ViewDataBinding.inflateInternal(inflater, R.layout.id7_fragment_navi_hicar, (ViewGroup) null, false, component);
    }

    public static HicarNaviFragment bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HicarNaviFragment bind(View view, Object component) {
        return (HicarNaviFragment) bind(component, view, R.layout.id7_fragment_navi_hicar);
    }
}
