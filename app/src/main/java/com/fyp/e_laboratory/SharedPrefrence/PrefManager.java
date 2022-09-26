package com.fyp.e_laboratory.SharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String IS_USER_LOGIN_EARLIER = "isUserLoginEarlier";
    private static final String IS_FIRST_TIME = "firstTime";
    private static final String PREF_NAME = "youchannle";
    private static final String User_Login_Type = "userlogin";

    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String Url_PIC = "pic";
    private static final String Token_Email = "token";
    private static final String UserID = "userid";
    private static final String TOTALCOINS = "totalcoins";
    private static final String WEBCOINS = "Webcoins";
    private static final String WEBTIME = "webtime";
    private static final String WEBVIDEOSEEN = "webviewseen";
    private static final String Servicevalue = "services";
    private static final String randomnumber = "randomnumber";


    private Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }



    public  void setUserName(String userName)
    {
        editor.putString(USER_NAME,userName);
        editor.apply();

    }
    public  String getUserName() {

        return sharedPreferences.getString(USER_NAME,"0");
    }
    public  void setUserEmail(String userEmail)
    {
        editor.putString(USER_EMAIL,userEmail);
        editor.apply();

    }
    public  String getUserEmail() {

        return sharedPreferences.getString(USER_EMAIL,"0");
    }

    public  void setUrl_PIC(String url_PIC)
    {
        editor.putString(Url_PIC,url_PIC);
        editor.apply();

    }
    public  String getUrl_PIC() {

        return sharedPreferences.getString(Url_PIC,"0");
    }
    public  void setToken_Email(String token_Email)
    {
        editor.putString(Token_Email,token_Email);
        editor.apply();

    }
    public  String getToken_Email() {

        return sharedPreferences.getString(Token_Email,"0");
    }
    public  void setUserID(String userID)
    {
        editor.putString(UserID,userID);
        editor.apply();

    }
    public  String getUserID() {

        return sharedPreferences.getString(UserID,"0");
    }
    public  void setTotalcoins(String totalcoins)
    {
        editor.putString(TOTALCOINS,totalcoins);
        editor.apply();

    }
    public  String getTotalcoins() {

        return sharedPreferences.getString(TOTALCOINS,"0");
    }
    public  void setWebcoins(String webcoins)
    {
        editor.putString(WEBCOINS,webcoins);
        editor.apply();

    }
    public  String getWebcoins() {

        return sharedPreferences.getString(WEBCOINS,"0");
    }
    public  void setWebtime(String webtime)
    {
        editor.putString(WEBTIME,webtime);
        editor.apply();

    }
    public  String getWebtime() {

        return sharedPreferences.getString(WEBTIME,"0");
    }
    public  void setWebvideoseen(String webvideoseen)
    {
        editor.putString(WEBVIDEOSEEN,webvideoseen);
        editor.apply();

    }
    public  String getWebvideoseen() {

        return sharedPreferences.getString(WEBVIDEOSEEN,"0");
    }
    public  void setServicevalue(String s)
    {
        editor.putString(Servicevalue,s);
        editor.apply();

    }
    public  String getServicevalue() {

        return sharedPreferences.getString(Servicevalue,"0");
    }
    public  void setRandomnumber(String randomnumber11)
    {
        editor.putString(randomnumber,randomnumber11);
        editor.apply();

    }
    public  String getRandomnumber() {

        return sharedPreferences.getString(randomnumber,"0");
    }
    public  void setUser_Login_Type(String login_type)
    {
        editor.putString(User_Login_Type,login_type);
        editor.apply();

    }
    public  String getUser_Login_Type() {

        return sharedPreferences.getString(User_Login_Type,"0");
    }
}

