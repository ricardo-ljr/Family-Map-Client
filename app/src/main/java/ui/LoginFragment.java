package ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import Request.LoginRequest;
import Request.RegisterRequest;

public class LoginFragment extends Fragment {

    public static final String ARG_TITLE = "title";
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

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

    // Wire in widgets

    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
