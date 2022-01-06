    package com.example.renting_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

    public class PropertyAdpter extends RecyclerView.Adapter<PropertyAdpter.ViewHolder> implements Filterable {

    ArrayList<Property> registered_props;
    ArrayList<Property> props_list_filer;

    Context context;

    public PropertyAdpter(ArrayList<Property> registered_props,UI_Activity activity){
        this.registered_props = registered_props;
        this.context = activity;
        this.props_list_filer= new ArrayList<Property>(registered_props);
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

        final Property prop_list = registered_props.get(position);
        holder.pro_name_tv.setText(prop_list.getCity()); // no name of property - replaced by city
        holder.pro_date_tv.setText(Integer.toString(prop_list.getConst_year())); // as a date
        holder.imgview.setImageResource(prop_list.getImage_view());
        holder.pro_stat_tv.setText(prop_list.getStatus());
        holder.pro_bed_tv.setText(Integer.toString(prop_list.getBedroom_no()));
        holder.pro_surf_tv.setText(Double.toString(prop_list.getSurface_area()));
        holder.pro_price_tv.setText(Double.toString(prop_list.getRental_price()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Application is going to be opened ", Toast.LENGTH_SHORT).show();
                // list of prop added for instance statically just to test ya man
               //here is the actual work of each prop in hte list -- when click on the card item
                application_form_of_property(prop_list,holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return registered_props.size();
    }

        @Override
        public Filter getFilter() {
            return filter;
        }

        Filter filter = new Filter() {

        //runs in background
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
              ArrayList<Property> filtered_list = new ArrayList<>();

              if (constraint.toString().isEmpty()) {
                  filtered_list.addAll(props_list_filer);
              }
                  else {

                          for (Property prop: props_list_filer)
                          {
                              if(prop.getCity().toLowerCase().contains(constraint.toString().toLowerCase()))
                                  filtered_list.add(prop);
                              if(prop.getStatus().toLowerCase().contains(constraint.toString().toLowerCase()))
                                  filtered_list.add(prop);
                          }

              }
                  FilterResults fr = new FilterResults();
                  fr.values= filtered_list;

                return fr;
            }

            //runs in UI thread
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                registered_props.clear();
                registered_props.addAll((Collection<? extends Property>) results.values);
                notifyDataSetChanged();
            }
        };
        public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView pro_name_tv;
        TextView pro_date_tv;
        TextView pro_surf_tv;
        TextView pro_bed_tv;
        TextView pro_stat_tv;
        TextView pro_price_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview= itemView.findViewById(R.id.imageview_home);
            pro_date_tv= itemView.findViewById(R.id.textdate_home);
            pro_name_tv= itemView.findViewById(R.id.textName_home);
            pro_surf_tv= itemView.findViewById(R.id.textSurfaceArea_home);
            pro_bed_tv=itemView.findViewById(R.id.textViewBedNo_home);
            pro_stat_tv=itemView.findViewById(R.id.textViewStatus_home);
            pro_price_tv=itemView.findViewById(R.id.textViewRentalPrice_home);
        }
    }

    public void  application_form_of_property (Property property,ViewHolder holder)
    {
        Intent intent = new Intent(context,ApplicationForm.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("prop_img",holder.imgview.toString());
        intent.putExtra("prop_city",property.getCity());
        intent.putExtra("prop_date",Integer.toString(property.getConst_year()));
        intent.putExtra("prop_surface",Double.toString(property.getSurface_area()));
        intent.putExtra("prop_bed",Integer.toString(property.getBedroom_no()));
        intent.putExtra("prop_status",property.getStatus());
        intent.putExtra("prop_price",Double.toString(property.getRental_price()));
        intent.putExtra("prop_postal",property.getPostal_address());

        context.startActivity(intent);
    }
}
