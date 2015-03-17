package com.paradowski.slusarz.homebudget;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Dominik on 2015-03-17.
 */
public class Token {

    private String token = null;
    private final String FILENAME = "hb_config";
    private Context context;

    public String getToken(){
        return this.token;
    }

    public Token(Context context){
        this.context = context;
    }

    public void createToken() throws IOException {
        if(!loadToken()){
            this.token = Long.toHexString(Double.doubleToLongBits(Math.random()));
            saveToken();
        }
    }

    private void saveToken() throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(this.token.getBytes());
        fos.close();
    }

    private boolean loadToken() throws IOException {
        try {
            FileInputStream fin = context.openFileInput(FILENAME);
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            this.token = temp;
            fin.close();
            return true;
        }catch(FileNotFoundException e){
            return false;
        }
    }

}
