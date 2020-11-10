package me.nassimi.covid19_data_visualisation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.models.NewsItem;

public class AdviceItemAdapter extends RecyclerView.Adapter <AdviceItemAdapter.ListItemViewHolder> {
    private ArrayList<NewsItem> mExampleList;
    private Context context;

    public AdviceItemAdapter(Context _context,ArrayList<NewsItem> _exampleList) {
        this.context = _context;
        this.mExampleList = _exampleList;
    }


    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView _imageView_advice;
        public TextView _textView_adviceText;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            _imageView_advice = itemView.findViewById(R.id.imageView_advice);
            _textView_adviceText = itemView.findViewById(R.id.textView_adviceText);
        }

    }
    @Override
    public AdviceItemAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.advice_item, parent, false);
        AdviceItemAdapter.ListItemViewHolder evh = new AdviceItemAdapter.ListItemViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(AdviceItemAdapter.ListItemViewHolder holder, int position) {
        NewsItem currentItem = mExampleList.get(position);
        holder._imageView_advice.setImageResource(currentItem.getmImageResource());
        holder._textView_adviceText.setText(currentItem.getmText2());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}