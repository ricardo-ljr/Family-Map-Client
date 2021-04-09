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
    private Map<String, Event> events; // list of event ID's
    private Map<String, ArrayList<Event>> personEvents; // list of chronologically sorted events
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


    /******** Variables that handle event functions ********/

    private boolean isLoggedIn = false;
    private String eventID = new String();


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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    /**
     * Storing all events for logged in user and respective family tree
     *
     * @param event
     */
    public void setEvents(Event[] event) {
        setLoggedIn(true);
        setEventID(event[0].getEventID());

        //Store temp keys as EventID for later use
        Map<String, ArrayList<Event>> tempEvent = new HashMap<>();

        for (int i = 0; i < event.length; i++) {
            events.put(event[i].getPersonID(), event[i]);
            eventTypes.add(event[i].getEventType().toLowerCase());

            if(!tempEvent.containsKey(event[i].getPersonID())) {
                ArrayList<Event> newEventList = new ArrayList<>();
                tempEvent.put(event[i].getPersonID(), newEventList);
            }

            tempEvent.get(event[i].getPersonID()).add(event[i]);
        }

        // Chronologically order for events
        for (String key : tempEvent.keySet()) {
            Set birthSet = new HashSet<>();
            Set deathSet = new HashSet<>();
            ArrayList<Event> eventList = new ArrayList<Event>();

            // Adding events in chronological order based on events
            for(int i = 0; i < tempEvent.get(key).size(); i++) {
                Event currEvent = tempEvent.get(key).get(i);

                if(currEvent.getEventType().toLowerCase().equals("birth")) {
                    birthSet.add(currEvent);
                } else if (currEvent.getEventType().toLowerCase().equals("death")) {
                    deathSet.add(currEvent);
                } else {
                    if(eventList.size() > 0) {
                        if (currEvent.getYear() < eventList.get(0).getYear()) {
                            eventList.add(0, currEvent);
                        } else if (currEvent.getYear() >= eventList.get(eventList.size() - 1).getYear()) {
                            for (int j = 0; j < eventList.size() - 1; j++) {
                                if(eventList.get(j).getYear() <= currEvent.getYear() && eventList.get(j + 1).getYear() > currEvent.getYear()) {
                                    eventList.add(j + 1, currEvent);
                                }
                            }
                        }
                    } else {
                        eventList.add(currEvent);
                    }
                }
            }

            ArrayList<Event> orderedList = new ArrayList<Event>();

            orderedList.addAll(birthSet);
            orderedList.addAll(eventList);
            orderedList.addAll(deathSet);

            personEvents.put(key, orderedList);
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
