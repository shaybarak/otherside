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
    private final boolean mIncludeAddress;

    public ReverseGeocode(Context context, TextView textView, boolean includeAddress) {
        mContext = context;
        mTextView = textView;
        mIncludeAddress = includeAddress;
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
            Address address = addresses.get(0);
            if (mIncludeAddress) {
                String addressStr = "";
                if (address.getAddressLine(0) != null) {
                    addressStr += address.getAddressLine(0) + "\n";
                }
                if (address.getAdminArea() != null) {
                    addressStr += address.getAdminArea() + ", ";
                }
                return addressStr + address.getCountryName();
            } else {
                return address.getCountryName();
            }
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
