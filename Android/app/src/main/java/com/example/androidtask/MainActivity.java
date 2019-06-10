package com.example.androidtask;

import android.content.Intent;

import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    Fragment startFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFragment = new ReceiveDataFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, startFragment);
        transaction.commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setUpBottomNavigationView();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;

                        switch (menuItem.getItemId()){
                            case R.id.action_getData:
                                Log.d("click", "getData clicked");
                                selectedFragment = new ReceiveDataFragment();
                                break;
                            case R.id.action_showList:
                                Log.d("click", "getList clicked");
                                selectedFragment = new PictureDataFragment();
                                break;
                            default:
                                return false;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();

                        return true;
                    }
                }
        );
    }
}
