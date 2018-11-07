package com.example.rafao.proyectomoviles;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        ModelLayout fragment = ModelLayout.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewGroup layout = (ViewGroup) inflater.inflate(fragment.getLayoutId(), collection, false);

        collection.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return ModelLayout.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ModelLayout customPagerEnum = ModelLayout.values()[position];

        return mContext.getString(customPagerEnum.getTitleId());
    }

    public enum ModelLayout {

        LOGIN(R.layout.loginuv),
        REGISTER(R.layout.registro);

        private int mTitleId;
        private int mLayoutId;

        ModelLayout(int layoutResId) {
            mLayoutId = layoutResId;
        }

        public int getTitleId() {
            return mTitleId;
        }

        public int getLayoutId() {
            return mLayoutId;
        }

    }

}
