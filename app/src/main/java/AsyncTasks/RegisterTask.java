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

import Request.RegisterRequest;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

    private RegisterRequest resRequest;
    private RegisterResult registerRes;
    private Fragment fragment;
    private Context context;
    private MainActivity mainActivity;


    public void setContext(Context context) {
        this.context = context;
    }

    public RegisterTask(Fragment frag, Context cont) {
        registerRes = new RegisterResult();
        this.fragment = frag;
        this.context = cont;
    }

    @Override
    protected RegisterResult doInBackground(RegisterRequest... request) {
        try {
            ServerProxy proxy = new ServerProxy();
            DataCache data = DataCache.getInstance();

            URL url = new URL("http://" + data.getServerHost() + ":" + data.getServerPort() +"/user/register");

            registerRes = proxy.register(url, request[0]);

            return registerRes;
        } catch (MalformedURLException e) {
            registerRes.setSuccess(false);
            registerRes.setMessage("Error registering user - Async Register Task");
            return registerRes;
        }
    }

    @Override
    protected void onPostExecute(RegisterResult res) {
        DataCache data = DataCache.getInstance();
        if (res.isSuccess()) {
            data.setAuthtoken(res.getAuthToken());
            Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Register Failed", Toast.LENGTH_LONG).show();
        }
    }
}
