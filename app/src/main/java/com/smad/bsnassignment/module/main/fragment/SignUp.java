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
import com.smad.bsnassignment.support.ExecuteTask;

import org.json.JSONObject;

import static com.smad.bsnassignment.support.ExecuteTask.BASE;
import static com.smad.bsnassignment.support.ExecuteTask.EMAIL;
import static com.smad.bsnassignment.support.ExecuteTask.MESSAGE;
import static com.smad.bsnassignment.support.ExecuteTask.NAME;
import static com.smad.bsnassignment.support.ExecuteTask.PASSWORD;
import static com.smad.bsnassignment.support.ExecuteTask.STATUS;

public class SignUp extends BaseFragment implements View.OnClickListener {
    private TextView name, email, password;
    private Button sign_up, login;

    @Override
    protected int getFragmentTitle() {
        return R.string.sign_up;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = view.findViewById(R.id.name);
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
            case R.id.sign_up:
                if (name.getText().toString().trim().isEmpty()) {
                    name.setError(getString(R.string.err_field_empty));
                    return;
                }
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
                    jsonObject.put(NAME, name.getText().toString());
                    jsonObject.put(EMAIL, email.getText().toString());
                    jsonObject.put(PASSWORD, password.getText().toString());
                    final ProgressDialog progressDialog=ProgressDialog.show(getActivity(),"",getString(R.string.loading));
                    ExecuteTask.postJsonObjectRequest(getActivity(), BASE + "register", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            showToast(response.optString(MESSAGE));
                            if (response.optBoolean(STATUS))
                                replaceFragment(new UserLogin());
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
            case R.id.login:
                replaceFragment(new UserLogin());
                break;
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_user_sign_up;
    }


}
