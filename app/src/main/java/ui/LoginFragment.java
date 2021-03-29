package ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.family_map_client.DataCache;
import com.example.family_map_client.MainActivity;
import com.example.family_map_client.R;

import AsyncTasks.LoginTask;
import AsyncTasks.RegisterTask;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.RegisterResult;

public class LoginFragment extends Fragment {

    public static final String ARG_TITLE = "title";
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    private static Context thisContext;

    // Private variables for form
    private EditText serverHost;
    private EditText serverPort;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup gender;
    private String genderSelected;
    private RadioButton maleButton;
    private RadioButton femaleButton;

    // Private variable for buttons
    private Button loginButton;
    private Button registerButton;

    private MainActivity mainActivity;

    // Wire in widgets

    public static LoginFragment newInstance(Context context) {
        thisContext = context;
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
    }

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);

        // Form contents
        serverHost = (EditText) v.findViewById(R.id.ServerHost);
        serverPort = (EditText) v.findViewById(R.id.ServerPort);
        username = (EditText) v.findViewById(R.id.UserName);
        password = (EditText) v.findViewById(R.id.Password);
        firstName = (EditText) v.findViewById(R.id.FirstName);
        lastName = (EditText) v.findViewById(R.id.LastName);
        email = (EditText) v.findViewById(R.id.Email);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton();
            }
        };

        serverHost.addTextChangedListener(textWatcher);
        serverPort.addTextChangedListener(textWatcher);
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);

        gender = (RadioGroup) v.findViewById(R.id.Gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableButton();
            }
        });

        maleButton = (RadioButton) v.findViewById(R.id.MaleButton);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRequest.setGender("m");
            }
        });

        femaleButton = (RadioButton) v.findViewById(R.id.FemaleButton);
        femaleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registerRequest.setGender("f");
            }
        });

        registerButton = (Button) v.findViewById(R.id.RegisterButton);
        registerButton.setEnabled(false);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });

        loginButton = (Button) v.findViewById(R.id.LoginButton);
        loginButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });

        return v;
    }

    private void onRegisterClick() {
        DataCache data = DataCache.getInstance();

        // Setting server Port and Host
        data.setServerPort(serverPort.getText().toString());
        data.setServerHost(serverHost.getText().toString());

        // Register Request
        registerRequest.setUserName(username.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setFirstName(firstName.getText().toString());
        registerRequest.setLastName(lastName.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        RegisterTask task = new RegisterTask(this, thisContext);
        task.execute(registerRequest);
    }

    private void onLoginClick() {
        DataCache data = DataCache.getInstance();

        // Setting server Port and Host
        data.setServerPort(serverPort.getText().toString());
        data.setServerHost(serverHost.getText().toString());

        //Login Request
        loginRequest.setUserName(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        LoginTask task = new LoginTask(this, thisContext);
        task.execute(loginRequest);
    }

    private void enableButton() {
        if (serverHost.length() > 0 && serverPort.length() > 0 && username.length() > 0 && password.length() > 0) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }

        boolean genderNotSelected = true;

        if (gender.getCheckedRadioButtonId() == -1) {
            genderNotSelected = false;
        }

        if (serverHost.length() > 0 && serverPort.length() > 0 &&
                username.length() > 0 && password.length() > 0 &&
                firstName.length() > 0 && lastName.length() > 0 &&
                email.length() > 0 && genderNotSelected) {
            registerButton.setEnabled(true);
        } else {
            registerButton.setEnabled(false);
        }
    }

}
