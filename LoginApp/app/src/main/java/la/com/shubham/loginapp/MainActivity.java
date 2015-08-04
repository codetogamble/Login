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

import com.bulsy.greenwall.MainActivity_gw;
import com.bulsy.wbtempest.MainActivity_wbta;
import com.bulsy.greenwall.PlayScreen;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.BufferedReader;
import java.io.FileReader;


public class MainActivity extends Activity implements  View.OnClickListener {


    Button btnLogout, btnProfile , btnGameGw ,btnGameWbta;
    UserLocalStore userLocalStore;
    Integer hiScore;
    private static final String HISCORE_FILE = "gwhs.dat";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);


        btnLogout = (Button) findViewById(R.id.button_logout);
        btnProfile = (Button) findViewById(R.id.button_profile);

        btnGameGw = (Button) findViewById(R.id.button_game_gw);
        btnGameWbta = (Button) findViewById(R.id.button_game_wbta);


        btnProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        btnGameGw.setOnClickListener(this);
        btnGameWbta.setOnClickListener(this);



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
            case R.id.button_game_gw:

                startActivity(new Intent(this, MainActivity_gw.class));
                break;
            case R.id.button_game_wbta:
                startActivity(new Intent(this, MainActivity_wbta.class));
                break;


        }
    }

//    public Integer ReadHighScoreGw(){
//        Integer hiscore=0;
//        try {
//            BufferedReader f = new BufferedReader(new FileReader(MainActivity.getFilesDir() + HISCORE_FILE));
//            hiscore = Integer.parseInt(f.readLine());
//            f.close();
//        } catch (Exception e) {
//            Log.d("Greenie", "ReadHiScore", e);
//        }
//        return(hiscore);
//    }

}
