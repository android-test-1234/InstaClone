package com.irne.instaclone;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText editText,editText1,editText2,editText3,editText4;

    private Button button;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

        editText = view.findViewById(R.id.editText6);
        editText1 = view.findViewById(R.id.editText7);
        editText2 = view.findViewById(R.id.editText8);
        editText3 = view.findViewById(R.id.editText9);
        editText4 = view.findViewById(R.id.editText10);

        button = view.findViewById(R.id.button4);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("ProfileName".toString()) != null) {
            editText.setText(parseUser.get("ProfileName").toString());
        }
        if(parseUser.get("Bio".toString()) != null) {
            editText1.setText(parseUser.get("Bio").toString());

        }if(parseUser.get("Work".toString()) != null) {
            editText2.setText(parseUser.get("Work").toString());

        }if(parseUser.get("Hobbies".toString()) != null) {
            editText3.setText(parseUser.get("Hobbies").toString());

        }if(parseUser.get("Sports".toString()) != null) {
            editText4.setText(parseUser.get("Sports").toString());

        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseUser.put("ProfileName",editText.getText().toString());
                parseUser.put("Bio",editText1.getText().toString());
                parseUser.put("Work",editText2.getText().toString());
                parseUser.put("Hobbies",editText3.getText().toString());
                parseUser.put("Sports",editText4.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            FancyToast.makeText(getContext(),"Profile Info updated",
                                    Toast.LENGTH_LONG,FancyToast.INFO,true).show();
                        }
                        else {

                            FancyToast.makeText(getContext(),e.getMessage(),
                                    Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                        progressDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

}
