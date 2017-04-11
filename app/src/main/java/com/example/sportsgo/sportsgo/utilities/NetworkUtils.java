/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sportsgo.sportsgo.utilities;

import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String BACKEND_BASE_URL =
            "http://sports-go.cloudapp.net";

    final static String PARAM_QUERY = "q";
    final static String ALL_FACILITY = "";
    final static String USER = "/user/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    /**
     * Builds the URL used to query backend.
     *
     * @param backendSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the backend.
     */
    public static URL buildUrl(String backendSearchQuery) {
        Uri builtUri = Uri.parse(BACKEND_BASE_URL).buildUpon()
                .appendPath(backendSearchQuery)
                //.appendQueryParameter(PARAM_QUERY, backendSearchQuery)
                //.appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String buildLoginUrl(String username, String password){
        return  BACKEND_BASE_URL + USER + "?user_name=" + username + "&password=" + password;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param murl The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(String murl) throws IOException {
        URL url = new URL(murl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static String getAllFacilities()  {
        URL url = buildUrl(ALL_FACILITY);
        String result = null;
        try{
            result = getResponseFromHttpUrl(BACKEND_BASE_URL+ALL_FACILITY);
        }
        catch(IOException e){
            Log.d("getAllFacilities", "IOException");
        }
        return result;
    }

    public static String GetLogin(String username, String password, AsyncHttpResponseHandler handler){
        String url = BACKEND_BASE_URL + USER + "?user_name=" + username + "&password=" + password;
        String result = null;
        RequestParams params = new RequestParams();
        params.put("user_name", username);
        params.put("password", password);
        client.get(BACKEND_BASE_URL + USER, params, handler);
        /*
        try{
            result = getResponseFromHttpUrl(url);
        }
        catch(IOException e){
            Log.d("getAllFacilities", "IOException");
        }
        return result;
        */
        return "";
    }

    public static void editFavorite(int user_id, int facility_id, boolean add) throws IOException {
        URL url = buildUrl("/favorites");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("user", ""+user_id)
                .appendQueryParameter("facility", ""+facility_id);
        if(add) builder.appendQueryParameter("like", "1");
        else builder.appendQueryParameter("like","0");
        String query = builder.build().getEncodedQuery();

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();

        conn.connect();
        //try {
        //    getResponseFromHttpUrl(url);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //AsyncHttpClient client = new AsyncHttpClient();
        //client.post(getAbsoluteUrl(url), params, responseHandler);
    }

}