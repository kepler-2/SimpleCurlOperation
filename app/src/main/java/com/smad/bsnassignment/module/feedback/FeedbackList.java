package com.smad.bsnassignment.module.feedback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smad.bsnassignment.BaseActivity;
import com.smad.bsnassignment.R;
import com.smad.bsnassignment.support.ExecuteTask;
import com.smad.bsnassignment.support.ShardPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.smad.bsnassignment.support.ExecuteTask.BASE;
import static com.smad.bsnassignment.support.ExecuteTask.DATA;
import static com.smad.bsnassignment.support.ExecuteTask.FEEDBACK;
import static com.smad.bsnassignment.support.ExecuteTask.MESSAGE;
import static com.smad.bsnassignment.support.ExecuteTask.STATUS;
import static com.smad.bsnassignment.support.ExecuteTask.USER_ID;

public class FeedbackList extends BaseActivity {

    private ShardPref shardPref;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<Feedback> listUsers=new ArrayList<>();
    private UsersRecyclerAdapter usersRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackButton();
        shardPref = ShardPref.getInstance(getApplicationContext());
        initViews();
    }

    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        textViewName.setText("ADMIN");
        final ProgressDialog progressDialog = ProgressDialog.show(FeedbackList.this, "", getString(R.string.loading));
        ExecuteTask.getJsonObjectRequest(getApplicationContext(), BASE + "getFeedback", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                if (response.optBoolean(STATUS)) {
                    JSONArray jsonArray=response.optJSONArray(DATA);
                    JSONObject jsonObject;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.optJSONObject(i);
                        listUsers.add(new Feedback(jsonObject.optString(USER_ID),
                                jsonObject.optString(FEEDBACK)));
                    }
                    initObjects();
                    Log.e("",response.optString(DATA));
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
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);


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
        return R.layout.activity_feedback_list;
    }
}
