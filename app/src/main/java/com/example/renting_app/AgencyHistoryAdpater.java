package com.example.renting_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AgencyHistoryAdpater  extends RecyclerView.Adapter<AgencyHistoryAdpater.ViewHolder> {

    ArrayList<RelAgencyProp> agencyProps;
    Context c;

    public AgencyHistoryAdpater(ArrayList<RelAgencyProp> tenants_props, Context activity){
        this.agencyProps = tenants_props;
        this.c = activity;
    }

    @NonNull
    @Override
    public AgencyHistoryAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tenant_history_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView prop_city;
        TextView prop_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.immmmmmmmm);
            prop_city =itemView.findViewById(R.id.prop_city_hist_ten);
            prop_id=itemView.findViewById(R.id.porp_id_ten_hist);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyHistoryAdpater.ViewHolder holder, int position) {
        final RelAgencyProp prop_list = agencyProps.get(position);

        holder.prop_city.setText(prop_list.getProp_city());
        holder.prop_id.setText(Integer.toString(prop_list.getProp_id()));
        holder.imgview.setImageResource(R.drawable.flag_vatican_city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return agencyProps.size();
    }
}
