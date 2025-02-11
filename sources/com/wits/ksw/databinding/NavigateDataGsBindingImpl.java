package com.wits.ksw.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.View;
import com.wits.ksw.R;
import com.wits.ksw.generated.callback.OnClickListener;
import com.wits.ksw.launcher.model.LauncherViewModel;

public class NavigateDataGsBindingImpl extends NavigateDataGsBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback192;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_desc, 2);
    }

    public NavigateDataGsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private NavigateDataGsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, bindings[1], bindings[0], bindings[2]);
        this.mDirtyFlags = -1;
        this.ivMask.setTag((Object) null);
        this.llContainerGs.setTag((Object) null);
        setRootTag(root);
        this.mCallback192 = new OnClickListener(this, 1);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, Object variable) {
        if (15 != variableId) {
            return false;
        }
        setNavigateViewModel((LauncherViewModel) variable);
        return true;
    }

    public void setNavigateViewModel(LauncherViewModel NavigateViewModel) {
        this.mNavigateViewModel = NavigateViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(15);
        super.requestRebind();
    }

    /* access modifiers changed from: protected */
    public boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        LauncherViewModel launcherViewModel = this.mNavigateViewModel;
        if ((2 & dirtyFlags) != 0) {
            this.ivMask.setOnClickListener(this.mCallback192);
        }
    }

    public final void _internalCallbackOnClick(int sourceId, View callbackArg_0) {
        LauncherViewModel navigateViewModel = this.mNavigateViewModel;
        if (navigateViewModel != null) {
            navigateViewModel.openNaviApp(callbackArg_0);
        }
    }
}
