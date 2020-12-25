package com.fdu.swcontest.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fdu.swcontest.Hooks.AbstractHook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String POST_URL = "http://42.192.128.239:5000/rjds/api/v0.1";
    public static final String GET_BASE_URL = "http://42.192.128.239:5000/rjds/api/v0.1/";
    public static final String TAG = "NetUils";

    //pkName=="" post全部序列，否则post该指定app的序列
    public static boolean postJson(Context context, String pkName) throws JSONException {

        String deviceID = getUniquePsuedoID();
        SWQuery swq = new SWQuery(context);

//        String seq = getHash(pkName);
//        String seq = pkName;
        String arr;
        arr = swq.getSequence(pkName);
        String seq = getHash(arr);
        String[] arr_splited = arr.split(AbstractHook.Splitter);
        StringBuilder sbarr_encoded = new StringBuilder();
        for (String s : arr_splited) {
            sbarr_encoded.append(string2HexString(s));
        }
        String arr_encoded = sbarr_encoded.toString();
        String len_arr = Integer.toString(arr_encoded.length());
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", deviceID);
            jsonObj.put("seq", seq);
            jsonObj.put("length", len_arr);
            jsonObj.put("arr", arr);
            jsonObj.put("pkName", pkName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建OkhttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObj));
        Request request = new Request.Builder().url(POST_URL).post(requestBody).build();

        //异步
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.i("info",resp+"");
                try {
                    JSONObject json = new JSONObject(resp);
                    String err = json.optString("error");
                    if(!err.equals("")) {
                        Log.i(TAG, err);
                    }
                    if(!json.optString("length").equals(len_arr)) {
                        Log.i(TAG, "Check failed!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    public static boolean postJson(String pkName, String arr) throws JSONException {

        String deviceID = getUniquePsuedoID();
        String seq = getHash(arr);      //原序列做hash作为序号
        String[] arr_splited = arr.split(AbstractHook.Splitter);
        StringBuilder sbarr_encoded = new StringBuilder();
        for (String s : arr_splited) {
            sbarr_encoded.append(string2HexString(s));
        }
        String arr_encoded = sbarr_encoded.toString();
        String len_arr = Integer.toString(arr_encoded.length());
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", deviceID);
            jsonObj.put("seq", seq);
            jsonObj.put("length", len_arr);
            jsonObj.put("arr", arr);
            jsonObj.put("pkName", pkName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建OkhttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObj));
        Request request = new Request.Builder().url(POST_URL).post(requestBody).build();

        //异步
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.i("info",resp+"");
                try {
                    JSONObject json = new JSONObject(resp);
                    String err = json.optString("error");
                    if(!err.equals("")) {
                        Log.i(TAG, err);
                    }
                    if(!json.optString("length").equals(len_arr)) {
                        Log.i(TAG, "Check failed!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }



    public static String getJson(Context context, String seq) {
        String deviceID = getUniquePsuedoID();
        String geturl = GET_BASE_URL + deviceID + "_" + seq;
        final String[] resp = new String[1];

        SWQuery swq = new SWQuery(context);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(geturl).build();
        //enqueue就是将此次的call请求加入异步请求队列，会开启新的线程执行，并将执行的结果通过Callback接口回调的形式返回。
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败的回调方法
                Log.i(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功的回调方法
                resp[0] = response.body().string();
                try {
                    JSONObject resp_json = new JSONObject(new String(resp[0]));
                    String pkname = resp_json.optString("pkName");
                    String finished = resp_json.optString("finished");
                    String isMal = resp_json.optString("is_malware");
                    swq.setMal(pkname, "True".equals(isMal));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, resp[0]);
                //关闭body
                response.body().close();
            }
        });
        return resp[0];
    }

    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    //将数字字符串转换为三位十六进制数字字符串
    public static String string2HexString(String str)
    {
        int d = Integer.parseInt(str);
        String strHex = Integer.toHexString(d);
        if(strHex.length() == 3)
            return strHex;
        if(strHex.length() == 2)
            return "0" + strHex;
        if(strHex.length() == 1)
            return "00" + strHex;
        //超过三位十六进制表示范围
        Log.e(TAG, "Encoding error!");
        return "";
    }

    //根据app包名生成哈希值作为seq，以分辨app
    public static String getHash(String pkName)
    {
        String tmpKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(pkName.getBytes());
            tmpKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            tmpKey = String.valueOf(pkName.hashCode());
        }
        return tmpKey;
    }

    private static String bytesToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            String hex = Integer.toHexString(0xFF & value);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}


