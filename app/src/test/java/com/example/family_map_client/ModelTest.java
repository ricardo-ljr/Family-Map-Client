package com.example.family_map_client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Model.Person;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.EventsResult;
import Result.LoginResult;
import Result.PersonsResult;
import Result.RegisterResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ModelTest {

    DataCache data = DataCache.getInstance();

    private String host;
    private String port;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String compareTest;

    // Register User, Log in, retrieve people and events
    @Before
    public void setUp() {
        host = "localhost";
        port = "8000";

        userName = "sheila";
        password = "parker";
        firstName = "Sheila";
        lastName = "Parker";
        email = "sheila@email.com";
        gender = "f";

        ServerProxy proxy = new ServerProxy();

        try {
            URL url = new URL("http://" + host + ":" + port + "/user/register");

            RegisterRequest request = new RegisterRequest();
            request.setUserName(userName);
            request.setPassword(password);
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setEmail(email);
            request.setGender(gender);


            RegisterResult res = proxy.register(url, request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL("http://" + host + ":" + port + "/user/login");

            LoginRequest request = new LoginRequest();
            request.setUserName(userName);
            request.setPassword(password);

            LoginResult res = proxy.login(url, request);

            if (res.getMessage() == null) {
                String authtoken = res.getAuthToken();

                URL personUrl = new URL("http://" + host + ":" + port + "/person");

                PersonsResult personRes = proxy.persons(personUrl, authtoken);

                URL eventUrl = new URL("http://" + host + ":" + port + "/event");

                EventsResult eventsResult = proxy.events(eventUrl, authtoken);

                compareTest = res.getMessage();
            } else {
                compareTest = res.getMessage();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws IOException {
        ServerProxy proxy = new ServerProxy();
        proxy.clear(host, port);
        DataCache.getInstance().logout();
    }

    /********************************************** CALCULATE FAMILY RELATIONSHIPS **********************************************/

}
