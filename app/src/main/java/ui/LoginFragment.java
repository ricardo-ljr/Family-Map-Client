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

import com.example.family_map_client.R;

import Request.LoginRequest;
import Request.RegisterRequest;

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
    private RadioButton maleButton;
    private RadioButton femaleButton;

    // Private variable for buttons
    private Button loginButton;
    private Button registerButton;

    // Wire in widgets

    public static LoginFragment newInstance(Context context) {
        thisContext = context;
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

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

        // Form contents

        serverHost = (EditText) v.findViewById(R.id.ServerHost);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        serverHost.addTextChangedListener(textWatcher);


        return v;
    }

    private void onRegisterClick() {

    }

    private void onLoginClick() {

    }
}
