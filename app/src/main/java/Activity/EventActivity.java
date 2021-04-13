package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.R;

import ui.MapFragment;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent myIntent = getIntent();
        String eventID = myIntent.getStringExtra("eventID");

        DataCache data = DataCache.getInstance();
        data.setEventID(eventID); // storing event ID for list

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Creating fragment map for event display
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.eventContainer);

        if(mapFragment == null) {
            mapFragment = new MapFragment();
            fm.beginTransaction().add(R.id.eventContainer, mapFragment).commit();
        }
    }

    // Ending event activity and returning to map
    @Override
    protected void onPause(){
        super.onPause();
        DataCache data = DataCache.getInstance();
        data.setPersonOrSearch(false); // keep track of when activity ends, and switches to false
    }
}
