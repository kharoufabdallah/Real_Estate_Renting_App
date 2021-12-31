    package com.example.renting_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PropertyAdpter extends RecyclerView.Adapter<PropertyAdpter.ViewHolder> {

    Property[] registered_props;
    Context context;

    public PropertyAdpter(Property[] registered_props,UI_Activity activity){
        this.registered_props = registered_props;
        this.context = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.prop_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Property prop_list = registered_props[position];
        holder.pro_name_tv.setText(prop_list.getCity()); // no name of property - replaced by city
        holder.pro_date_tv.setText(Integer.toString(prop_list.getConst_year())); // as a date
        holder.imgview.setImageResource(prop_list.getImage_view());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, prop_list.getStatus(), Toast.LENGTH_SHORT).show();
                // list of prop added for instance statically just to test ya man
             //   here is the actual work of each prop in hte list -- when click on the card item
            }
        });
    }

    @Override
    public int getItemCount() {
        return registered_props.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView pro_name_tv;
        TextView pro_date_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview= itemView.findViewById(R.id.imageview_home);
            pro_date_tv= itemView.findViewById(R.id.textdate_home);
            pro_name_tv= itemView.findViewById(R.id.textName_home);

        }
    }
}
