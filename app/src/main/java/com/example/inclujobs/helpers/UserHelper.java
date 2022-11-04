package com.example.inclujobs.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inclujobs.R;
import com.example.inclujobs.entidades.Usuario;
import com.google.gson.Gson;

public class UserHelper {

    public static void saveUser(Usuario user, Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences("inclujobsPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString(ctx.getString(R.string.logged_user_key), userJson);
        editor.apply();
    }

    public static Usuario getUser(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences("inclujobsPreferences",Context.MODE_PRIVATE);
        String userString = sharedPref.getString(ctx.getString(R.string.logged_user_key), null);
        Gson gson = new Gson();
        Usuario user = gson.fromJson(userString, Usuario.class);
        return user;
    }

    public static void removeUser(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences("inclujobsPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(ctx.getString(R.string.logged_user_key));
        editor.apply();
    }
}
