package com.example.daniel.laligalive.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniel.laligalive.R;

/**
 * Created by Daniel on 08/12/2015.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private String[] items;
    private Context context;
    private int[] imatges;


    public RecyclerAdapter(Context context,int[] imatges, String[] items) {
        this.context = context;
        this.items= items;
        this.imatges = imatges;

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder itemsViewHolder, final int i) {
        itemsViewHolder.vTitle.setText(items[i]);
        itemsViewHolder.vImage.setImageResource(imatges[i]);
        final String item = items[i];
        final SharedPreferences sp = context.getSharedPreferences(item, Context.MODE_PRIVATE);
        itemsViewHolder.vCheck.setChecked(sp.getBoolean(item, false));
        itemsViewHolder.vCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(itemsViewHolder.vCheck.isChecked())
                {
                   SharedPreferences.Editor spe = sp.edit();
                    spe.putBoolean(item,true);
                    spe.commit();

                }
                else
                {
                    SharedPreferences.Editor spe = sp.edit();
                    spe.putBoolean(item, false);
                    spe.commit();
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected ImageView vImage;
        protected CheckBox vCheck;

        public MyViewHolder(View v) {
            super(v);
            vTitle = (TextView) v.findViewById(R.id.list_row_title);
            vImage = (ImageView) v.findViewById(R.id.list_row_image);
            vCheck = (CheckBox) v.findViewById(R.id.checkBox);
        }
    }
}