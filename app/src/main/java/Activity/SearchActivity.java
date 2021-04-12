package Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import Model.Event;
import Model.Person;

public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

//                DataGenerator generator = new DataGenerator();
//                List<SkiResort> skiResorts = generator.getSkiResorts();
//                List<HikingTrail> hikingTrails = generator.getHikingTrails();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                SearchAdapter adapter = new SearchAdapter(personSearch(newText.toLowerCase()), eventSearch(newText.toLowerCase()));

                recyclerView.setAdapter(adapter);

                return false;
            }
        });
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

        private final ArrayList<Person> personArrayList;
        private final ArrayList<Event> eventArrayList;

        private SearchAdapter(ArrayList<Person> personArrayList, ArrayList<Event> eventArrayList) {
            this.personArrayList = personArrayList;
            this.eventArrayList = eventArrayList;
        }

        @Override
        public int getItemViewType(int position) {
            return position < personArrayList.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.list_person, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.list_event, parent, false);
            }

            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if(position < personArrayList.size()) {
                holder.bind(personArrayList.get(position));
            } else {
                holder.bind(eventArrayList.get(position - personArrayList.size()));
            }
        }

        @Override
        public int getItemCount() {
            return personArrayList.size() + eventArrayList.size();
        }

    }

    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView icon;
        private final TextView personName;
        private final TextView eventType;
        private final TextView namePlace;

        private final int viewType;
        private Person person;
        private Event event;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                icon = itemView.findViewById(R.id.iconPerson);
                personName = itemView.findViewById(R.id.PersonName);
                eventType = null;
                namePlace = null;
            } else {
                icon = itemView.findViewById(R.id.iconEvent);
                personName = itemView.findViewById(R.id.EventPerson1);
                eventType = itemView.findViewById(R.id.EventType1);
                namePlace = itemView.findViewById(R.id.EventTimePlace1);
            }
        }

        private void bind(Person person) {
            this.person = person;

            if(person.getGender().equals("m")) {
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_male).colorRes(R.color.blue).sizeDp(40);
                icon.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_female).colorRes(R.color.pink).sizeDp(40);
                icon.setImageDrawable(genderIcon);
            }

            String fullName = person.getFirstName() + " " + person.getLastName();
            personName.setText(fullName.toUpperCase());
        }

        private void bind(Event event) {
            this.event = event;

            DataCache data = DataCache.getInstance();
            Person person = data.getPeople().get(event.getPersonID());

            Drawable eventIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.black).sizeDp(40);
            icon.setImageDrawable(eventIcon);

            String fullName = person.getFirstName() + " " + person.getLastName() + " - " + event.getEventType() + " \n" + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")" ;
            personName.setText(fullName);
//            String type = event.getEventType();
//            eventType.setText(type);
//            String namePlace1 = event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
//            namePlace.setText(namePlace1);
        }
        @Override
        public void onClick(View v) {

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("personID", person.getPersonID());
                startActivity(intent);
            } else {
                DataCache data = DataCache.getInstance();
                data.setPersonOrSearch(true);
                Intent intent = new Intent(SearchActivity.this, EventActivity.class);
                intent.putExtra("eventID", event.getEventID());
                startActivity(intent);
            }

        }
    }

    public ArrayList<Person> personSearch(String query) {
        DataCache data = DataCache.getInstance();

        ArrayList<Person> personList = new ArrayList<Person>();

        for(String key : data.getPeople().keySet()) {
            Person currPerson = data.getPeople().get(key);

            if(currPerson.getFirstName().toLowerCase().contains(query)) {
                personList.add(currPerson);
            } else if(currPerson.getLastName().toLowerCase().contains(query)) {
                personList.add(currPerson);
            }
        }

        return personList;
    }

    public ArrayList<Event> eventSearch(String query) {
        DataCache data = DataCache.getInstance();

        ArrayList<Event> eventList = new ArrayList<>();

        for(String key : data.getCurrentPersonEvents().keySet()) {
            Person person = data.getPeople().get(key);

            if(person.getFirstName().toLowerCase().contains(query) || person.getLastName().toLowerCase().contains(query)) {
                eventList.addAll(data.getCurrentPersonEvents().get(key));
            } else {
                for(int i = 0; i < data.getCurrentPersonEvents().get(key).size(); i++) {
                    Event currEvent = data.getCurrentPersonEvents().get(key).get(i);

                    if(currEvent.getCountry().toLowerCase().contains(query)) {
                        eventList.add(currEvent);
                    } else if(currEvent.getCity().toLowerCase().contains(query)) {
                        eventList.add(currEvent);
                    } else if(currEvent.getEventType().toLowerCase().contains(query)) {
                        eventList.add(currEvent);
                    } else if(Integer.toString(currEvent.getYear()).contains(query)) {
                        eventList.add(currEvent);
                    }
                }
            }
        }

        return eventList;
    }

}


