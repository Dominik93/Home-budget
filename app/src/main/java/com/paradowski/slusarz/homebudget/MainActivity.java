package com.paradowski.slusarz.homebudget;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ActionBarActivity {

    TextView tv;
    ProgressDialog pDialog;
    Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tokenTextView);
        token = new Token(getApplicationContext());
        try {
            String s = new CheckToken().execute().get();
        } catch (InterruptedException e) {
            Log.i("InterruptedException", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.i("ExecutionException", e.getMessage());
            e.printStackTrace();
        }
    }


    public void buttonClick(View view) throws IOException {
        tv.setText("create token clicked v3");
    }

    public void buttonClickSave(View view) {
        tv.setText("save token clicked");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    class CheckToken extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Checking token..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            try {
                if(!token.loadToken()){
                    while(true){
                        token.createToken();
                        if(Database.checkToken(token.getToken()).equals("NOT_EXIST")){
                            token.saveToken();
                            if(Database.insertToken(token.getToken()).equals("")){
                                Log.e("Error ", "Token insert error");
                            }
                            Log.d("Token created ", token.getToken());
                            return "Token created" + token.getToken();
                        }
                    }
                }
                Log.d("Token loaded ", token.getToken());
                return "Token loaded" + token.getToken();
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
                return "IOException" + e.getMessage();
            }

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }


}
