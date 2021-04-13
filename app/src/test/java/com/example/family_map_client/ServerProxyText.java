package com.example.family_map_client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import AsyncTasks.ClearTask;
import Request.RegisterRequest;
import Result.RegisterResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ServerProxyText {

//    Write JUnit tests to verify that your Server Proxy class works correctly. You should have tests for the following functionality:
//    Login method
//    Registering a new user
//    Retrieving people related to a logged in/registered user
//    Retrieving events related to a logged in/registered user

    private String host;
    private String port;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String compareTest;

    @Before
    public void setUp() {
        host = "localhost";
        port = "8000";
    }

    @After
    public void tearDown() throws IOException {
        ServerProxy proxy = new ServerProxy();
        proxy.clear(host, port);
        DataCache.getInstance().logout();

    }

    /***************************************************** REGISTER TESTS *****************************************************/

    @Test
    public void register() {

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

            if(res.getMessage() == null) {
                compareTest = res.getUsername();
            } else {
                compareTest = res.getMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assertEquals(compareTest, userName);
    }

    // Tries to register the same user again and it fails
    @Test
    public void registerFails() {
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
            RegisterResult res2 = proxy.register(url, request);

            if(res.getMessage() == null) {
                compareTest = res2.getUsername();
            } else {
                compareTest = res2.getMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assertNull(compareTest);
    }



}
