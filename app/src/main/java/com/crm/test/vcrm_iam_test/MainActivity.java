package com.crm.test.vcrm_iam_test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zoho.accounts.clientframework.IAMClientSDK;
import com.zoho.accounts.clientframework.IAMErrorCodes;
import com.zoho.accounts.clientframework.IAMToken;
import com.zoho.accounts.clientframework.IAMTokenCallback;

public class MainActivity extends AppCompatActivity {

    public String scopes = "Aaaserver.profile.Read,ZohoCRM.modules.ALL,ZohoCRM.settings.READ,ZohoCRM.users.READ,ZohoCRM.org.READ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("VCRM", "Starting..");
        login();

    }

    private void login()
    {
        IAMClientSDK.getInstance(getApplicationContext()).init(scopes);
        Log.i("VCRM","logging in ZVCRM");

        Intent targetIntent = new Intent(getApplicationContext(), LogoutActivity.class);

        IAMClientSDK sdk = IAMClientSDK.getInstance(getApplicationContext());
        if (sdk.isUserSignedIn()) {
            Log.i("VCRM","User already signed in..");
            sdk.getToken(scopes, getZVCRMTokenCallBack(targetIntent));
        } else {
            Log.i("VCRM","Presenting Login Screen..");
            sdk.presentLoginScreen(this, getZVCRMTokenCallBack(targetIntent), "flogout=true", Color.parseColor("#757575"));//No I18N
        }
    }

    private IAMTokenCallback getZVCRMTokenCallBack(final Intent targetIntent)
    {
        return new IAMTokenCallback() {

            @Override
            public void onTokenFetchInitiated() {
                Log.i("VCRM","token fetch initiated");
            }

            @Override
            public void onTokenFetchComplete(IAMToken iamToken) {
                Log.i("VCRM","token fetch success - with token = "+iamToken.getToken());					//No I18N
                startActivity(targetIntent);
            }

            @Override
            public void onTokenFetchFailed(IAMErrorCodes iamErrorCodes) {
                switch (iamErrorCodes)
                {
                    case user_cancelled:
                    case access_denied:
                        Log.i("VCRM",iamErrorCodes.getDescription());
                        PackageManager packageManager = getApplicationContext().getPackageManager();
                        Intent intent = packageManager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
                        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
                        getApplicationContext().startActivity(mainIntent);
                        break;
                    default:
                        Log.i("VCRM",iamErrorCodes.getDescription());
                }
            }
        };
    }
}
