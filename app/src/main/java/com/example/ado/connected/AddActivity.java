package com.example.ado.connected;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ado on 2016-11-03.
 */

public class  AddActivity extends FragmentActivity {

    private final String LOG_TAG = AddActivity.class.getSimpleName();
    private TextView mNameTextView, mEmailTextView, mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView = (TextView) findViewById(R.id.connectedName);
        mPhoneTextView = (TextView) findViewById(R.id.connectedPhone);
        mEmailTextView = (TextView) findViewById(R.id.connectedEmail);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(ConnectedContract.ConnectedColumns.CONNECTED_NAME, mNameTextView.getText().toString());
                    values.put(ConnectedContract.ConnectedColumns.CONNECTED_PHONE, mPhoneTextView.getText().toString());
                    values.put(ConnectedContract.ConnectedColumns.CONNECTED_EMAIL, mEmailTextView.getText().toString());
                    Uri returned = mContentResolver.insert(ConnectedContract.URI_TABLE, values);
                    Log.d(LOG_TAG, "record id is returned is " + returned.toString());
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Please ensure your data is valid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValid() {
        if (mNameTextView.getText().toString().length() == 0 ||
                mPhoneTextView.getText().toString().length() == 0 ||
                mEmailTextView.getText().toString().length() == 0) {
            return false;
        }else{
            return true;
        }
    }

    private boolean someDataEntered() {
        if (mNameTextView.getText().toString().length() > 0 ||
                mPhoneTextView.getText().toString().length() > 0 ||
                mEmailTextView.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(someDataEntered()) {
            ConnectedDialog dialog = new ConnectedDialog();
            Bundle args = new Bundle();
            args.putString(ConnectedDialog.DIALOG_TYPE, ConnectedDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        }else{
            super.onBackPressed();
        }
    }
}