package com.irne.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText4,editText5;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);

        editText5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(button);
                }
                return false;
            }
        });

        button = findViewById(R.id.button3);

        if(ParseUser.getCurrentUser() != null) {
           // ParseUser.logOut();
            HomeActivity();
        }

        button.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {

        ParseUser.logInInBackground(editText4.getText().toString(), editText5.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (editText4.getText().toString().equals("") || editText5.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity   .this, "E mail, Username and Password is required",
                            Toast.LENGTH_LONG, FancyToast.INFO, true).show();
                } else {

                    if (user != null && e == null) {

                        FancyToast.makeText(LoginActivity.this, ParseUser.getCurrentUser().getUsername() + " successfully logged in",
                                Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        HomeActivity();
                    } else {

                        FancyToast.makeText(LoginActivity.this, e.getMessage(),
                                Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }

    public void LayoutTapped (View view){


        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){

            e.getStackTrace();
        }

    }

    private void HomeActivity (){

        startActivity(new Intent(this,HomeActivity.class));
    }
}
