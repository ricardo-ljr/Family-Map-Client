package Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.R;

import ui.MapFragment;

public class EventActivity extends AppCompatActivity {

    private String eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventID = getIntent().getExtras().getString("eventID");

        DataCache data = DataCache.getInstance();
        data.setEventID(eventID); // storing event ID for list

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.eventContainer);

        if(mapFragment == null) {
            mapFragment = new MapFragment();
            fm.beginTransaction().add(R.id.eventContainer, mapFragment).commit();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        DataCache data = DataCache.getInstance();
        data.setPersonOrSearch(false); // keep track of when activity ends
    }
}
