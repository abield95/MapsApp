package com.codebrain.minato.mapsapp.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by username on 12/12/2017.
 */

public class TravelDialog extends BaseDialog {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void onComplete()
    {
        Bundle bundle = new Bundle();
        super.dialogListener.onCompleteDialog(bundle);
    }
}
