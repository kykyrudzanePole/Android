package com.example.androidtask;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class ReceiveDataFragment extends Fragment {

    private PictureViewModel viewModel;

    View view;
    private FusedLocationProviderClient client;

    public static int count = 0;

    String fileName;
    double latitude;
    double longitude;
    EditText description;

    Button buttonForTakePicture;
    String pathToFile;
    ImageView imageView;
    TextView showLocation;
    Button addPictureInDB;

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "Permissions";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receive_data, container, false);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        userInterfaceInitialization();

        verifyPermissions();

        Toast.makeText(getActivity(), "Permissions Granted", Toast.LENGTH_SHORT).show();

        return view;
    }

        private void userInterfaceInitialization(){


        showLocation = view.findViewById(R.id.location);

            addPictureInDB = view.findViewById(R.id.addPictureInDB);
            addPictureInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
        description = view.findViewById(R.id.description);

            buttonForTakePicture = view.findViewById(R.id.buttonForTakePicture);
            buttonForTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPictureTakeAction();
                getLocationClick();
            }
        });
        imageView = view.findViewById(R.id.imageView);
    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions" );
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};

        if(ContextCompat.checkSelfPermission(
                getActivity().getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getActivity().getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getActivity().getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getActivity().getApplicationContext(), permissions[3]) == PackageManager.PERMISSION_GRANTED){

            return;
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    public void getLocationClick() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Log.d("get", getActivity().toString());


        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){

                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    showLocation.setText("longitude = " + Double.toString(longitude) + " latitude = " + Double.toString(latitude));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

            private void dispatchPictureTakeAction() {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePicture.resolveActivity(getActivity().getPackageManager()) != null){
                    File photoFile = null;
                    photoFile = createPhotoFile();

                    if(photoFile != null){
                        pathToFile = photoFile.getAbsolutePath();
                        Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.androidtask.fileprovider", photoFile);
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePicture, REQUEST_CODE);
                    }
        }
    }

    private File createPhotoFile(){
        count++;
        fileName = "photo" + " " + count;
        File storageDirectory = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        Log.d("storage", storageDirectory.toString());

            image = new File(storageDirectory + "/" + fileName + ".jpg");
            Log.d("image", image.toString());


        return image;
    }

    public void saveItem() {
        String FileName = fileName;
        String Description = description.getText().toString();
        double Latitude = latitude;
        double Longitude = longitude;

        if(Description.trim().isEmpty()){
            Toast.makeText(getActivity(), "Please, insert description", Toast.LENGTH_SHORT).show();
            return;
        }
        Picture item = new Picture(FileName, Description, Latitude, Longitude);
        viewModel.insert(item);

        Toast.makeText(getActivity(), "Picture was added", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PictureViewModel.class);
    }
}
