package com.codebrain.minato.mapsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView singUp;
    private AppCompatButton btLogin;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.personal_toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }

        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        singUp = (TextView)findViewById(R.id.login_no_account);
        btLogin = (AppCompatButton)findViewById(R.id.login_button);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

    private void userLogin()
    {
        if (!validate())
        {
            onLoginFailed();
            return;
        }

        CheckLogin checkLogin = new CheckLogin();
        checkLogin.execute(this.username.getText().toString(), this.password.getText().toString());
    }

    private boolean validate()
    {
        boolean valid = true;

        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        if (username.isEmpty())
        {
            this.username.setError(getResources().getString(R.string.empty_username));
            valid = false;
        }
        else
        {
            this.username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20)
        {
            this.password.setError(getResources().getString(R.string.password_incorrect));
            valid = false;
        }
        else
        {
            this.password.setError(null);
        }

        return valid;
    }

    private void onLoginFailed()
    {

    }

    protected class CheckLogin extends AsyncTask<String, String, String>
    {
        private boolean networkAccess = false;
        private boolean loged = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.authenticating) + "...";
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try
            {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    networkAccess = true;
                    loged = false;
                    return "Failed To Login";
                }
            }
            catch (Exception e)
            {
                Log.d("Exception: ", e.getMessage());
                return "Failed to login";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (!networkAccess)
            {
                //no network access
                Toast.makeText(getApplicationContext(), "No network access", Toast.LENGTH_LONG).show();
            }
            else if (!loged)
            {
                Toast.makeText(getApplicationContext(), "Failde login", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Loged", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = getSharedPreferences("MyPref", Context.MODE_PRIVATE).edit();
                editor.putBoolean("logged", true);
                editor.putString("username", username.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        }
    }
}

