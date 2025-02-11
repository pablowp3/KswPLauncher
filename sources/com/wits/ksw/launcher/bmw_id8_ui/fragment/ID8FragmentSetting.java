package com.wits.ksw.launcher.bmw_id8_ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wits.ksw.R;
import com.wits.ksw.launcher.bmw_id8_ui.ID8EditActivity;
import com.wits.ksw.launcher.bmw_id8_ui.listener.CardOnClickListener;
import com.wits.ksw.launcher.bmw_id8_ui.listener.CardOnDragListener;
import com.wits.ksw.launcher.bmw_id8_ui.listener.DragClickListener;

public class ID8FragmentSetting extends Fragment {
    private static final String TAG = "ID8FragmentSetting";
    public static final int iconResId = 2131233432;
    public static final String name = "SETTING";
    public static final int nameResId = 2131558780;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_edit, container, false);
        view.setOnDragListener(new CardOnDragListener(TAG, this, (ID8EditActivity) getActivity()));
        view.setOnLongClickListener(new DragClickListener(view, "SETTING", R.drawable.id8_main_left_icon_set, R.string.ksw_id8_abbr_setting));
        view.setOnClickListener(new CardOnClickListener((ID8EditActivity) getActivity()));
        view.setTag("SETTING");
        return view;
    }
}
