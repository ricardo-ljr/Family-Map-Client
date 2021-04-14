package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.MainActivity;
import com.example.family_map_client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Model.Person;
import Result.PersonsResult;

public class FamilyData extends AsyncTask<String, Integer, PersonsResult> {

    private PersonsResult personRes;
    private Fragment fragment;
    private Context context;

    public FamilyData (Fragment frag, Context cont) {
        personRes = new PersonsResult();
        this.fragment = frag;
        this.context = cont;
    }

    public FamilyData() { }

    @Override
    protected PersonsResult doInBackground(String... authtoken) {
        try {
            ServerProxy proxy = new ServerProxy();
            DataCache data = DataCache.getInstance();

            URL url = new URL("http://" + data.getServerHost() + ":" + data.getServerPort() +"/person");

            personRes = proxy.persons(url, authtoken[0]);

            return personRes;
        } catch (MalformedURLException e) {
            personRes.setSuccess(false);
            personRes.setMessage("Error logging in - Family Data Async Task");
            return personRes;
        }
    }

    @Override
    protected void onPostExecute(PersonsResult res) {
        DataCache data = DataCache.getInstance();
        if(res.isSuccess()) {
            data.setPeople(res.getPerson());
            Toast.makeText(context, "People successfully received for " + ((Person) personRes.getPerson()[0]).getFirstName() + " " +((Person) personRes.getPerson()[0]).getLastName() + ".", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Retrieving people for " + ((Person) personRes.getPerson()[0]).getFirstName() + " " +((Person) personRes.getPerson()[0]).getLastName() + " failed.", Toast.LENGTH_SHORT).show();
        }
    }
}

