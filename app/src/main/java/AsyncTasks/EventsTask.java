package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Result.EventsResult;

public class EventsTask extends AsyncTask<String, Void, EventsResult> {

    private EventsResult eventsResult;
    private Fragment fragment;
    private Context context;

    public EventsTask(Fragment frag, Context cont) {
        eventsResult = new EventsResult();
        this.fragment = frag;
        this.context = cont;
    }

    @Override
    protected EventsResult doInBackground(String... authtoken) {

        try {
            ServerProxy proxy = new ServerProxy();
            DataCache data = DataCache.getInstance();

            URL url = new URL("http://" + data.getServerHost() + ":" + data.getServerPort() +"/event");

            eventsResult = proxy.events(url, authtoken[0]);

            return eventsResult;
        } catch(MalformedURLException e) {
            eventsResult.setSuccess(false);
            eventsResult.setMessage("Error retrieving events - Events Async Task");
        }
        return null;
    }

    @Override
    protected void onPostExecute(EventsResult res) {
        DataCache data = DataCache.getInstance();
        if(res.isSuccess()) {
            data.setEvents(res.getEvents());
            Toast.makeText(context, "Events retrieved for user successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Events retrieval for user failes", Toast.LENGTH_LONG).show();
        }
    }
}
