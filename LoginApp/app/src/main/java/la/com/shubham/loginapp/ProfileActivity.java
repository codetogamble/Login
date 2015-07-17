package la.com.shubham.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class ProfileActivity extends Activity {

    UserLocalStore userLocalStore;
    EditText name, username ,age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.etname);
        username = (EditText) findViewById(R.id.etusername);
        age = (EditText) findViewById(R.id.etage);
        userLocalStore = new UserLocalStore(this);


    }
    @Override
    protected void onStart(){
        super.onStart();

        if(authenticate()){
            displayUserDetails();
        }
        else{
            startActivity(new Intent(ProfileActivity.this,Login.class));
        }


    }
    private Boolean authenticate()
    {
        return userLocalStore.getUserLoggedIn();
    }


    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        name.setText(user.getName());
        age.setText(user.getAge() + "");
        username.setText(user.getUsername());
    }

}
