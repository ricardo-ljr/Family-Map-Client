package com.example.family_map_client;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import JSONReader.Deserializer;
import JSONReader.ReadWrite;
import JSONReader.Serializer;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.EventsResult;
import Result.LoginResult;
import Result.PersonsResult;
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
        DataCache data = DataCache.getInstance();

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

                data.setLoggedIn(true); // keeps track of user being logged in

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

    public PersonsResult persons(URL url, String authtoken){
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object

        PersonsResult personsResult = new PersonsResult();

        try {

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false); // there is no request body
            http.addRequestProperty("Accept", "application/json");
            http.addRequestProperty("Authorization", authtoken);

            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream resBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(resBody);

                personsResult = Deserializer.deserialize(reqData, PersonsResult.class);

                http.getInputStream().close();

                return personsResult;
            } else {
                System.out.println("Error in persons proxy");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        personsResult.setSuccess(false);

        personsResult.setMessage("Error in ServerProxy when retrieving user's person");

        return personsResult;

    }

    //GetAllEvents in the family tree

    public EventsResult events(URL url, String authToken) {
        // Serialize request as JSON string
        // Make HTTP request to server in order to call the web api
        // Deserialize response body to LoginResult object

        EventsResult eventsResult = new EventsResult();

        try {

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false); // there is no request body
            http.addRequestProperty("Accept", "application/json");
            http.addRequestProperty("Authorization", authToken);

            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream resBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(resBody);

                eventsResult = Deserializer.deserialize(reqData, EventsResult.class);

                http.getInputStream().close();

                return eventsResult;
            } else {
                System.out.println("Error in events proxy");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        eventsResult.setSuccess(false);
        eventsResult.setMessage("Error in ServerProxy when retrieving user's events");
        return eventsResult;
    }

    /**
     * Clear function to clear database when testing
     *
     * @return
     */
    public ClearResult clear(String host, String port) throws IOException {

        ClearResult clearResult = new ClearResult();

        try {

            URL url = new URL("http://" + host + ":" + port +"/clear");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false); // there is no request body
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream resBody = http.getInputStream(); // parse JSON data out of the response body

                String reqData = ReadWrite.readString(resBody);

                clearResult = Deserializer.deserialize(reqData, ClearResult.class);

                http.getInputStream().close();

                return clearResult;
            } else {
                System.out.println("Error in clear proxy");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        clearResult.setSuccess(false);
        clearResult.setMessage("Error in ServerProxy when clearing");
        return clearResult;
    }
}

