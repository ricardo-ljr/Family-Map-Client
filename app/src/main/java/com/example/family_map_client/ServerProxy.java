package com.example.family_map_client;

import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import JSONReader.Deserializer;
import JSONReader.ReadWrite;
import JSONReader.Serializer;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;

/**
 * This class takes care of the HTTP requests
 */
public class ServerProxy {

    public static String serverHostName;
    public static int serverPortNumber;

    public ServerProxy() {

    }

    public LoginResult login(URL url, LoginRequest request) {
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object
        LoginResult loginResult = new LoginResult();
        try {

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true); // there is a request body

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            // Serializing request as JSON string
            String jsonString = Serializer.serialize(request);

            OutputStream reqBody = http.getOutputStream();
            ReadWrite.writeString(jsonString, reqBody);

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream resBody = http.getInputStream(); // parse JSON data out of the response body

                String resData = ReadWrite.readString(resBody);

                loginResult = Deserializer.deserialize(resData, LoginResult.class);

                http.getInputStream().close();

                return loginResult;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        loginResult.setSuccess(false);

        loginResult.setMessage("Error in ServerProxy when logging in");

        return loginResult;
    }

    public RegisterResult register(URL url, RegisterRequest request){
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object

        RegisterResult registerResult = new RegisterResult();

        try {

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true); // there is a request body

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            // Serializing request as JSON string
            // String jsonString = Serializer.serialize(request);

            String jsonString = Serializer.serialize(request);

            OutputStream reqBody = http.getOutputStream();

            ReadWrite.writeString(jsonString, reqBody);

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream resBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(resBody);

                registerResult = Deserializer.deserialize(reqData, RegisterResult.class);

                http.getInputStream().close();

                return registerResult;
            } else {
                System.out.println("Error in register proxy");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        registerResult.setSuccess(false);

        registerResult.setMessage("Error in ServerProxy when registering user");

        return registerResult;

    }

    // GetAllPeople and user's family tree
    //GetAllEvents in the family tree
}
