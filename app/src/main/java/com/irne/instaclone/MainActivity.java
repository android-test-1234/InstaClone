package com.irne.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText,editText1,editText2;

    private Button button,button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((androidx.appcompat.widget.Toolbar) findViewById(R.id.dashboard_toolbar));

        ParseInstallation.getCurrentInstallation().saveInBackground();

        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);
        editText2 = findViewById(R.id.editText3);

        editText2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                onClick(button1);
                }
                return false;
            }
        });

        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button2);


        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.logOut();
            HomeActivity();
        }

        button1.setOnClickListener(MainActivity.this);


        button.setOnClickListener(MainActivity.this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button2:
                if(editText.getText().toString().equals("") || editText1.getText().toString().equals("")
                        || editText2.getText().toString().equals("")){

                    FancyToast.makeText(MainActivity.this,"E mail, Username and Password is required",
                            Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                }else {
                    final ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(editText1.getText().toString());
                    parseUser.setEmail(editText.getText().toString());
                    parseUser.setPassword(editText2.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Signing up " + editText1.getText().toString());
                    progressDialog.show();
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                FancyToast.makeText(MainActivity.this, parseUser.getUsername() + " is successfully signed up",
                                        Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                editText.setText("");
                                editText1.setText("");
                                editText2.setText("");
                                HomeActivity();

                            } else {

                                FancyToast.makeText(MainActivity.this, e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                editText.setText("");
                                editText1.setText("");
                                editText2.setText("");
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.button:

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
        }
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
