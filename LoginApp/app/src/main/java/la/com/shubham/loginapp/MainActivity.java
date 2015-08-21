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
import com.google.android.gms.games.Game;
import com.google.android.gms.plus.Plus;

import java.io.BufferedReader;
import java.io.FileReader;


public class MainActivity extends Activity implements  View.OnClickListener {


    Button btnLogout, btnProfile , btnGameGw ,btnGameWbta;
    UserLocalStore userLocalStore;
    User user;
    Integer hiScore_gw,hiScore_wbta;
    static final int REQ_CODE_GW= 153;
    static final int REQ_CODE_WBTA= 154;
    private static final String HISCORE_FILE = "gwhs.dat";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

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
                Intent intent = new Intent(MainActivity.this, MainActivity_gw.class);
                startActivityForResult(intent, REQ_CODE_GW);
                break;
            case R.id.button_game_wbta:
                Intent intent1 = new Intent(MainActivity.this, MainActivity_wbta.class);
                startActivityForResult(intent1, REQ_CODE_WBTA);
                break;


        }
    }
    public void saveScoreData(GameData gameData, GetGameData getGameData){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserScoreData(gameData, getGameData);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode){
        case (REQ_CODE_GW):
            if(resultCode ==1) {
                hiScore_gw = new Integer(data.getIntExtra("Hi_Score", 0));
                Log.d("HiScore", hiScore_gw.toString());
                final GameData gameData = new GameData(user.getUsername(), 1, hiScore_gw);
                saveScoreData(gameData, new GetGameData() {
                    @Override
                    public void done(GameData returnedGameData) {

                                            }
                });
            }
    }
    }
}
