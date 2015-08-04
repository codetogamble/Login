package la.com.shubham.loginapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.media.JetPlayer;
import android.os.AsyncTask;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shubham on 09-07-2015.
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://shubhamiitkgp10.site88.net/";

    public ServerRequest(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("processing");
        progressDialog.setMessage("Please wait...");

    }

    public void storeUserDataInBackground(User user, GetUserCallBack userCallBack) {

        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();

    }

    public void fetchUserDataInBackground(User user, GetUserCallBack userCallBack) {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallBack).execute();
    }
 public void fetchUserCountInBackground(User user, GetUserCallBack userCallBack) {
        progressDialog.show();
        new FetchUserCountAsyncTask(user, userCallBack).execute();
    }

    public void storeUserScoreData(GameData gameData, GetGameData getGameData){
        progressDialog.show();
        new StoreUserScoreDataAsyncTask(gameData , getGameData).execute();
    }

    public void fetchUserScoreData(GameData gameData, GetGameData getGameData){
        progressDialog.show();
        new FetchUserGameDataAsyncTask(gameData , getGameData).execute();
    }



    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallBack userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.getName()));
            dataToSend.add(new BasicNameValuePair("age", user.getAge().toString()));
            dataToSend.add(new BasicNameValuePair("username", user.getUsername()));
            dataToSend.add(new BasicNameValuePair("password", user.getPassword()));
            dataToSend.add(new BasicNameValuePair("email_id", user.getEmail()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);

            super.onPostExecute(aVoid);

        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallBack userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;


        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.getUsername()));
            dataToSend.add(new BasicNameValuePair("password", user.getPassword()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject JObject = new JSONObject(result);

                if (JObject.length() == 0) {
                    returnedUser = null;
                } else {
                    String name = JObject.getString("name");
                    int age = JObject.getInt("age");
                    int UserID = JObject.getInt("userID");
                    String username = JObject.getString("username");
                    String password = JObject.getString("password");
                    String email = JObject.getString("email");

                    returnedUser = new User(name, age, username, password, email);
                    returnedUser.setUserID(UserID);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);

            super.onPostExecute(returnedUser);

        }
    }

    public class FetchUserCountAsyncTask extends AsyncTask<Void, Void, User> {

        User user;
        GetUserCallBack userCallBack;

        public FetchUserCountAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;


        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.getUsername()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserCount.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject JObject = new JSONObject(result);

                if (JObject.length() == 0) {
                    returnedUser = null;
                } else {
                    String name = JObject.getString("name");
                    int age = JObject.getInt("age");
                    String username = JObject.getString("username");
                    String password = JObject.getString("password");
                    String email = JObject.getString("email");

                    returnedUser = new User(name, age, username, password, email);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);

            super.onPostExecute(returnedUser);

        }
    }

    public class StoreUserScoreDataAsyncTask extends  AsyncTask<Void,Void,Void>{

        GameData gameData;
        GetGameData getGameData;

        public StoreUserScoreDataAsyncTask(GameData gameData, GetGameData getGameData){
            this.gameData = gameData;
            this.getGameData = getGameData;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Username", gameData.getUsername()));
            dataToSend.add(new BasicNameValuePair("GameID", gameData.getGameID().toString()));
            dataToSend.add(new BasicNameValuePair("HighScore", gameData.getHighScore().toString()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "RegisterGameData.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            getGameData.done(null);

            super.onPostExecute(aVoid);

        }
    }

    public class FetchUserGameDataAsyncTask extends AsyncTask<Void , Void , GameData>{
        GameData gameData;
        GetGameData getGameData;

        public FetchUserGameDataAsyncTask(GameData gameData, GetGameData getGameData){
            this.gameData = gameData;
            this.getGameData = getGameData;
        }
        @Override
        protected GameData doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("Username", gameData.getUsername()));
            dataToSend.add(new BasicNameValuePair("GameID", gameData.getGameID().toString()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserGameData.php");

            GameData returnedGameData = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject JObject = new JSONObject(result);

                if (JObject.length() == 0) {
                    returnedGameData = null;
                } else {
                    String username = JObject.getString("Username");
                    int GameID = JObject.getInt("GameID");
                    int HighSCore = JObject.getInt("HighScore");


                    returnedGameData = new GameData(username, GameID, HighSCore);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedGameData;
        }

        protected void onPostExecute(GameData returnedGameData) {
            progressDialog.dismiss();
            getGameData.done(returnedGameData);

            super.onPostExecute(returnedGameData);

        }
    }
    }
