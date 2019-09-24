package com.irne.instaclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareTab extends Fragment implements View.OnClickListener {

    private ImageView imageView1;
    private EditText editText;
    private Button button2;

    private Bitmap recievedImage;
    public ShareTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_tab, container, false);

        imageView1 = view.findViewById(R.id.imageView3);
        editText = view.findViewById(R.id.editText11);
        button2= view.findViewById(R.id.button5);

        imageView1.setOnClickListener(ShareTab.this);
        button2.setOnClickListener(ShareTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.imageView3:

                if(Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else {

                    getImage();
                }
                break;
            case R.id.button5:

                if(recievedImage != null){

                    if(editText.getText().toString().equals("")){

                        FancyToast.makeText(getContext(),"Enter a Caption",
                                Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    }else {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        recievedImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("image.jpeg",bytes);
                        ParseObject parseObject = new ParseObject("Photos");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_caption",editText.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e == null){

                                    FancyToast.makeText(getContext(),"Image Uploaded Successfully",
                                            Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                }else {

                                    FancyToast.makeText(getContext(),e.getMessage(),
                                            Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }
                }else{

                    FancyToast.makeText(getContext(),"Choose a Image",
                            Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                }
                break;
        }
    }

    public void getImage() {

        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 2000){

            if(resultCode == Activity.RESULT_OK){

                try {

                    Uri selectedImage =data.getData();
                    String[] filepathcolumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor =getActivity().getContentResolver().query(selectedImage,filepathcolumn,null,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex =cursor.getColumnIndex(filepathcolumn[0]);
                    String picturepath =cursor.getString(columnIndex);
                    cursor.close();
                    recievedImage = BitmapFactory.decodeFile(picturepath);

                    imageView1.setImageBitmap(recievedImage);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }

    }
}
