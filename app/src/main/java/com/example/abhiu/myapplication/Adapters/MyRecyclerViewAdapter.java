package com.example.abhiu.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.abhiu.myapplication.R;

/**
 * Created by abhiu on 4/19/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{

    private Context mContext;
    OnItemClickListener mItemClickListener;
    private String[] mDataset={"hello", "hello1"};

    public MyRecyclerViewAdapter(Context context,AdapterView.OnItemClickListener mItemClickListener) {
        this.mContext = context;
        //this.mItemClickListener = mItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view= View.inflate(mContext, R.layout.cardview_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.vtext.setText("data");
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public interface  OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView vtext;

        public ViewHolder(View itemView) {
            super(itemView);
            vtext=(TextView)itemView.findViewById(R.id.cat_text);
        }
    }


}
