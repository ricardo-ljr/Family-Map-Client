package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Result.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {

    private LoginResult loginRes;
    private Fragment fragment;
    private Context context;

    public LoginTask(Fragment frag, Context cont) {
        loginRes = new LoginResult();
        this.fragment = frag;
        this.context = cont;
    }

    @Override
    protected LoginResult doInBackground(LoginRequest... request) {
        try {
            ServerProxy proxy = new ServerProxy();
            DataCache data = DataCache.getInstance();

            URL url = new URL("http://" + data.getServerHost() + ":" + data.getServerPort() +"/user/login");

            loginRes = proxy.login(url, request[0]);

            loginRes.setSuccess(true);
            loginRes.setMessage("Successful Login - Login Async Task");
            return loginRes;
        } catch (MalformedURLException e) {
            loginRes.setSuccess(false);
            loginRes.setMessage("Error logging in - Login Async Task");
            return loginRes;
        }
    }

    @Override
    protected void onPostExecute(LoginResult res) {
        DataCache data = DataCache.getInstance();
        if(res.isSuccess()) {
            data.setAuthtoken(res.getAuthToken());
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

}
