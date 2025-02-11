package com.wits.ksw.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wits.ksw.R;
import com.wits.ksw.launcher.model.LauncherViewModel;
import com.wits.ksw.launcher.view.CustomBmwImageView;

public abstract class Id7SubHicarViewBinding extends ViewDataBinding {
    public final TextView dayTextView;
    @Bindable
    protected LauncherViewModel mNaviViewModel;
    public final TextView monthTextView;
    public final ConstraintLayout phoneConstraintLayout;
    public final CustomBmwImageView phoneImageView;
    public final TextView textView2;

    public abstract void setNaviViewModel(LauncherViewModel launcherViewModel);

    protected Id7SubHicarViewBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView dayTextView2, TextView monthTextView2, ConstraintLayout phoneConstraintLayout2, CustomBmwImageView phoneImageView2, TextView textView22) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dayTextView = dayTextView2;
        this.monthTextView = monthTextView2;
        this.phoneConstraintLayout = phoneConstraintLayout2;
        this.phoneImageView = phoneImageView2;
        this.textView2 = textView22;
    }

    public LauncherViewModel getNaviViewModel() {
        return this.mNaviViewModel;
    }

    public static Id7SubHicarViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Id7SubHicarViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (Id7SubHicarViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.id7_sub_hicar_view, root, attachToRoot, component);
    }

    public static Id7SubHicarViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Id7SubHicarViewBinding inflate(LayoutInflater inflater, Object component) {
        return (Id7SubHicarViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.id7_sub_hicar_view, (ViewGroup) null, false, component);
    }

    public static Id7SubHicarViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Id7SubHicarViewBinding bind(View view, Object component) {
        return (Id7SubHicarViewBinding) bind(component, view, R.layout.id7_sub_hicar_view);
    }
}
