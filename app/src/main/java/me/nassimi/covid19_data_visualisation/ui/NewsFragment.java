package me.nassimi.covid19_data_visualisation.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.models.NewsItem;
import me.nassimi.covid19_data_visualisation.ui.adapters.NewsListAdapter;


public class NewsFragment extends Fragment {

    NewsListAdapter adapter;


    ArrayList<NewsItem> newsList= new ArrayList<>();


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView rv= view.findViewById(R.id.rv_news_container);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter=new NewsListAdapter(this.getActivity(),newsList);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsList.add(new NewsItem( "Morocco Has Taken Series of Measures to Mitigate Covid-19 Impact on Tourism ", "https://www.mapnews.ma/en/actualites/regional/morocco-has-taken-series-measures-mitigate-covid-19-impact-tourism-amf"));
        newsList.add(new NewsItem("The National Coronavirus Response Plan Aims to Reduce Reproduction Rate to 0.7% - Health Ministry","https://www.mapnews.ma/en/actualites/general/national-coronavirus-response-plan-aims-reduce-reproduction-rate-07-health"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            public void onItemClick(int position) {
                NewsItem newsItem = newsList.get(position);


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(newsItem.getUrl()));
                startActivity(i);
            }
        });

    }

}