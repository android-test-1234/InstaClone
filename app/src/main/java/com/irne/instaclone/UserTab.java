package com.irne.instaclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment {

    private ListView listView;


    private ArrayList arrayList;

    private ArrayAdapter arrayAdapter;
    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab, container, false);

        listView = view.findViewById(R.id.listView);

        arrayList =new ArrayList();


        final TextView textView = view.findViewById(R.id.textView);

        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
        ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();

        parseUserParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseUserParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if(e == null){

                    if(users.size() > 0){

                        for(ParseUser parseUser : users){

                            arrayList.add(parseUser.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        textView.animate().alpha(0f).setDuration(200);
                        listView.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        return view;
    }

}
