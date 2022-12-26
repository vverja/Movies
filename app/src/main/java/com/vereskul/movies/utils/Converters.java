package com.vereskul.movies.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static List<Integer> stringToList(String list){
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        return new Gson().fromJson(list, type);
    }
    @TypeConverter
    public static String listToString(List<Integer> list){
        return new Gson().toJson(list);
    }
}
