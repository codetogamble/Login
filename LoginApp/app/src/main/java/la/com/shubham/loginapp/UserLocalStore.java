package la.com.shubham.loginapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shubham on 09-07-2015.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";

    //User user;
    SharedPreferences userStore;

    public UserLocalStore(Context context){
        userStore = context.getSharedPreferences(SP_NAME,0);
    }


    public void saveUser(User user){

        SharedPreferences.Editor spEditor = userStore.edit();
        spEditor.putString("name", user.getName());
        spEditor.putInt("age", user.getAge());
        spEditor.putInt("UserID", user.getUserID());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("email_id", user.getEmail());
        spEditor.commit();
    }

    public void saveGameData(GameData gameData){
        SharedPreferences.Editor spEditor = userStore.edit();
        spEditor.putInt("HighScore" , gameData.getHighScore());
        spEditor.putInt("GameID" , gameData.getGameID());
        spEditor.apply();
    }


    public User getLoggedInUser() {

            String name = userStore.getString("name", "");
            String username = userStore.getString("username", "");
            String password = userStore.getString("password", "");
            String email = userStore.getString("email_id", "");
            Integer age = userStore.getInt("age", -1);
            Integer UserID = userStore.getInt("UserID", -1);

            User user = new User(name, age, username, password,email);
            user.setUserID(UserID);
            return user;

    }

    public void setUserLoggedIn(Boolean loggedIn){
        SharedPreferences.Editor spEditor = userStore.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }
    public Boolean getUserLoggedIn(){
        return userStore.getBoolean("loggedIn",false);
    }
    public void clearUserStore(){
        SharedPreferences.Editor spEditor = userStore.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public  void  setLogout(boolean logout){
        SharedPreferences.Editor spEditor = userStore.edit();
        spEditor.putBoolean("logout", logout);
        spEditor.commit();
    }

    public Boolean getLogout(){
        return  userStore.getBoolean("logout", false);
    }
}
