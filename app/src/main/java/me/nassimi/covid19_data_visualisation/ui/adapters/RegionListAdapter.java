package me.nassimi.covid19_data_visualisation.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.models.CountryItem;


public class RegionListAdapter extends RecyclerView.Adapter <RegionListAdapter.RegionListViewHolder> {
    private ArrayList<CountryItem> regionList;
    private Context context;

    public RegionListAdapter(Context _context, ArrayList<CountryItem> _exampleList) {
        this.context = _context;
        this.regionList = _exampleList;
    }

    // --------------- ViewHolder ---------------
    public static class RegionListViewHolder extends RecyclerView.ViewHolder {
        public TextView _textView_RegionName,_textView_TotalConfirmed,_textView_TotalRecovered,_textView_TotalDeaths;


        public RegionListViewHolder(View itemView) {
            super(itemView);
            _textView_RegionName = itemView.findViewById(R.id.textView_RegionName);

            _textView_TotalConfirmed = itemView.findViewById(R.id.textView_TotalConfirmed);
            _textView_TotalRecovered = itemView.findViewById(R.id.textView_TotalRecovered);
            _textView_TotalDeaths = itemView.findViewById(R.id.textView_TotalDeaths);

        }

    }


    @Override
    public RegionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_item, parent, false);
        RegionListViewHolder countryViewHolder = new RegionListAdapter.RegionListViewHolder(v);
        return countryViewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(RegionListViewHolder holder, int position) {
        CountryItem currentItem = regionList.get(position);
//      holder.mImageView.setImageResource(currentItem.getmImageResource());

        holder._textView_RegionName.setText(currentItem.getCountryName());
        holder._textView_TotalConfirmed.setText(String.format("%,d",currentItem.getTotalConfirmed()));
        holder._textView_TotalRecovered.setText(String.format("%,d",currentItem.getTotalRecovered()));
        holder._textView_TotalDeaths.setText(String.format("%,d",currentItem.getTotalDeaths()));
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public void swapRegionListRecords(ArrayList<CountryItem> objects) {
        regionList.clear();
        regionList.addAll(objects);
        this.notifyDataSetChanged();

    }


}
