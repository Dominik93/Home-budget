package com.slusarzparadowski.database;

import android.util.Log;

import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Settings;
import com.slusarzparadowski.model.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2015-03-17.
 */
public class Database {

    private static final String TAG_VALUE = "response_value";
    private static final String TAG_MESSAGE = "response_message";

    private static String urlInsert = "http://slusarzparadowskiprojekt.esy.es/insert.php";
    private static String urlCheck = "http://slusarzparadowskiprojekt.esy.es/check.php";
    private static String urlGet = "http://slusarzparadowskiprojekt.esy.es/get.php";
    private static String urlUpdate = "http://slusarzparadowskiprojekt.esy.es/update.php";
    private static String urlDelete = "http://slusarzparadowskiprojekt.esy.es/delete.php";

    static JSONParser jsonParser = new JSONParser();

    static public String checkToken(String token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("check_token", token));

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(urlCheck, "POST", params);

        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);
            Log.d(String.valueOf(value), message);
            return message;
        } catch (JSONException e) {
            Log.e(Database.class.toString(), "checkToken "+ e.toString());
            return "JSONException";
        }
    }

    static public boolean insertToken(String token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("insert_token", token));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlInsert, "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);
            Log.d(String.valueOf(value), message);
            return true;
        } catch (JSONException e) {
            Log.e(Database.class.toString(), "insertToken" + e.toString());
            return false;
        }
    }

    static public ArrayList<Element> getElement(String token, int id){
        ArrayList<Element> returnList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("get_element", token));
        params.add(new BasicNameValuePair("get_category_id", String.valueOf(id)));

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(urlGet, "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);

            Log.d(String.valueOf(value), message);
            JSONObject listId = json.getJSONObject("response_array_id");
            JSONObject listIdCategory = json.getJSONObject("response_array_id_category");
            JSONObject listName = json.getJSONObject("response_array_name");
            JSONObject listValue = json.getJSONObject("response_array_element_value");
            JSONObject listConst = json.getJSONObject("response_array_const");
            JSONObject listDate = json.getJSONObject("response_array_date");
            if(listId.length() != listName.length()){
                return null;
            }
            for(int i = 0; i < listId.length(); i++){
                    Log.d(Database.class.toString(), "getElement Element(" + listId.getInt("id[" + i + "]") + "," + listName.getString("name[" + i + "]") + "," + listValue.getDouble("value[" + i + "]") + "," + listConst.getBoolean("const[" + i + "]") + "," + listDate.getString("date[" + i + "]") + ")");
                    returnList.add(new Element(listId.getInt("id[" + i + "]"),
                                                listIdCategory.getInt("idCategory[" + i + "]"),
                                                listName.getString("name[" + i + "]"),
                                                (float)listValue.getDouble("value[" + i + "]"),
                                                listConst.getBoolean("const[" + i + "]"),
                                                new LocalDate(listDate.getString("date[" + i + "]"))));
            }
            return returnList;
        } catch (JSONException e) {
            Log.e(Database.class.toString(), "getElement"+ e.toString());
            return returnList;
        }
    }

    public static ArrayList<Category> getList(String token, String type) {
        ArrayList<Category> returnList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("get_category", token));
        params.add(new BasicNameValuePair("get_category_type", type));
        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(urlGet, "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);
            JSONObject listId = json.getJSONObject("response_array_id");
            JSONObject listName = json.getJSONObject("response_array_name");
            if(listId.length() != listName.length()){
                return null;
            }
            for(int i = 0; i < listId.length(); i++){
                Log.d(Database.class.toString(), "getList Category("+listId.getInt("id["+i+"]")+", "+listName.getString("name["+i+"]")+", "+type+")");
                //returnList.add(new Category(listId.getInt("id["+i+"]"), listName.getString("name["+i+"]"), type));
            }
            for(Category el : returnList){
                el.setElementList(Database.getElement(token, el.getId()));
            }
            Log.d(String.valueOf(value), message);
            return returnList;
        } catch (JSONException e) {
            Log.e(Database.class.toString(), "getList"+ e.toString());
            return returnList;
        }
    }

    public static void deleteUser(String token) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("delete_user", token));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlDelete, "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);
            Log.d(String.valueOf(value), message);
        } catch (JSONException e) {
            Log.e(Database.class.toString(),"deleteUser"+ e.toString());
        }
    }

    public static User getUser(String token) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("get_user", token));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(urlGet, "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            int value = json.getInt(TAG_VALUE);
            String message = json.getString(TAG_MESSAGE);
            Log.d(String.valueOf(value), message);
            return new User(token, "name", (float)json.getDouble("response_savings"), new Settings(json.getBoolean("response_auto_save"), json.getBoolean("response_auto_delete")));
        } catch (JSONException e) {
            Log.e(Database.class.toString(), "getUser "+e.toString());
            return null ;
        }
    }
}
