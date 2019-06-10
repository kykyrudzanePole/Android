package com.example.androidtask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class PictureDataFragment extends Fragment {
    View view;

    private PictureViewModel itemViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_data, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final PictureAdapter adapter = new PictureAdapter();
        recyclerView.setAdapter(adapter);

        itemViewModel = ViewModelProviders.of(this).get(PictureViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(@Nullable List<Picture> items) {
                adapter.setItems(items);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Picture deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Picture item) {
                File storageDirectory = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String description = item.getDescription();
                String fileName = item.getFileName();

                Log.d("desc", fileName);
                Log.d("desc", description);

                Bundle bundle = new Bundle();
                bundle.putString("description", description);
                bundle.putString("fileName", fileName);

                ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                itemDetailFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, itemDetailFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
        final MenuItem item = menu.findItem(R.id.delete_all_items);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_items:
                itemViewModel.deleteAllItems();
                Toast.makeText(getActivity(), "All items deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

