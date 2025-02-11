package com.wits.ksw.launcher.base;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.wits.ksw.R;
import com.wits.ksw.launcher.bmw_id8_ui.view.ID8ProgressBar;
import com.wits.ksw.launcher.view.DragGridView;
import java.util.Locale;
import skin.support.content.res.SkinCompatResources;

public class BaseBindingModel {
    public static void srcImage(ImageView imageView, Drawable resid) {
        imageView.setImageDrawable(resid);
    }

    public static void srcImage(ImageView imageView, int resid) {
        imageView.setImageResource(resid);
    }

    public static void setAdpater(GridView gridView, BaseListAdpater adapter) {
        gridView.setAdapter(adapter);
    }

    public static void setID6MusicAlbumIcon(ImageView imageView, BitmapDrawable data) {
        ((RequestBuilder) ((RequestBuilder) Glide.with(imageView.getContext()).load((Drawable) data).placeholder((int) R.drawable.id6_music_album_default)).error((int) R.drawable.id6_music_album_default)).into(imageView);
    }

    public static void setMusicAlbumIcon(ImageView imageView, BitmapDrawable data) {
        imageView.setAlpha(data != null ? 0.2f : 1.0f);
        ((RequestBuilder) ((RequestBuilder) Glide.with(imageView.getContext()).load((Drawable) data).placeholder((int) R.drawable.id7_album_bk_big)).error((int) R.drawable.id7_album_bk_big)).into(imageView);
    }

    public static void setAlbumIcon(ImageView imageView, BitmapDrawable data) {
        ((RequestBuilder) ((RequestBuilder) Glide.with(imageView.getContext()).load((Drawable) data).placeholder((int) R.drawable.id7_album_default)).error((int) R.drawable.id7_album_default)).into(imageView);
    }

    public static void setAlsID7AlbumIcon(ImageView imageView, BitmapDrawable data) {
        ((RequestBuilder) ((RequestBuilder) Glide.with(imageView.getContext()).load((Drawable) data).placeholder((int) R.color.transparent)).error((int) R.color.transparent)).into(imageView);
    }

    public static void setID8AlbumIcon(ImageView imageView, BitmapDrawable data) {
        ((RequestBuilder) ((RequestBuilder) Glide.with(imageView.getContext()).load((Drawable) data).placeholder(SkinCompatResources.getDrawable(imageView.getContext(), R.drawable.id8_main_icon_music_album))).error(SkinCompatResources.getDrawable(imageView.getContext(), R.drawable.id8_main_icon_music_album))).into(imageView);
    }

    public static void setOnItemChangerListener(DragGridView gridView, DragGridView.onItemChangerListener onItemChangerListener) {
        gridView.setOnItemChangeListener(onItemChangerListener);
    }

    public static void setTurnSpeedStr(TextView textView, int speed) {
        textView.setText(String.format(Locale.ENGLISH, "%.1f", new Object[]{Double.valueOf(((double) speed) * 0.001d)}));
    }

    public static void setID8ProgressBarMax(ID8ProgressBar id8ProgressBar, int max) {
        id8ProgressBar.setMax(max);
    }

    public static void setID8ProgressBarValue(ID8ProgressBar id8ProgressBar, int value) {
        id8ProgressBar.setValue(value);
    }
}
