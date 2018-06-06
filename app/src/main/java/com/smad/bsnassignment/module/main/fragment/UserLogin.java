package com.smad.bsnassignment.module.main.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smad.bsnassignment.BaseFragment;
import com.smad.bsnassignment.R;
import com.smad.bsnassignment.module.feedback.SendFeedback;
import com.smad.bsnassignment.support.ExecuteTask;
import com.smad.bsnassignment.support.ShardPref;

import org.json.JSONObject;

import static com.smad.bsnassignment.support.ExecuteTask.BASE;
import static com.smad.bsnassignment.support.ExecuteTask.MESSAGE;
import static com.smad.bsnassignment.support.ExecuteTask.PASSWORD;
import static com.smad.bsnassignment.support.ExecuteTask.STATUS;
import static com.smad.bsnassignment.support.ExecuteTask.USERNAME;
import static com.smad.bsnassignment.support.ExecuteTask.USER_TYPE;

public class UserLogin extends BaseFragment implements View.OnClickListener {

    private TextView  email, password;
    private Button sign_up, login;
    private ShardPref shardPref;

    @Override
    protected int getFragmentTitle() {
        return R.string.user_login;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shardPref=ShardPref.getInstance(getActivity());
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        sign_up = view.findViewById(R.id.sign_up);
        login = view.findViewById(R.id.login);
        sign_up.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (email.getText().toString().trim().isEmpty()) {
                    email.setError(getString(R.string.err_field_empty));
                    return;
                }
                if (password.getText().toString().trim().isEmpty()) {
                    password.setError(getString(R.string.err_field_empty));
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(USERNAME, email.getText().toString());
                    jsonObject.put(PASSWORD, password.getText().toString());
                    jsonObject.put(USER_TYPE, ShardPref.TYPE_USER);
                    final ProgressDialog progressDialog=ProgressDialog.show(getActivity(),"",getString(R.string.loading));
                    ExecuteTask.postJsonObjectRequest(getActivity(), BASE + "login", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            if (response.optBoolean(STATUS)) {
                                shardPref.setLogged(ShardPref.TYPE_USER,response.optString(MESSAGE));
                                replaceFragment(new Import());
                                startActivity(SendFeedback.class);
                            }else {
                                showToast(response.optString(MESSAGE));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            showToast(R.string.network_error);

                        }
                    });

                } catch (Exception e) {

                }
                break;
            case R.id.sign_up:
                replaceFragment(new SignUp());
                break;
        }
    }
    @Override
    protected int getViewResource() {
        return R.layout.fragment_user_login;
    }
}
