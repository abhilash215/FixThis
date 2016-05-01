package com.example.abhiu.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.entity.HealthCare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhilash on 3/11/2016.
 */
public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.RestaurantViewHolder> {

    List<HealthCare> restaurantList = new ArrayList<>();
  static   OnItemClickListener mItemClickListener;

    public interface  OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.restaurant_item, null);
        return new RestaurantViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        HealthCare restaurant = restaurantList.get(position);
        holder.txtName.setText(restaurant.getName());
        holder.descp.setText(restaurant.getAddress());
        holder.phone.setText(restaurant.getTel());
        List<List<String>> categoryLabels = restaurant.getCategoryLabels();
        List<String> categoryItem = categoryLabels.get(0);
        String category = categoryItem.get(categoryItem.size() - 1);
        //String category = restuarant.getCategoryLabels().get(0).get(restuarant.getCategoryLabels().get(0).size() - 1);
        if (category != null) {
            holder.restaurantType.setText(category);
        }
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView descp;
        TextView restaurantType;
        TextView phone;
        public RestaurantViewHolder(View itemView) {
            super(itemView);
            Initialiseview(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(itemView,getPosition());
                    }
                }

            });


            }

        private void Initialiseview(View itemView) {
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            descp = (TextView) itemView.findViewById(R.id.desc);
            restaurantType = (TextView) itemView.findViewById(R.id.restaurantType);
            phone=(TextView)itemView.findViewById(R.id.phone);
        }
    }




    public void setRestaurantList(List<HealthCare> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

   }
