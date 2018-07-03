package com.crm.test.vcrm_iam_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zoho.accounts.clientframework.IAMClientSDK;


public class ZCRMRedirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IAMClientSDK.getInstance(this).handleRedirection(this);
    }
}
