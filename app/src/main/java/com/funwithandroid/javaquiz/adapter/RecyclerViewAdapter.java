package com.funwithandroid.javaquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
//        switch (position) {
//            case 0:
//            holder.cardView.setCardBackgroundColor(Color.RED);break;
//            case 1:
//                holder.cardView.setCardBackgroundColor(Color.BLUE);break;
//            case 2:
//                holder.cardView.setCardBackgroundColor(Color.BLACK);break;
//            case 3:
//                holder.cardView.setCardBackgroundColor(Color.GRAY);break;
//            case 4:
//                holder.cardView.setCardBackgroundColor(Color.GREEN);break;
//        }
        switch (position){
            case 0:
                holder.constraintLayout.setBackgroundResource(R.drawable.card_background1);break;
            case 1:
                holder.constraintLayout.setBackgroundResource(R.drawable.card_background2);break;
            case 2:
                holder.constraintLayout.setBackgroundResource(R.drawable.card_background1);break;
            case 3:
                holder.constraintLayout.setBackgroundResource(R.drawable.card_background2);break;
            case 4:
                holder.constraintLayout.setBackgroundResource(R.drawable.card_background1);break;
        }
    }
    @Override
    public int getItemCount() {
        return quetionlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView quiznumber;
        private TextView recylescore;
        private CardView cardView;
        private ConstraintLayout constraintLayout;
        private  ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            quiznumber = itemView.findViewById(R.id.quiznumber);
            recylescore=itemView.findViewById(R.id.recylescore);
            cardView=itemView.findViewById(R.id.cardview);
            constraintLayout=itemView.findViewById(R.id.constraintlayout);
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
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
