package com.wits.ksw.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wits.ksw.R;
import com.wits.ksw.launcher.model.BcVieModel;
import com.wits.ksw.launcher.view.benzmbux.BenzMbuxItemView;
import com.wits.ksw.launcher.view.benzmbux2021ksw.bean.BenzMbux2021KswBean;

public abstract class BenzMbux2021KswItemBinding extends ViewDataBinding {
    public final BenzMbuxItemView benzMbux2021ImageView;
    public final TextView benzMbux2021TextView;
    public final TextView benzMbux2021Tip;
    @Bindable
    protected BenzMbux2021KswBean mListItem;
    @Bindable
    protected BcVieModel mVieModel;
    public final RelativeLayout rlContain;
    public final View space;

    public abstract void setListItem(BenzMbux2021KswBean benzMbux2021KswBean);

    public abstract void setVieModel(BcVieModel bcVieModel);

    protected BenzMbux2021KswItemBinding(Object _bindingComponent, View _root, int _localFieldCount, BenzMbuxItemView benzMbux2021ImageView2, TextView benzMbux2021TextView2, TextView benzMbux2021Tip2, RelativeLayout rlContain2, View space2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.benzMbux2021ImageView = benzMbux2021ImageView2;
        this.benzMbux2021TextView = benzMbux2021TextView2;
        this.benzMbux2021Tip = benzMbux2021Tip2;
        this.rlContain = rlContain2;
        this.space = space2;
    }

    public BenzMbux2021KswBean getListItem() {
        return this.mListItem;
    }

    public BcVieModel getVieModel() {
        return this.mVieModel;
    }

    public static BenzMbux2021KswItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BenzMbux2021KswItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (BenzMbux2021KswItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.benz_mbux_2021_ksw_item, root, attachToRoot, component);
    }

    public static BenzMbux2021KswItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BenzMbux2021KswItemBinding inflate(LayoutInflater inflater, Object component) {
        return (BenzMbux2021KswItemBinding) ViewDataBinding.inflateInternal(inflater, R.layout.benz_mbux_2021_ksw_item, (ViewGroup) null, false, component);
    }

    public static BenzMbux2021KswItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BenzMbux2021KswItemBinding bind(View view, Object component) {
        return (BenzMbux2021KswItemBinding) bind(component, view, R.layout.benz_mbux_2021_ksw_item);
    }
}
