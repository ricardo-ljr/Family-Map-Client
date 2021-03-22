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

    private static DataCache _instance = new DataCache();

    public static DataCache getInstance() {
        return _instance;
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
        people = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        eventTypes = new ArrayList<>();
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
    }


}
