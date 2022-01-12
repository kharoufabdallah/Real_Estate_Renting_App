package com.example.renting_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TenantHistoryAdapter extends RecyclerView.Adapter<TenantHistoryAdapter.ViewHolder>{

    ArrayList<RelTenantProp> tenants_props;

    String tenant_agency="";
    Context context;

        public TenantHistoryAdapter(ArrayList<RelTenantProp> tenants_props, Context activity){
        this.tenants_props = tenants_props;
        this.context = activity;
    }

    @NonNull
    @Override
    public TenantHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull TenantHistoryAdapter.ViewHolder holder, int position) {
        final RelTenantProp prop_list = tenants_props.get(position);

        holder.prop_city.setText(prop_list.getProp_city());
        holder.prop_id.setText(prop_list.getProp_id());
        holder.imgview.setImageResource(R.drawable.flag_czech_republic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tenants_props.size();
    }
}
