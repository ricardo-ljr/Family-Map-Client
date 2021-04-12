package Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_map_client.R;

import java.util.ArrayList;

import Model.Event;
import Model.Person;

public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 0;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                SearchAdapter adapter = new SearchAdapter(personArrayList, eventArrayList)

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

            return new SearchViewHolder()(view, viewType);
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
        @Override
        public void onClick(View v) {

        }
    }

}


