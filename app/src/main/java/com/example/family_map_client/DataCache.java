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
    private Map<String, ArrayList<Event>> currentPersonEvents; // list of current events added to map based on settings
    private List<String> eventTypes;

    private Map<String, Person> childrenMap;
    private Set<String> maleSpouse;
    private Set<String> femaleSpouse;
    private Set<String> paternalAncestorsMales;
    private Set<String> paternalAncestorsFemales;
    private Set<String> maternalAncestorsMales;
    private Set<String> maternalAncestorsFemales;
    private Map<String, List<Person>> personChildren;

    /**
     * Private constructor to store person and event data that comes back from server
     */
    private DataCache() {
        this.people = new HashMap<>();
        this.events = new HashMap<>();
        this.personEvents = new HashMap<>();
        this.currentPersonEvents = new HashMap<>();
        this.eventTypes = new ArrayList<>();
        this.childrenMap = new HashMap<>();
        this.maleSpouse = new HashSet<>();
        this.femaleSpouse = new HashSet<>();
        this.paternalAncestorsMales = new HashSet<>();
        this.paternalAncestorsFemales = new HashSet<>();
        this.maternalAncestorsMales = new HashSet<>();
        this.maternalAncestorsFemales = new HashSet<>();
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

        setSpouse();
        setFamilyTree();
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

    /**
     * This function sets the family tree for the user in the app
     *
     */
    public void setFamilyTree() {

        //Set Father's Side
        if(user.getFatherID() != null) {
            Person father = people.get(user.getFatherID());
            paternalAncestorsMales.add(father.getPersonID());
            childrenMap.put(user.getFatherID(), user);

            setFatherSide(father);
        }

        if(user.getMotherID() != null) {
            Person mother = people.get(user.getMotherID());
            maternalAncestorsFemales.add(mother.getPersonID());
            childrenMap.put(user.getMotherID(), user);

            setMotherSide(mother);
        }
    }

    /**
     * Recursive function to store paternal side of users family
     *
     * @param currPerson
     */
    public void setFatherSide(Person currPerson) {

        if(currPerson.getFatherID() != null) {
            Person father = people.get(currPerson.getFatherID());
            paternalAncestorsMales.add(father.getPersonID());
            childrenMap.put(currPerson.getFatherID(), currPerson);

            setFatherSide(father);
        }

        if(currPerson.getMotherID() != null) {
            Person mother = people.get(currPerson.getMotherID());
            paternalAncestorsFemales.add(mother.getPersonID());
            childrenMap.put(currPerson.getMotherID(), currPerson);

            setFatherSide(mother);
        }
    }

    /**
     *
     * Recursive function to store maternal side of users family
     *
     * @param currPerson
     */
    public void setMotherSide(Person currPerson) {

        if(currPerson.getFatherID() != null) {
            Person father = people.get(currPerson.getFatherID());
            maternalAncestorsMales.add(father.getPersonID());
            childrenMap.put(currPerson.getFatherID(), currPerson);

            setMotherSide(father);
        }

        if(currPerson.getMotherID() != null) {
            Person mother = people.get(currPerson.getMotherID());
            maternalAncestorsFemales.add(mother.getPersonID());
            childrenMap.put(currPerson.getMotherID(), currPerson);

            setMotherSide(mother);
        }

    }

    /**
     * This function set the spouse for the user and family
     *
     */
    public void setSpouse() {

        if (user.getGender().equals('m')) {
            maleSpouse.add(user.getPersonID());
        } else {
            femaleSpouse.add(user.getPersonID());
        }

        if(user.getSpouseID() != null) {

            Person spouse = people.get(user.getSpouseID());

            if(spouse.getGender().equals('m')) {
                maleSpouse.add(spouse.getPersonID());
            } else {
                femaleSpouse.add(spouse.getPersonID());
            }
        }
    }

    /**
     * This function is in charge of populating the map based on the settings given for the events
     * Based on the gender filter for the family given according to specs
     */
    public void isCurrentEventOn() {
        // Clear events before putting them back
        currentPersonEvents.clear();

        ArrayList<String> currentPeopleList = new ArrayList<String>();

        if(isMaleEventsOn) {
            currentPeopleList.addAll(maleSpouse);
        }
        if(isFemaleEventsOn) {
            currentPeopleList.addAll(femaleSpouse);
        }

        if(isMaleEventsOn && isFatherSideOn) {
            currentPeopleList.addAll(paternalAncestorsMales);
        }
        if(isFemaleEventsOn && isMotherSideOn) {
            currentPeopleList.addAll(paternalAncestorsFemales);
        }

        if(isMaleEventsOn && isMotherSideOn) {
            currentPeopleList.addAll(maternalAncestorsMales);
        }
        if(isFemaleEventsOn && isMotherSideOn) {
            currentPeopleList.addAll(maternalAncestorsFemales);
        }

        for (int i = 0; i < currentPeopleList.size(); i++) {
            String personID = currentPeopleList.get(i);
            ArrayList<Event> eventList = personEvents.get(personID);

            currentPersonEvents.put(personID, eventList);
        }
    }

    public String getRelationship(Person currPerson, String personID) {
        String relationship = new String();

        if(currPerson.getFatherID() != null) {
            if(currPerson.getFatherID().equals(personID)) {
                relationship = "Father";
            }
        }

        if(currPerson.getMotherID() != null) {
            if(currPerson.getMotherID().equals(personID)) {
                relationship = "Mother";
            }
        }

        if(currPerson.getSpouseID() != null) {
            if(currPerson.getSpouseID().equals(personID)) {
                relationship = "Spouse";
            }
        }

        if(getChildrenMap().containsKey(personID)) {
            if(getChildrenMap().get(personID).getPersonID().equals(personID)) {
                relationship = "Child";
            }
        }

        return relationship;
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

    public Map<String, Person> getChildrenMap() {
        return childrenMap;
    }
}
