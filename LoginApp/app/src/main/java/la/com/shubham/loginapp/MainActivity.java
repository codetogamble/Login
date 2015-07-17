package la.com.shubham.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;


public class MainActivity extends Activity implements  View.OnClickListener {


    Button btnLogout, btnProfile;
    GoogleApiClient mGoogleApiClient;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);


        btnLogout = (Button) findViewById(R.id.button_logout);
        btnProfile = (Button) findViewById(R.id.button_profile);

        btnProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

      /*  mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        Log.d("LoginApp", "Connecting");
        this.mGoogleApiClient.connect();*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.button_logout:
               userLocalStore.setLogout(true);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
