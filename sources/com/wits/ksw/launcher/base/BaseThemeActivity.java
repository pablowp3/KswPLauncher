package com.wits.ksw.launcher.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.SkinAppCompatDelegateImpl;
import android.view.Window;
import android.view.WindowManager;
import com.wits.ksw.launcher.utils.ClientManager;
import com.wits.ksw.launcher.utils.UiThemeUtils;

public abstract class BaseThemeActivity extends AppCompatActivity {
    private static final String TAG = ("KswApplication." + BaseThemeActivity.class.getSimpleName());

    /* access modifiers changed from: protected */
    public abstract void initAlsId7UI();

    /* access modifiers changed from: protected */
    public abstract void initAlsId7UI_V2();

    /* access modifiers changed from: protected */
    public abstract void initAlsId7UiView();

    /* access modifiers changed from: protected */
    public abstract void initAlsView();

    /* access modifiers changed from: protected */
    public abstract void initAudiMbi3View();

    /* access modifiers changed from: protected */
    public abstract void initAudiMbi3ViewV2();

    /* access modifiers changed from: protected */
    public abstract void initAudiMib3Ty();

    /* access modifiers changed from: protected */
    public abstract void initAudiView();

    /* access modifiers changed from: protected */
    public abstract void initAudi_mib3_FyUiView();

    /* access modifiers changed from: protected */
    public abstract void initAudi_mib3_Fy_V2_UiView();

    /* access modifiers changed from: protected */
    public abstract void initBcUiView();

    /* access modifiers changed from: protected */
    public abstract void initBenzGSView();

    /* access modifiers changed from: protected */
    public abstract void initBenzMBUXView();

    /* access modifiers changed from: protected */
    public abstract void initBenzNTG5View();

    /* access modifiers changed from: protected */
    public abstract void initBenz_MBUX_2021_KSW_View();

    /* access modifiers changed from: protected */
    public abstract void initBenz_MBUX_2021_KSW_View_V2();

    /* access modifiers changed from: protected */
    public abstract void initBenz_MBUX_2021_View();

    /* access modifiers changed from: protected */
    public abstract void initBenz_MBUX_2021_View2();

    /* access modifiers changed from: protected */
    public abstract void initBenz_NTG6_FY_View();

    /* access modifiers changed from: protected */
    public abstract void initBmwEvoId6GS();

    /* access modifiers changed from: protected */
    public abstract void initBmwId8GsUiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwId8UiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwid5UiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwid6CuspUiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwid6UiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwid7UiView();

    /* access modifiers changed from: protected */
    public abstract void initBmwid7V2UiView();

    /* access modifiers changed from: protected */
    public abstract void initBwmID7Hicar();

    /* access modifiers changed from: protected */
    public abstract void initBwmNbt();

    /* access modifiers changed from: protected */
    public abstract void initCommonUIGSUG1024View();

    /* access modifiers changed from: protected */
    public abstract void initCommonUIGSUGView();

    /* access modifiers changed from: protected */
    public abstract void initCommonUIKSWMBUX1024View();

    /* access modifiers changed from: protected */
    public abstract void initGSUiView();

    /* access modifiers changed from: protected */
    public abstract void initLandRover();

    /* access modifiers changed from: protected */
    public abstract void initLexus();

    /* access modifiers changed from: protected */
    public abstract void initLexusLs();

    /* access modifiers changed from: protected */
    public abstract void initLexusLsDrag();

    /* access modifiers changed from: protected */
    public abstract void initLexusLsDragV2();

    /* access modifiers changed from: protected */
    public abstract void initRomeo();

    /* access modifiers changed from: protected */
    public abstract void initUIKSWID7View();

    /* access modifiers changed from: protected */
    public abstract void initUI_NTG6_FY_ViewV2();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (UiThemeUtils.isBenz_NTG6(this)) {
            initBcUiView();
        } else if (UiThemeUtils.isBMW_EVO_ID7(this)) {
            initBmwid7UiView();
        } else if (UiThemeUtils.isBMW_EVO_ID6(this)) {
            initBmwid6UiView();
        } else if (UiThemeUtils.isBMW_EVO_ID6_CUSP(this) && ClientManager.getInstance().isCUSP_210407()) {
            initBmwid6CuspUiView();
        } else if (UiThemeUtils.isBMW_EVO_ID6_GS(this)) {
            initBmwEvoId6GS();
        } else if (UiThemeUtils.isBMW_EVO_ID5(this)) {
            initBmwid5UiView();
        } else if (UiThemeUtils.isCommon_UI_GS(this)) {
            initGSUiView();
        } else if (UiThemeUtils.isCommon_UI_GS_UG(this)) {
            initCommonUIGSUGView();
        } else if (UiThemeUtils.isBenz_MBUX(this)) {
            initBenzMBUXView();
        } else if (UiThemeUtils.isBenz_MBUX_2021(this)) {
            initBenz_MBUX_2021_View2();
        } else if (UiThemeUtils.isBenz_MBUX_2021_KSW(this)) {
            initBenz_MBUX_2021_KSW_View();
        } else if (UiThemeUtils.isBenz_MBUX_2021_KSW_V2(this)) {
            initBenz_MBUX_2021_KSW_View_V2();
        } else if (UiThemeUtils.isBenz_NTG6_FY(this) && ClientManager.getInstance().isAls6208Client()) {
            initBenz_NTG6_FY_View();
        } else if (UiThemeUtils.isUI_NTG6_FY_V2(this) && ClientManager.getInstance().isAls6208Client()) {
            initUI_NTG6_FY_ViewV2();
        } else if (UiThemeUtils.isBenz_GS(this)) {
            initBenzGSView();
        } else if (UiThemeUtils.isAudi_MMI_4G(this)) {
            initAudiView();
        } else if (UiThemeUtils.isAudi_mib3(this)) {
            initAudiMbi3View();
        } else if (UiThemeUtils.isUI_mib3_V2(this)) {
            initAudiMbi3ViewV2();
        } else if (UiThemeUtils.isBenz_NTG5(this)) {
            initBenzNTG5View();
        } else if (UiThemeUtils.isALS_ID6(this)) {
            initAlsView();
        } else if (UiThemeUtils.isBMW_NBT(this)) {
            initBwmNbt();
        } else if (UiThemeUtils.isLEXUS_UI(this)) {
            initLexus();
        } else if (UiThemeUtils.isLEXUS_LS_UI(this)) {
            initLexusLsDrag();
        } else if (UiThemeUtils.isLEXUS_LS_UI_V2(this)) {
            initLexusLsDragV2();
        } else if (UiThemeUtils.isROMEO_UI(this)) {
            initRomeo();
        } else if (UiThemeUtils.isLAND_ROVER(this)) {
            setActivityFull();
            initLandRover();
        } else if (UiThemeUtils.isCommon_UI_GS_UG_1024(this)) {
            initCommonUIGSUG1024View();
        } else if (UiThemeUtils.isUI_KSW_MBUX_1024(this)) {
            initCommonUIKSWMBUX1024View();
        } else if (UiThemeUtils.isID7_ALS(this)) {
            if (ClientManager.getInstance().isAls6208Client()) {
                initAlsId7UI();
            } else {
                initBmwid7UiView();
            }
        } else if (UiThemeUtils.isID7_ALS_V2(this)) {
            if (ClientManager.getInstance().isAls6208Client()) {
                initAlsId7UI_V2();
            } else {
                initBmwid7UiView();
            }
        } else if (UiThemeUtils.isALS_ID7_UI(this)) {
            initAlsId7UiView();
        } else if (UiThemeUtils.isAudi_mib3_FY(this) && ClientManager.getInstance().isAls6208Client()) {
            initAudi_mib3_FyUiView();
        } else if (UiThemeUtils.isAudi_mib3_FY_V2(this) && ClientManager.getInstance().isAls6208Client()) {
            initAudi_mib3_Fy_V2_UiView();
        } else if (UiThemeUtils.isAudi_mib3_ty(this)) {
            initAudiMib3Ty();
        } else if (UiThemeUtils.isUI_KSW_ID7(this)) {
            initUIKSWID7View();
        } else if (UiThemeUtils.isBMW_ID8_UI(this)) {
            initBmwId8UiView();
        } else if (UiThemeUtils.isUI_GS_ID8(this)) {
            initBmwId8GsUiView();
        } else if (UiThemeUtils.isBMW_EVO_ID7_V2(this)) {
            initBmwid7V2UiView();
        } else {
            initBmwid7UiView();
        }
        super.onCreate(savedInstanceState);
    }

    public void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(0);
            window.getDecorView().setSystemUiVisibility(1280);
        }
    }

    public void setFull() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(67108864);
            getWindow().addFlags(134217728);
        }
    }

    public void setActivityFull() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    public void setFullActivity(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= 1024;
            getWindow().setAttributes(lp);
            getWindow().addFlags(512);
            return;
        }
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.flags &= -1025;
        getWindow().setAttributes(attr);
        getWindow().clearFlags(512);
    }

    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
