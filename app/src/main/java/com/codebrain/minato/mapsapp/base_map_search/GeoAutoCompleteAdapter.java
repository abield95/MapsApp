package com.codebrain.minato.mapsapp.base_map_search;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codebrain.minato.mapsapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by username on 12/25/2017.
 */

public class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable{
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List resultList = new ArrayList();

    public GeoAutoCompleteAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public int getCount()
    {
        return resultList.size();
    }

    @Override
    public GeoSearchResult getItem(int index)
    {
        return (GeoSearchResult) resultList.get(index);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.geo_search_result, parent, false);

            }

            ((TextView) convertView.findViewById(R.id.geo_search_result_text)).setText(getItem(position).getAddress());

        } catch (NullPointerException e) {
            Log.d("Exception: ", e.getMessage());
        }
        return null;
    }

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null)
                {
                    List locations = findLocations(mContext, constraint.toString());

                    //assing the data to the filter results
                    filterResults.values = locations;
                    filterResults.count = locations.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0)
                {
                    resultList = (List)results.values;
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<GeoSearchResult> findLocations(Context context, String query_text)
    {
        List<GeoSearchResult> geo_search_result = new ArrayList<GeoSearchResult>();

        Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
        List<Address> addresses = null;

        try
        {
            //get a maximun of 15 adresses that matches the input text
            addresses = geocoder.getFromLocationName(query_text, 15);

            for (int i=0; i<addresses.size(); i++)
            {
                Address address = (Address)addresses.get(i);
                if (address.getMaxAddressLineIndex() != -1)
                {
                    geo_search_result.add(new GeoSearchResult(address));
                }
            }

        }
        catch (IOException e)
        {
            Log.d("Exception: ", e.getMessage());
        }

        return geo_search_result;
    }

}
