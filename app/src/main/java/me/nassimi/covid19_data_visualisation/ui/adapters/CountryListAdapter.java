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


public class CountryListAdapter extends RecyclerView.Adapter <CountryListAdapter.CountryListViewHolder> {
    private ArrayList<CountryItem> countriesList;
    private Context context;

    public CountryListAdapter(Context _context,ArrayList<CountryItem> _exampleList) {
        this.context = _context;
        this.countriesList = _exampleList;
    }

    // --------------- ViewHolder ---------------
    public static class CountryListViewHolder extends RecyclerView.ViewHolder {
        public TextView _textView_CountryName,_textView_TotalConfirmed,_textView_TotalRecovered,_textView_TotalDeaths;
        public TextView _textView_NewConfirmed ,_textView_NewRecovered,_textView_NewDeaths;


        public CountryListViewHolder(View itemView) {
            super(itemView);
            _textView_CountryName = itemView.findViewById(R.id.textView_CountryName);

            _textView_TotalConfirmed = itemView.findViewById(R.id.textView_TotalConfirmed);
            _textView_TotalRecovered = itemView.findViewById(R.id.textView_TotalRecovered);
            _textView_TotalDeaths = itemView.findViewById(R.id.textView_TotalDeaths);

            _textView_NewConfirmed = itemView.findViewById(R.id.textView_NewConfirmed);
            _textView_NewRecovered = itemView.findViewById(R.id.textView_NewRecovered);
            _textView_NewDeaths = itemView.findViewById(R.id.textView_NewDeaths);
        }

    }


    @Override
    public CountryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        CountryListViewHolder countryViewHolder = new CountryListAdapter.CountryListViewHolder(v);
        return countryViewHolder;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(CountryListViewHolder holder, int position) {
        CountryItem currentItem = countriesList.get(position);
//      holder.mImageView.setImageResource(currentItem.getmImageResource());

        holder._textView_CountryName.setText(currentItem.getCountryName());

        holder._textView_TotalConfirmed.setText(String.format("%,d",currentItem.getTotalConfirmed()));
        holder._textView_TotalRecovered.setText(String.format("%,d",currentItem.getTotalRecovered()));
        holder._textView_TotalDeaths.setText(String.format("%,d",currentItem.getTotalDeaths()));

        holder._textView_NewConfirmed.setText("+" + String.format("%,d",currentItem.getNewConfirmed()));
        holder._textView_NewRecovered.setText("+" + String.format("%,d",currentItem.getNewRecovered()));
        holder._textView_NewDeaths.setText("+" + String.format("%,d",currentItem.getNewDeaths()));

    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public void swapCountrylistRecords(ArrayList<CountryItem> objects) {
        countriesList.clear();
        countriesList.addAll(objects);
        this.notifyDataSetChanged();

    }


}
