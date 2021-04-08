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



    /******* Strings for login purposes ********/

    private String authtoken;
    private String personID;
    private String serverHost;
    private String serverPort;
    private String firstName;
    private String lastName;
    private boolean success;

    /******* Boolean Variables to check for settings switch ********/

    private boolean isLifeStoryLinesOn = true;
    private boolean isFamilyTreeLinesOn = true;
    private boolean isSpouseLinesOn = true;
    private boolean isFatherSideOn = true;
    private boolean isMotherSideOn = true;
    private boolean isMaleEventsOn = true;
    private boolean isFemaleEventsOn = true;



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


    /**
     * Storing all persons for logged in user
     *
     * @param persons
     */
    public void setPeople(Person[] persons) {
        setUser(persons[0]);

        for (int i = 0; i < persons.length; i++) {
            people.put(persons[i].getPersonID(), persons[i]);
        }
    }


    /********* Settings Switch Getter and Setter Found Here *********/

    public boolean isLifeStoryLinesOn() {
        return isLifeStoryLinesOn;
    }

    public void setLifeStoryLinesOn(boolean lifeStoryLinesOn) {
        isLifeStoryLinesOn = lifeStoryLinesOn;
    }

    public boolean isFamilyTreeLinesOn() {
        return isFamilyTreeLinesOn;
    }

    public void setFamilyTreeLinesOn(boolean familyTreeLinesOn) {
        isFamilyTreeLinesOn = familyTreeLinesOn;
    }

    public boolean isSpouseLinesOn() {
        return isSpouseLinesOn;
    }

    public void setSpouseLinesOn(boolean spouseLinesOn) {
        isSpouseLinesOn = spouseLinesOn;
    }

    public boolean isFatherSideOn() {
        return isFatherSideOn;
    }

    public void setFatherSideOn(boolean fatherSideOn) {
        isFatherSideOn = fatherSideOn;
    }

    public boolean isMotherSideOn() {
        return isMotherSideOn;
    }

    public void setMotherSideOn(boolean motherSideOn) {
        isMotherSideOn = motherSideOn;
    }

    public boolean isMaleEventsOn() {
        return isMaleEventsOn;
    }

    public void setMaleEventsOn(boolean maleEventsOn) {
        isMaleEventsOn = maleEventsOn;
    }

    public boolean isFemaleEventsOn() {
        return isFemaleEventsOn;
    }

    public void setFemaleEventsOn(boolean femaleEventsOn) {
        isFemaleEventsOn = femaleEventsOn;
    }

}
