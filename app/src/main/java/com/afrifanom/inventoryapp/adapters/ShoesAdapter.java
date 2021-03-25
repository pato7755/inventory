package com.afrifanom.inventoryapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.application.AfrifaApplication;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.ShoeModel;
import com.afrifanom.inventoryapp.ui.view.ViewShoesViewModel;
import com.afrifanom.inventoryapp.utilities.UtilityManager;

import java.util.List;


public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.MyViewHolder> {

    private List<ShoeModel> list;
    private Context context;
    private ViewShoesViewModel viewModel = new ViewShoesViewModel();
    UtilityManager utilityManager = new UtilityManager();


    public ShoesAdapter() {
    }

    public ShoesAdapter(List<ShoeModel> list, Context context) {
        this.list = list;
        this.context = context;

    }


//    public ShoesAdapter(List<ShoeModel> list) {
//        this.list = list;
//    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View view;
        private final TextView idTextView;
        private final TextView brandTextView;
        private final TextView sizeTextView;
        private final TextView colorTextView;
        private final TextView priceTextView;
        private final ImageButton editButton;
        private final ImageButton deleteButton;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            idTextView = view.findViewById(R.id.shoe_id_textview);
            brandTextView = view.findViewById(R.id.brand_textview);
            sizeTextView = view.findViewById(R.id.size_textview);
            colorTextView = view.findViewById(R.id.color_textview);
            priceTextView = view.findViewById(R.id.price_textview);
            editButton = view.findViewById(R.id.edit_button);
            deleteButton = view.findViewById(R.id.delete_button);

            view.setOnClickListener(this);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("recyclerview clicked");

            String id = idTextView.getText().toString();
//            String name = brandTextView.getText().toString();


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoe_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ShoeModel modelObject = list.get(position);

        System.out.println("shoe ID: " + modelObject.getId());

        holder.idTextView.setText(modelObject.getId());
        holder.brandTextView.setText(modelObject.getBrand());
        holder.sizeTextView.setText(String.format("Size %s", modelObject.getSize()));
        holder.colorTextView.setText(modelObject.getColor());
        holder.priceTextView.setText(String.format("GHS %s", modelObject.getPrice()));

        holder.editButton.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("id", modelObject.getId());
            bundle.putString("brand", modelObject.getBrand());
            bundle.putString("size", modelObject.getSize());
            bundle.putString("color", modelObject.getColor());
            bundle.putString("price", modelObject.getPrice());
            Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_modify, bundle);

        });

        holder.deleteButton.setOnClickListener(view -> {

            ResponseModel responseModel;

            viewModel.deleteShoe(modelObject.getId(), utilityManager.getSharedPreference(AfrifaApplication.getContext(), "USER_ID"), position);

//            System.out.println("responseModel: " + responseModel);

//            if (responseModel != null) {
//
//                if (responseModel.getResponseCode().equals("100")) {
//                    Toast.makeText(AfrifaApplication.getContext(), responseModel.getResponseMessage(), Toast.LENGTH_LONG).show();
//                    removeAt(position);
//                } else {
//                    Toast.makeText(AfrifaApplication.getContext(), responseModel.getResponseMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            } else {
//                Toast.makeText(AfrifaApplication.getContext(), "An error occurred", Toast.LENGTH_LONG).show();
//            }
        });


    }

//
//    public void removeAt(int position) {
//
//        if (delete == 1) {
//
//            list.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, list.size());
//            delete = 0;
//        }
//    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}

//utilities.getSharedPreference(MainApplication.getContext(), Utilities.USER_ID)
