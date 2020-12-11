package com.example.neostore2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.neostore2.R;

public class AdapterViewPager extends PagerAdapter {
    private Context mContext;
    private int[] imgid = new int[] {R.drawable.slider_sofa, R.drawable.slider_table,R.drawable.slider_bed,R.drawable.slider_chair};

    AdapterViewPager(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return imgid.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imgid[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
