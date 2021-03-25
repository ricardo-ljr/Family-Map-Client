package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

    private RegisterResult registerRes;
    private Fragment fragment;
    private Context context;

    public RegisterTask(Fragment frag, Context cont) {
        registerRes = new RegisterResult();
        this.fragment = frag;
        this.context = cont;
    }

    @Override
    protected RegisterResult doInBackground(RegisterRequest... request) {
        try {
            ServerProxy proxy = new ServerProxy();

            URL url = new URL("http://10.0.2.2:8000/user/login");
            registerRes = proxy.register(url, request[0]);

            return registerRes;
        } catch (MalformedURLException e) {
            registerRes.setSuccess(false);
            registerRes.setMessage("Error when registering user - Register Async Task");
            return registerRes;
        }
    }

    @Override
    protected void onPostExecute(RegisterResult res) {
        if (res.isSuccess()) {
            Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Register Failed", Toast.LENGTH_LONG).show();
        }
    }
}
