package com.fdu.swcontest.Util;

import com.fdu.swcontest.Main;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtils {
    /**
     * Import content from Json file
     * @param fileName relative path of the json file
     * @return
     */
    public static String getJson(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        String jsonString = "";
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    Main.class.getResourceAsStream("/assets/" + fileName), "utf-8"
            ));
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            jsonString = stringBuilder.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T jsonToObject(String json, Class<T> type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
