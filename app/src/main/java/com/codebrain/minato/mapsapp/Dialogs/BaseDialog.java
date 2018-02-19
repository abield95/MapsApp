package com.codebrain.minato.mapsapp.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by username on 12/12/2017.
 */

public abstract class BaseDialog extends DialogFragment {
    DialogActivityListener dialogListener;
    private View view;
    private int layoutResID;

    public BaseDialog()
    {

    }

    @Override
    public void setArguments(Bundle bundle)
    {
        layoutResID = bundle.getInt("layoutResID");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(layoutResID, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    public View getView()
    {
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            dialogListener = (DialogActivityListener) context;
        }
        catch (ClassCastException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }

    /**
     * Used for compatibiliti for older versions
     * @param activity
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Code here
            try
            {
                dialogListener = (DialogActivityListener) activity;
            }
            catch (ClassCastException e)
            {
                Log.d("Exception", e.getMessage());
            }
        }
    }
}
