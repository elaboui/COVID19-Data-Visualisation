package me.nassimi.covid19_data_visualisation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.models.NewsItem;

public class NewsListAdapter extends RecyclerView.Adapter <NewsListAdapter.NewsListViewHolder> {
        private ArrayList<NewsItem> mExampleList;
        private Context context;


    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

//    public NewsListAdapter(Context _context, ArrayList<ListItem> _exampleList) {
//        this.context = _context;
//        this.mExampleList = _exampleList;
//    }
    public NewsListAdapter(Context _context, ArrayList<NewsItem> _exampleList) {
        this.context = _context;
        this.mExampleList = _exampleList;
    }


    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
//            public ImageView mImageView;
            public TextView mTextView1;
//            public TextView mTextView2;
            public NewsListViewHolder(View itemView , final OnItemClickListener listener) {
                super(itemView);
//                mImageView = itemView.findViewById(R.id.imageView);
                mTextView1 = itemView.findViewById(R.id.textView);
//                mTextView2 = itemView.findViewById(R.id.textView2);

                itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int position;
                        if (listener != null && (position = NewsListViewHolder
                                .this.getAdapterPosition()) != -1) {
                            listener.onItemClick(position);
                        }
                    }
                });
            }

        }
        @Override
        public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsListViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false), this.mListener);
        }
        @Override
        public void onBindViewHolder(NewsListViewHolder holder, int position) {
            NewsItem currentItem = mExampleList.get(position);
//            holder.mImageView.setImageResource(currentItem.getmImageResource());
            holder.mTextView1.setText(currentItem.getmText1());
//            holder.mTextView2.setText(currentItem.getmText2());
        }
        @Override
        public int getItemCount() {
            return mExampleList.size();
        }
    }