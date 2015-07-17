package la.com.shubham.loginapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity implements View.OnClickListener{

    EditText name,age,username,password,email;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.etname);
        age = (EditText)findViewById(R.id.etage);
        username = (EditText)findViewById(R.id.etusername);
        password = (EditText)findViewById(R.id.etpassword);
        email = (EditText)findViewById(R.id.etemail);

        register = (Button)findViewById(R.id.btnregister);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnregister:
                String name = this.name.getText().toString();
                String username = this.username.getText().toString();
                String password = this.password.getText().toString();
                Integer age = Integer.parseInt(this.age.getText().toString());
                String email = this.email.getText().toString();

                final User user = new User(name,age,username,password,email);

                ServerRequest serverRequest = new ServerRequest(Register.this);
                serverRequest.fetchUserCountInBackground(user, new GetUserCallBack() {

                    @Override
                    public void done(User returnedUser) {
                        if (returnedUser == null) {
                            registerUser(user);
                        } else {
                            showErrorMessage();
                        }
                    }
                });
                break;
        }
    }
    private void registerUser(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
        dialogBuilder.setMessage("Username exists");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
