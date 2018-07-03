package com.crm.test.vcrm_iam_test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zoho.accounts.clientframework.IAMClientSDK;
import com.zoho.accounts.clientframework.IAMErrorCodes;
import com.zoho.accounts.clientframework.IAMToken;
import com.zoho.accounts.clientframework.IAMTokenCallback;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button api = (Button) findViewById(R.id.api);
        api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout()
    {
        final IAMClientSDK sdk = IAMClientSDK.getInstance(getApplicationContext());
        new android.os.AsyncTask<String, String, String>()
        {
            @Override
            protected String doInBackground(String[] params)
            {
                sdk.revoke(new IAMClientSDK.OnLogoutListener() {
                    @Override
                    public void onLogoutSuccess() {
                       Log.i("ZVCRM","Logout done.");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onLogoutFailed() {
                        Log.i("ZVCRM","Logout Failed..");
                    }
                });
                return "success";
            }

            @Override
            protected void onPostExecute(String rr)
            {
            }
        }.execute();
    }

}
