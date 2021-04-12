package Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Model.Event;
import Model.Person;

public class PersonActivity extends AppCompatActivity {

    private String personID = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataCache data = DataCache.getInstance();

        personID = getIntent().getExtras().getString("personID");

        TextView firstName = findViewById(R.id.personFirstName);
        firstName.setText(data.getPeople().get(personID).getFirstName());

        TextView lastName = findViewById(R.id.personLastName);
        lastName.setText(data.getPeople().get(personID).getLastName());

        TextView gender = findViewById(R.id.personGender);

        if(data.getPeople().get(personID).getGender().equals("m")) {
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new ExpandableListAdapter(getFamilyArrayList(), getEventArrayList()));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private ArrayList<Event> eventArrayList;
        private ArrayList<Person> familyArrayList;

        public ExpandableListAdapter(ArrayList<Person> familyArrayList, ArrayList<Event> eventArrayList) {
            this.eventArrayList = eventArrayList;
            this.familyArrayList = familyArrayList;

        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case 0:
                    return eventArrayList.size();
                case 1:
                    return familyArrayList.size();
                default:
                    return Integer.parseInt(null);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case 0:
                    return "Event";
                case 1:
                    return "Family";
                default:
                    return null;
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case 0:
                    return eventArrayList.get(childPosition);
                case 1:
                    return familyArrayList.get(childPosition);
                default:
                    return null;
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_group, parent, false);
            }

            TextView title = convertView.findViewById(R.id.nameOfList);

            if (groupPosition == 0) {
                title.setText("Life Events");
            } else {
                title.setText("Family");
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view;

            if (groupPosition == 0) {
                view = getLayoutInflater().inflate(R.layout.list_event, parent, false);
                eventView(view, childPosition);
            } else {
                view = getLayoutInflater().inflate(R.layout.list_person, parent, false);
                familyView(view, childPosition);
            }

            return view;
        }

        private void familyView(View personView, final int childPosition) {
            DataCache data = DataCache.getInstance();

            // Set gender icons based on gender retrieved
            ImageView icon = personView.findViewById(R.id.iconPerson);
            if (familyArrayList.get(childPosition).getGender().equals("m")) {
                Drawable genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_male).colorRes(R.color.blue).sizeDp(40);
                icon.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_female).colorRes(R.color.pink).sizeDp(40);
                icon.setImageDrawable(genderIcon);
            }

            // Set persons full name
            TextView personName = personView.findViewById(R.id.PersonName);
            String fullName = familyArrayList.get(childPosition).getFirstName() + " " +
                    familyArrayList.get(childPosition).getLastName();
            personName.setText(fullName);

            // Calls getRelationship function to determine relationship with the current person selected
            Person currPerson = data.getPeople().get(personID);
            TextView personRelationship = personView.findViewById(R.id.Relationship);
            personRelationship.setText(data.getRelationship(currPerson, familyArrayList.get(childPosition).getPersonID()));

            personView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String personID = familyArrayList.get(childPosition).getPersonID();
                    Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                    intent.putExtra("personID", personID);
                    startActivity(intent);
                }
            });

        }

        private void eventView(View eventView, final int childPosition) {


            ImageView icon = eventView.findViewById(R.id.iconEvent);
            Drawable eventIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.black).sizeDp(50);
            icon.setImageDrawable(eventIcon);

            TextView personName = eventView.findViewById(R.id.EventPerson1);
            personName.setText(eventArrayList.get(childPosition).getEventType().toUpperCase() + ": ");

            TextView location = eventView.findViewById(R.id.EventTimePlace1);
            String place = eventArrayList.get(childPosition).getCity() + ", " +
                           eventArrayList.get(childPosition).getCountry() + " (" +
                           eventArrayList.get(childPosition).getYear() + ")";
            location.setText(place);

            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataCache data = DataCache.getInstance();
                    data.setPersonOrSearch(true);
                    String eventID = eventArrayList.get(childPosition).getEventID();
                    Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                    intent.putExtra("eventID", eventID);
                    startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

        public ArrayList<Person> getFamilyArrayList() {
            DataCache data = DataCache.getInstance();
            ArrayList<Person> personArrayList = new ArrayList<Person>();
            Person currPerson = data.getPeople().get(personID);

            if(currPerson.getFatherID() != null) {
                personArrayList.add(data.getPeople().get(currPerson.getFatherID()));
            }
            if(currPerson.getMotherID() != null) {
                personArrayList.add(data.getPeople().get(currPerson.getMotherID()));
            }
            if(currPerson.getSpouseID() != null) {
                personArrayList.add(data.getPeople().get(currPerson.getSpouseID()));
            }
            if(data.getChildrenMap().containsKey(currPerson.getPersonID())) {
                personArrayList.add(data.getChildrenMap().get(currPerson.getPersonID()));
            }

            return personArrayList;
        }

        public ArrayList<Event> getEventArrayList() {
            DataCache data = DataCache.getInstance();
            ArrayList<Event> eventArrayList = new ArrayList<Event>();

            if(data.getCurrentPersonEvents().containsKey(personID)) {
                eventArrayList.addAll(data.getCurrentPersonEvents().get(personID));
            }

            return eventArrayList;
        }
    }

