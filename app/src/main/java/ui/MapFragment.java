package ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.family_map_client.MainActivity;
import com.example.family_map_client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import Activity.SearchActivity;
import Activity.SettingsActivity;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
       getActivity().setTitle("Family Map");
       return layoutInflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        Drawable icon_gear;
        Drawable search_icon;

        icon_gear = new IconDrawable(getContext(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).sizeDp(25);
        menu.findItem(R.id.settingsMenuItem).setIcon(icon_gear);

        search_icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.white).sizeDp(25);
        menu.findItem(R.id.searchMenuItem).setIcon(search_icon);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        super.onOptionsItemSelected(menu);
        switch(menu.getItemId()) {
            case R.id.searchMenuItem:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
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
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


//    @Override
//    public void onMapLoaded() {
//        // You probably don't need this callback. It occurs after onMapReady and I have seen
//        // cases where you get an error when adding markers or otherwise interacting with the map in
//        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
//        // move all code where you interact with the map (everything after
//        // map.setOnMapLoadedCallback(...) above) to here.
//    }
}
