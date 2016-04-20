package com.example.abhiu.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhiu.myapplication.R;

/**
 * Created by abhiu on 4/19/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{

    private Context mContext;
    OnItemClickListener mItemClickListener;
    private String[] mDataset={"Road/Potholes", "Lights","Leakages","Garbage","General/Others"};



    public MyRecyclerViewAdapter(Context context) {
        this.mContext = context;
        //this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        //View view= View.inflate(mContext, R.layout.cardview_layout,parent);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.vtext.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    //interface to handle on card view click
    public interface  OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView vtext;

        public ViewHolder(final View itemView) {
            super(itemView);
            vtext=(TextView)itemView.findViewById(R.id.cat_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener!=null) mItemClickListener.onItemClick(v,getPosition());
                }
            });
        }
    }


}
