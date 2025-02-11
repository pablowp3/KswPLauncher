package com.wits.ksw.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.wits.ksw.R;
import com.wits.ksw.launcher.model.BcVieModel;

public abstract class AudiMib3FyMainLayoutBinding extends ViewDataBinding {
    public final AudiMib3FyMainLeftBinding include;
    public final LinearLayout includeLl;
    public final LinearLayout indicatorAudimib3;
    @Bindable
    protected BcVieModel mViewModel;
    public final ViewPager viewPageAudiMib3;

    public abstract void setViewModel(BcVieModel bcVieModel);

    protected AudiMib3FyMainLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount, AudiMib3FyMainLeftBinding include2, LinearLayout includeLl2, LinearLayout indicatorAudimib32, ViewPager viewPageAudiMib32) {
        super(_bindingComponent, _root, _localFieldCount);
        this.include = include2;
        this.includeLl = includeLl2;
        this.indicatorAudimib3 = indicatorAudimib32;
        this.viewPageAudiMib3 = viewPageAudiMib32;
    }

    public BcVieModel getViewModel() {
        return this.mViewModel;
    }

    public static AudiMib3FyMainLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudiMib3FyMainLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (AudiMib3FyMainLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.audi_mib3_fy_main_layout, root, attachToRoot, component);
    }

    public static AudiMib3FyMainLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudiMib3FyMainLayoutBinding inflate(LayoutInflater inflater, Object component) {
        return (AudiMib3FyMainLayoutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.audi_mib3_fy_main_layout, (ViewGroup) null, false, component);
    }

    public static AudiMib3FyMainLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudiMib3FyMainLayoutBinding bind(View view, Object component) {
        return (AudiMib3FyMainLayoutBinding) bind(component, view, R.layout.audi_mib3_fy_main_layout);
    }
}
