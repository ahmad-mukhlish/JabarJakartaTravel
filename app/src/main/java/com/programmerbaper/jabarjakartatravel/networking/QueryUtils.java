package com.programmerbaper.jabarjakartatravel.networking;

import android.text.TextUtils;
import android.util.Log;

import com.programmerbaper.jabarjakartatravel.entities.Trayek;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    static List<Trayek> fetchTrayek(String link) {

        URL url = parseStringLinkToURL(link);

        String jsonResponse = null;
        try {
            jsonResponse = httpConnectRequestJson(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when closing input stream", e);
        }

        return extractTrayek(jsonResponse);

    }

    public static String fetchResponse(String link) {

        URL url = parseStringLinkToURL(link);

        String jsonResponse = null;
        try {
            jsonResponse = httpConnectRequestJson(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error when closing input stream", e);
        }

        return jsonResponse;
    }


    private static String httpConnectRequestJson(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;


        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /*miliseconds*/);
            urlConnection.setConnectTimeout(150000 /*miliseconds*/);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = streamToSting(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while retrieving jsonResponse", e);
        } finally {
            //close the url and input stream.. regardless exception thrown or not..
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String streamToSting(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();

    }


    public static URL parseStringLinkToURL(String link) {

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url", e);
        }
        return url;

    }


    private static List<Trayek> extractTrayek(String jason) {


        if (TextUtils.isEmpty(jason)) {
            return null;
        }

        List<Trayek> listProduks = new ArrayList<>();

        try {

            JSONArray root = new JSONArray(jason);

            for (int i = 0; i < root.length(); i++) {

                JSONObject trayekNow = root.getJSONObject(i);
                int idTrayek = Integer.parseInt(trayekNow.getString("id_trayek"));
                String nama = trayekNow.getString("nama_trayek");
                int tarif = Integer.parseInt(trayekNow.getString("tarif"));

                Trayek currentProduk = new Trayek(idTrayek, nama, tarif);
                listProduks.add(currentProduk);
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results", e);
        }

        return listProduks;
    }


    public static String postWithHttp(URL url, String message) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;


        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000 /*miliseconds*/);
            urlConnection.setConnectTimeout(150000 /*miliseconds*/);

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(message);
            dataOutputStream.flush();
            dataOutputStream.close();

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = QueryUtils.streamToSting(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while retrieving jsonResponse", e);
        } finally {
            //close the url and input stream.. regardless exception thrown or not..
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }


}
