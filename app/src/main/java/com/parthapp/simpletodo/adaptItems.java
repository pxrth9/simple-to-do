package com.parthapp.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//
public class adaptItems extends RecyclerView.Adapter<adaptItems.ViewHolder>{

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnClickListner{
        void OnItemClicked(int position);
    }

    ArrayList<String> list;
    OnLongClickListener longClickListener;
    OnClickListner clickListener;

    public adaptItems(ArrayList<String> list, OnLongClickListener longClickListener, OnClickListner clickListener) {
        this.list = list;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflater to inflate a view
        View todoView =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it inside a View Holder
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab the item at the position
        String item = list.get(position);
        //Bind the item into the specified view holder
        holder.bind(item);


    }

    //how many items are inthe list
    @Override
    public int getItemCount() {
        return list.size();
    }

    //Container to provide easy access to views that represent each row of the List

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update the view inside of the viewholder with the data of viewolder
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    clickListener.OnItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
