package ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.MainActivity;
import com.example.family_map_client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Activity.PersonActivity;
import Activity.SearchActivity;
import Activity.SettingsActivity;
import AsyncTasks.FamilyData;
import Model.Event;
import Model.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    public static String ARG_EVENT_ID;
    private GoogleMap map;
    private View view;
    private String selectedPerson = new String();
    private boolean drawLines = false;

    public void setPersonToDraw(Person personToDraw) {
        this.personToDraw = personToDraw;
    }

    private Person personToDraw = null;


    public Person getPersonToDraw() {
        return personToDraw;
    }


    private ArrayList<Polyline> polylines = new ArrayList<Polyline>();
    private ArrayList<Marker> markers = new ArrayList<>();

    public MapFragment() {
    }

    public String getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(String selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        getActivity().setTitle("Family Map");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        DataCache data = DataCache.getInstance();

        if(data.isPersonOrSearch()) {
            setHasOptionsMenu(false);
        } else {
            inflater.inflate(R.menu.main_menu, menu);

            Drawable icon_gear;
            Drawable search_icon;

            icon_gear = new IconDrawable(getContext(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).sizeDp(25);
            menu.findItem(R.id.settingsMenuItem).setIcon(icon_gear);

            search_icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.white).sizeDp(25);
            menu.findItem(R.id.searchMenuItem).setIcon(search_icon);
        }
    }



    // This function was breaking my map and lost me 8 hours of work ( * facepalm * )
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//
//        if(mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        super.onOptionsItemSelected(menu);
        switch(menu.getItemId()) {
            case R.id.searchMenuItem:
                ((MainActivity) getActivity()).displaySearch();
                return true;
            case R.id.settingsMenuItem:
                ((MainActivity) getActivity()).displaySettings();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        DataCache data = DataCache.getInstance();
        map = googleMap;

        data.isCurrentEventOn(); // populating events based on settings activity

        if(!data.isPersonOrSearch()) {
            map.moveCamera(CameraUpdateFactory.newLatLng(data.getStartLocation()));
        }

        for(String key : data.getCurrentPersonEvents().keySet()) {
            float birthColor = BitmapDescriptorFactory.HUE_GREEN;
            float baptism = BitmapDescriptorFactory.HUE_BLUE;
            float marriageColor = BitmapDescriptorFactory.HUE_MAGENTA;
            float deathColor = BitmapDescriptorFactory.HUE_ROSE;
            float asteroidColor = BitmapDescriptorFactory.HUE_YELLOW;

            for (int i = 0; i < data.getCurrentPersonEvents().get(key).size(); i++) {
                Event currEvent = data.getCurrentPersonEvents().get(key).get(i);

                LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());

                MarkerOptions options = new MarkerOptions().position(location);

                // Select random color for any other event not mentioned above

                if (currEvent.getEventType().equals("birth")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(birthColor));
                } else if(currEvent.getEventType().equals("baptism")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(baptism));
                } else if (currEvent.getEventType().equals("marriage")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(marriageColor));
                } else if (currEvent.getEventType().equals("death")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(deathColor));
                } else if(currEvent.getEventType().toLowerCase().equals("completed asteroids")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(asteroidColor));
                } else {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }

                Marker marker = map.addMarker(options);
                marker.setTag(currEvent);
                markers.add(marker);

                // setMarkerListener to (this)
                // Copy code over to it and call
                // if marker is set to true, than draw polylines as well
            }
        }

        if(data.isPersonOrSearch()) {
            float latitude = data.getEvents().get(data.getEventID()).getLatitude();
            float longitute = data.getEvents().get(data.getEventID()).getLongitude();
            float zoomLevel = 4.0f;
            LatLng latLng = new LatLng(latitude, longitute);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }

        map.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                DataCache data = DataCache.getInstance();

                if(!data.isPersonOrSearch()) {
                    data.setStartLocation(marker.getPosition());
                }
                clickMarker(marker);
                data.setMarker(marker);
                return false;
            }
        });

        // Display Event Box on Click
        LinearLayout eventDisplay = view.findViewById(R.id.eventDisplay);

        eventDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("personID", getSelectedPerson());
                startActivity(intent);
            }
        });

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
    }

    /**
     * Draws the PolyLines on click recursive - takes care of showing event description
     *
     * @param marker
     */
    public void clickMarker(Marker marker) {
        DataCache data = DataCache.getInstance();

        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();

        Event event = (Event) marker.getTag();
        Person person = data.getPeople().get(event.getPersonID());
        setSelectedPerson(person.getPersonID());
        data.setEventID(event.getEventID());

        String fullname = person.getFirstName() + " " + person.getLastName();
        String eventDescription = event.getEventType().toUpperCase();
        String timePlace = event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";

        ImageView icon = view.findViewById(R.id.iconImage);
        TextView textName = view.findViewById(R.id.EventPerson);
        TextView textDescription1 = view.findViewById(R.id.EventType);
        TextView textDescription2 =  view.findViewById(R.id.EventTimePlace);

        if(person.getGender().equals("m")) {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.blue).sizeDp(40);
            icon.setImageDrawable(genderIcon);
        } else {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.pink).sizeDp(40);
            icon.setImageDrawable(genderIcon);
        }

        textName.setText(fullname);
        textDescription1.setText(eventDescription);
        textDescription2.setText(timePlace);

        setPersonToDraw(person);
        drawPolyLines(marker.getPosition(), person);
    }

    /**
     * Function that takes care of drawing lines of selected users on the map based on settings
     *
     * @param src
     * @param selectedPerson
     */
    public void drawPolyLines(LatLng src, Person selectedPerson) {

        DataCache data = DataCache.getInstance();
        int currentWidth = 20;

        if(data.isSpouseLinesOn()) {
            if(data.getCurrentPersonEvents().containsKey(selectedPerson.getSpouseID())) {

                LatLng dest = getLatLng(selectedPerson.getSpouseID(), 0);
                addPolyline(src, dest, Color.MAGENTA, currentWidth); // spouse line
            }
        }

        if(data.isLifeStoryLinesOn()) {
            if(data.getCurrentPersonEvents().get(selectedPerson.getPersonID()).size() > 1) {

                for (int i = 0; i < data.getCurrentPersonEvents().get(selectedPerson.getPersonID()).size() - 1; i++) {

                    LatLng source = getLatLng(selectedPerson.getPersonID(), i);
                    LatLng dest = getLatLng(selectedPerson.getPersonID(), i + 1);
                    addPolyline(source, dest, Color.RED, currentWidth); // Life Story line
                }
            }
        }

        if(data.isFamilyTreeLinesOn()) {
            if(data.getCurrentPersonEvents().containsKey(selectedPerson.getFatherID())) {

                LatLng dest = getLatLng(selectedPerson.getFatherID(), 0);
                addPolyline(src, dest, Color.BLUE, currentWidth);

                Person father = data.getPeople().get(selectedPerson.getFatherID());
                drawParentLines(dest, father, currentWidth);
            }

            if(data.getCurrentPersonEvents().containsKey(selectedPerson.getMotherID())) {

                LatLng dest = getLatLng(selectedPerson.getMotherID(), 0);
                addPolyline(src, dest, Color.BLUE, currentWidth);

                Person mother = data.getPeople().get(selectedPerson.getMotherID());
                drawParentLines(dest, mother, currentWidth);
            }
        }
    }

    // Make lines thinner on parent family lines
    public void drawParentLines(LatLng src, Person person, int currentWidth) {
        DataCache data = DataCache.getInstance();

        if (currentWidth > 4) {
            currentWidth = (currentWidth / 2);
        }

        if(data.getCurrentPersonEvents().containsKey(person.getFatherID())) {
            LatLng dest = getLatLng(person.getFatherID(), 0);
            addPolyline(src, dest, Color.BLUE, currentWidth);

            Person father = data.getPeople().get(person.getFatherID());
            drawParentLines(dest, father, currentWidth);
        }

        if(data.getCurrentPersonEvents().containsKey(person.getMotherID())) {
            LatLng dest = getLatLng(person.getMotherID(), 0);
            addPolyline(src, dest, Color.BLUE, currentWidth);

            Person mother = data.getPeople().get(person.getMotherID());
            drawParentLines(dest, mother, currentWidth);
        }
    }

    public void addPolyline(LatLng src, LatLng dest, int color, int width) {

        Polyline newPolyline = map.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(new LatLng(src.latitude, src.longitude),
                        new LatLng(dest.latitude, dest.longitude))
                .width(width).color(color));

        polylines.add(newPolyline);
    }

    public LatLng getLatLng(String personID, int position) {
        DataCache data = DataCache.getInstance();
        LatLng newLatLng = new LatLng(data.getCurrentPersonEvents().get(personID).get(position).getLatitude(),
                data.getCurrentPersonEvents().get(personID).get(position).getLongitude());

        return newLatLng;
    }

    @Override
    public void onResume() {
        super.onResume();

        DataCache data = DataCache.getInstance();
        if(data.isLoggedIn()) {
            if(map != null) { // create a click event on marker
                onMapReady(map);
                reDrawMapOnReload();
                reDrawPolyLines();
            }
        }
    }

    private void reDrawMapOnReload() {
        DataCache data = DataCache.getInstance();

        for(Marker m: markers) {
            m.remove();
        }
        markers.clear();

        // Re-add markers
        for(String key : data.getCurrentPersonEvents().keySet()) {
            float birthColor = BitmapDescriptorFactory.HUE_GREEN;
            float baptism = BitmapDescriptorFactory.HUE_BLUE;
            float marriageColor = BitmapDescriptorFactory.HUE_MAGENTA;
            float deathColor = BitmapDescriptorFactory.HUE_ROSE;
            float asteroidColor = BitmapDescriptorFactory.HUE_YELLOW;

            for (int i = 0; i < data.getCurrentPersonEvents().get(key).size(); i++) {
                Event currEvent = data.getCurrentPersonEvents().get(key).get(i);

                LatLng location = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());

                MarkerOptions options = new MarkerOptions().position(location);

                // Select random color for any other event not mentioned above

                if (currEvent.getEventType().equals("birth")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(birthColor));
                } else if(currEvent.getEventType().equals("baptism")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(baptism));
                } else if (currEvent.getEventType().equals("marriage")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(marriageColor));
                } else if (currEvent.getEventType().equals("death")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(deathColor));
                } else if(currEvent.getEventType().toLowerCase().equals("completed asteroids")) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(asteroidColor));
                } else {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }

                Marker marker = map.addMarker(options);
                marker.setTag(currEvent);
                markers.add(marker);

                // setMarkerListener to (this)
                // Copy code over to it and call
                // if marker is set to true, than draw polylines as well
            }
        }
    }

    private void reDrawPolyLines() {
        DataCache data = DataCache.getInstance();

        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();

        drawPolyLines(data.getMarker().getPosition(), getPersonToDraw());
    }


}
