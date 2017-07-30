package org.jakubczyk.demo.flickrdemo.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by rudy on 30/07/2017.
 */

public class DataBinding {

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void imageLoader(ImageView imageView, String url, Drawable placeholder) {
        Picasso
                .with(imageView.getContext())
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }
}
