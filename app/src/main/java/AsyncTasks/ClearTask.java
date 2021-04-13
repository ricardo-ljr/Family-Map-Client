package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.ServerProxy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import Result.ClearResult;

public class ClearTask extends AsyncTask<String, Void, ClearResult> {


    private ClearResult clearResult;
    private Fragment fragment;
    private Context context;

    public ClearTask(Fragment frag, Context cont) {
        clearResult = new ClearResult();
        this.fragment = frag;
        this.context = cont;
    }

    @Override
    protected ClearResult doInBackground(String... strings) {
        try {
            ServerProxy proxy = new ServerProxy();
            DataCache data = DataCache.getInstance();

            URL url = new URL("http://" + data.getServerHost() + ":" + data.getServerPort() +"/clear");

//            clearResult = proxy.clear(url);

            return clearResult;
        } catch(IOException e) {
            clearResult.setSuccess(false);
            clearResult.setMessage("Error retrieving events - Events Async Task");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ClearResult res) {
        DataCache data = DataCache.getInstance();
        if(res.isSuccess()) {
            Toast.makeText(context, "Clear succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Internal Server Error", Toast.LENGTH_LONG).show();
        }
    }
}
