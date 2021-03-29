package com.example.family_map_client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;

/**
 * DataCache class used to store the data coming back from the server
 */
public class DataCache {

    private static DataCache instance;

    private Person user;

    public static DataCache getInstance() {

        if (instance == null) {
            instance = new DataCache();
        }

        return instance;
    }

    private Map<String, Person> people;
    private Map<String, Event> events;
    private Map<String, List<Event>> personEvents; // list of chronologically sorted events
    private List<String> eventTypes;
    private Set<String> paternalAncestors;
    private Set<String> maternalAncestors;
    private Map<String, List<Person>> personChildren;

    /**
     * Private constructor to store person and event data that comes back from server
     */
    private DataCache() {
        this.people = new HashMap<>();
        this.events = new HashMap<>();
        this.personEvents = new HashMap<>();
        this.eventTypes = new ArrayList<>();
        this.paternalAncestors = new HashSet<>();
        this.maternalAncestors = new HashSet<>();
    }

    private String authtoken;
    private String personID;
    private String serverHost;
    private String serverPort;
    private String firstName;
    private String lastName;
    private boolean success;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerHost(String serverHost) {
        return serverHost;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }



}
