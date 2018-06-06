package com.smad.bsnassignment.module.feedback;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smad.bsnassignment.BaseActivity;
import com.smad.bsnassignment.R;
import com.smad.bsnassignment.module.main.fragment.Import;
import com.smad.bsnassignment.support.ExecuteTask;
import com.smad.bsnassignment.support.ShardPref;

import org.json.JSONObject;

import static com.smad.bsnassignment.support.ExecuteTask.BASE;
import static com.smad.bsnassignment.support.ExecuteTask.FEEDBACK;
import static com.smad.bsnassignment.support.ExecuteTask.MESSAGE;
import static com.smad.bsnassignment.support.ExecuteTask.PASSWORD;
import static com.smad.bsnassignment.support.ExecuteTask.STATUS;
import static com.smad.bsnassignment.support.ExecuteTask.USERNAME;
import static com.smad.bsnassignment.support.ExecuteTask.USER_ID;
import static com.smad.bsnassignment.support.ExecuteTask.USER_TYPE;

public class SendFeedback extends BaseActivity {

    private ShardPref shardPref;
    private EditText feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackButton();
        shardPref=ShardPref.getInstance(getApplicationContext());
        feedback=findViewById(R.id.feed);
        (findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback.getText().toString().trim().isEmpty()){
                    feedback.setError(getString(R.string.err_field_empty));
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(USER_ID, shardPref.getUserId());
                    jsonObject.put(FEEDBACK, feedback.getText().toString());
                    final ProgressDialog progressDialog=ProgressDialog.show(SendFeedback.this,"",getString(R.string.loading));
                    ExecuteTask.postJsonObjectRequest(getApplicationContext(), BASE + "insertFeedback", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            if (response.optBoolean(STATUS)) {
                               feedback.setText("");
                            }
                            showToast(response.optString(MESSAGE));
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
            }
        });
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            shardPref.logout();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_send_feedback;
    }
}
