package com.funwithandroid.javaquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.funwithandroid.javaquiz.R;
import com.funwithandroid.javaquiz.recylerData.RecylerData;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private OnItemClickListener mListener;
    private ArrayList<RecylerData> quetionlist;
    private Context context;
    public RecyclerViewAdapter(Context context, ArrayList<RecylerData> arrayList) {
        this.context = context;
        this.quetionlist=arrayList;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewfortype, parent, false);
        return new ViewHolder(view,mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        RecylerData recylerData=quetionlist.get(position);
        holder.quiznumber.setText(recylerData.getType());
        holder.recylescore.setText(recylerData.getHighscore());
    }
    @Override
    public int getItemCount() {
        return quetionlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView quiznumber;
        private TextView recylescore;
        private  ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            quiznumber = itemView.findViewById(R.id.quiznumber);
            recylescore=itemView.findViewById(R.id.recylescore);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
       // void onDeleteBtnClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
