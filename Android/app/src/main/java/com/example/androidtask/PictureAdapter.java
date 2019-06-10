package com.example.androidtask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ItemHolder> {
    private List<Picture> items = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Picture currentItem = items.get(position);
        holder.tv_id.setText(String.valueOf(currentItem.getId()));
        holder.tv_fileName.setText(currentItem.getFileName());
        holder.tv_description.setText(currentItem.getDescription());
        holder.tv_longitude.setText(String.valueOf(currentItem.getLongitude()));
        holder.tv_latitude.setText(String.valueOf(currentItem.getLatitude()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Picture> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public Picture getItemAt(int position){
        return items.get(position);
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tv_fileName;
        private TextView tv_description;
        private TextView tv_longitude;
        private TextView tv_latitude;
        private TextView tv_id;

        public ItemHolder(View itemView){
            super(itemView);
            tv_fileName = itemView.findViewById(R.id.text_view_fileName);
            tv_description = itemView.findViewById(R.id.text_view_description);
            tv_longitude = itemView.findViewById(R.id.text_view_longitude);
            tv_latitude = itemView.findViewById(R.id.text_view_latitude);
            tv_id = itemView.findViewById(R.id.text_view_id);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(items.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Picture item);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
