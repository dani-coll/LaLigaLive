package com.example.daniel.laligalive.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.daniel.laligalive.Constants;
import com.example.daniel.laligalive.R;
import com.example.daniel.laligalive.adapters.RecyclerAdapter;
import com.example.daniel.laligalive.services.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class ScrollingActivity extends AppCompatActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String LOG_TAG = "ScrollingActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                WebView myWebView = (WebView) findViewById(R.id.webview);
                if(myWebView != null) {
                    WebSettings webSettings = myWebView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    SharedPreferences sp = getSharedPreferences("Token", Context.MODE_PRIVATE);
                    String token = sp.getString("Token", "dBQ8AJSKo5Y:APA91bFBFkuIYa5YWMgqSgMj_tf0iVuTiYw0whXr6E6bJkY6C-braHjoXsDyONylVQXSe_Sf03D69f3pJY4DdUOHA-I53b10fX9p7gyRJFqjUtDPcgBnPt3WbDPv63_1L3IwVvOCm7qR");
                    myWebView.loadUrl("http://practicaad.byethost14.com/SetToken.php?Username=" + "Daniel" + "&Password=" + "1234" + "&Token=" + token);
                }
            }
        });

        int[] imatges = {
                R.drawable.ath,
                R.drawable.atl,
                R.drawable.bar,
                R.drawable.bet,
                R.drawable.cel,
                R.drawable.dep,
                R.drawable.eib,
                R.drawable.esp,
                R.drawable.get,
                R.drawable.gra,
                R.drawable.las,
                R.drawable.lev,
                R.drawable.mal,
                R.drawable.ray,
                R.drawable.rea,
                R.drawable.rso,
                R.drawable.sev,
                R.drawable.spo,
                R.drawable.val,
                R.drawable.vil

        };String[] equips = {
                "Athletic",
                "Atlético",
                "Barcelona",
                "Betis",
                "Celta",
                "Deportivo",
                "Éibar",
                "Espanyol",
                "Getafe",
                "Granada",
                "Las Palmas",
                "Levante",
                "Málaga",
                "Rayo",
                "R. Madrid",
                "R. Sociedad",
                "Sevilla",
                "Sporting",
                "Valéncia",
                "Villareal"

        };



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Toast.makeText(getApplicationContext(), "TOKEN OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "TOKEN ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);

        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmanager = new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanager);

        RecyclerAdapter myAdapter = new RecyclerAdapter(this, imatges,equips);
        recyclerView.setAdapter(myAdapter);

    }

    private boolean checkPlayServices() {
        Log.v(LOG_TAG,"Checking play services");
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
