package ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    public static final String ARG_TITLE = "title";

    // Wire in widgets

    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


}
