package la.com.shubham.loginapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;
import java.util.List;


public class Login extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    Button bLogin;
    EditText etUsername;
    EditText etPassword;
    TextView register;
    UserLocalStore userLocalStore;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private boolean mSignInClicked;
    private boolean mIntentInProgress;
    private ConnectionResult mConnectionResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);
        bLogin = (Button) findViewById(R.id.btnlogin);
        register = (TextView) findViewById(R.id.tvRegister);

        bLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
                this.mGoogleApiClient.connect();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin:

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(username, password);

                authenticate(user);

                break;
            case R.id.btn_sign_in:
// Signin button clicked
                signInWithGplus();

                break;
            case R.id.tvRegister:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
    protected void onStart() {
        super.onStart();



    }
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private static final int GOOGLE_SIGIN = 100;
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, GOOGLE_SIGIN);
            }
            catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == GOOGLE_SIGIN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }



    private void authenticate(User user){

       ServerRequest serverRequest = new ServerRequest(Login.this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null){
                    showErrorMessage();
                }
                else{
                    logUserIn(returnedUser);
                }
            }
        });

    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect User Details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
    private void logUserIn(User returnedUser){
        userLocalStore.saveUser(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onConnected(Bundle bundle) {

        getProfileInformation();

        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
// Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;
            if (mSignInClicked) {
// The user has already clicked 'sign-in' so we attempt to
// resolve all
// errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                //String username = removeSpaces(personName);
                String username = "*" + email;

                final User user = new User(personName, -1, username, " ", email);

                ServerRequest serverRequest = new ServerRequest(Login.this);
                serverRequest.fetchUserDataInBackground(user, new GetUserCallBack() {

                    @Override
                    public void done(User returnedUser) {
                        if (returnedUser == null) {
                            registerUser(user);
                        }
                    }
                });

                logUserIn(user);
                signOutFromGplus();


            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void registerUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {

            }
        });
    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }
    /*private String removeSpaces(String input){
        String[] nameList = nameList = input.split(" ");
        String sanitizedString = "";
        if(nameList.length > 1) {
            for(String name:nameList){
                sanitizedString = sanitizedString.concat(name);
            }
        }
        return sanitizedString;
    }*/
}
