package com.example.androidtask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class ItemDetailFragment extends Fragment {

    TextView tv_description;
    ImageView imageView;

    File image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

        File storageDirectory = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);



        Bundle bundle = getArguments();

        String description = bundle.getString("description");
        String fileName = bundle.getString("fileName");
        imageView = view.findViewById(R.id.image);

        Log.d("exists", storageDirectory.toString());
        Log.d("exists", fileName);

        image = new File(storageDirectory.toString() + "/" + fileName + ".jpg");

        Log.d("image", image.toString());

        if(image.exists()){
            Log.d("exists", image.toString());
            imageView.setImageURI(Uri.fromFile(image));
            imageView.setRotation(imageView.getRotation() + 90);
            imageView.setScaleX((float) 1.95);
            imageView.setScaleY((float) 1.95);
        }
        else {
            Log.d("exists", "ne vizhy");
        }

        return view;
    }
}
