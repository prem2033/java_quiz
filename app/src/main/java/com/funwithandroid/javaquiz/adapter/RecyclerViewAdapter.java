package com.funwithandroid.javaquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.funwithandroid.javaquiz.R;
import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private OnItemClickListener mListener;
    private ArrayList<String > quetionlist;
    private Context context;
    public RecyclerViewAdapter(Context context, ArrayList<String> arrayList) {
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
        holder.quiznumber.setText(quetionlist.get(position));
    }
    @Override
    public int getItemCount() {
        return quetionlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView quiznumber;
        private  ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            quiznumber = itemView.findViewById(R.id.quiznumber);
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
