package AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.ServerProxy;

import java.net.HttpURLConnection;
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

            URL url = new URL("http://10.0.2.2:8000/user/login");
            loginRes = proxy.login(url, request[0]);

            return loginRes;
        } catch (MalformedURLException e) {
            loginRes.setSuccess(false);
            loginRes.setMessage("Error logging in - Login Async Task");
            return loginRes;
        }
    }

    @Override
    protected void onPostExecute(LoginResult res) {
        if(res.isSuccess()) {
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

}
