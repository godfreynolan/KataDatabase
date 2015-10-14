package com.riis.katadatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    EditText usernameField, passwordField, emailField;
    private static String REGISTERED = "true";
    Button loginButton;
    private UserOperations userDBoperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (EditText) findViewById (R.id.username_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        emailField = (EditText) findViewById(R.id.email_field);

        //UserPreferences uPref = new UserPreferences();
        //uPref.saveSharedPreferences(this, REGISTERED);

        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(v);
            }
        });
    }


    public void onSubmit(View v) {

        userDBoperation = new UserOperations(this);
        userDBoperation.open();

        User regUser = userDBoperation.addUser(usernameField.getText().toString(), emailField.getText().toString());

        userDBoperation.close();


        // implicit intent
        Intent intent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putString("Username", usernameField.getText().toString());
        bundle.putString("Password", passwordField.getText().toString());
        bundle.putString("Email", emailField.getText().toString());
        intent.setAction("com.riis.katadatabase.IntentReceiverActivity");

        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
