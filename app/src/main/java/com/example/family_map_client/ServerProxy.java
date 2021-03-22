package com.example.family_map_client;

import android.util.Log;

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

    public ServerProxy (String serverHostName, int serverPortNumber) {
        this.serverHostName = serverHostName;
        this.serverPortNumber = serverPortNumber;
    }

    public LoginResult login(LoginRequest request) {
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object
        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/login");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true); // there is a request body

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            // Serializing request as JSON string
            String jsonString = Serializer.serialize(request);
            OutputStream out = http.getOutputStream();
            ReadWrite.writeString(jsonString, out);
            out.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream reqBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(reqBody);

                LoginResult loginResult = Deserializer.deserialize(reqData, LoginResult.class);

                http.getInputStream().close();

                return loginResult;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginResult loginResult = new LoginResult();

        loginResult.setSuccess(false);

        loginResult.setMessage("Error in ServerProxy when logging in");

        return loginResult;
    }

    public RegisterResult register(RegisterRequest request){
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object
        try {
            URL url = new URL("http://" + serverHostName + ":" + serverPortNumber + "/user/register");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true); // there is a request body

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            // Serializing request as JSON string
            String jsonString = Serializer.serialize(request);
            OutputStream out = http.getOutputStream();
            ReadWrite.writeString(jsonString, out);
            out.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream reqBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(reqBody);

                RegisterResult registerResult = Deserializer.deserialize(reqData, RegisterResult.class);

                http.getInputStream().close();

                return registerResult;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        RegisterResult registerResult = new RegisterResult();

        registerResult.setSuccess(false);

        registerResult.setMessage("Error in ServerProxy when registering user");

        return registerResult;

    }

    // GetAllPeople and user's family tree
    //GetAllEvents in the family tree
}
