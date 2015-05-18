package com.slusarzparadowski.model.token;

import android.content.Context;
import android.util.Log;

import com.slusarzparadowski.database.ModelDataSourceMySQL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dominik on 2015-03-17.
 */
public class Token {

    private String token = "";

    public String getToken(){
        return this.token;
    }

    public Token(){}

    public void createToken() throws IOException {
        this.token = Long.toHexString(Double.doubleToLongBits(Math.random())) + Long.toHexString(Double.doubleToLongBits(Math.random()));
        Log.i("Token:createToken", "Token  "+ this.token + " created");
    }

}


