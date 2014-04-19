package com.shaibarack.otherside;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReverseGeocode extends AsyncTask<LatLng, Void, String> {

    private final Context mContext;
    private final TextView mTextView;

    public ReverseGeocode(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
    }

    @Override
    protected String doInBackground(LatLng... latLngs) {
        if (!Geocoder.isPresent()) {
            return null;
        }
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        LatLng latLng = latLngs[0];
        try {
            final int maxResults = 1;
            List<Address> addresses =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResults);
            if (addresses.isEmpty()) {
                return null;
            }
            return addresses.get(0).getCountryName();
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if (result != null) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(result);
        } else {
            // Nothing to show
            mTextView.setVisibility(View.GONE);
        }
    }
}
