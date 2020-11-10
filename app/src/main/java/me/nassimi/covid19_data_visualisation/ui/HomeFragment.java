package me.nassimi.covid19_data_visualisation.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.VolleyApplication;
import me.nassimi.covid19_data_visualisation.models.CountryItem;
import me.nassimi.covid19_data_visualisation.models.GlobalStats;
import me.nassimi.covid19_data_visualisation.ui.adapters.CountryListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

public class HomeFragment extends Fragment {
    ArrayList<CountryItem> countriesList = new ArrayList<>();

    // WorldWide
    TextView _textView_WorldWide_TotalConfirmed,_textView_WorldWide_TotalRecovered,_textView_WorldWide_TotalDeaths;
    TextView _textView_WorldWide_NewConfirmed,_textView_WorldWide_NewRecovered,_textView_WorldWide_NewDeaths;

    // Last Update ( Time ) - Local Time
    TextView _textView_lastUpdate;
    CountryListAdapter adapter;
    GlobalStats globalStats ;
    View view;
    Button _btn_stats;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        instantiateComponents();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Toolbar

        // --------- Launch Stats Activity ---------
        _btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WorldWideChartActivity.class);
                startActivity(intent);
            }
        });



        // --------- Fetch Data & Setup the Adapter ---------
        RecyclerView rv = view.findViewById(R.id.rv_home_container);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //   old--     CountryListAdapter adapter=new CountryListAdapter(this.getActivity(),countriesList);
        adapter = new CountryListAdapter(this.getActivity(), countriesList);
        rv.setAdapter(adapter);

        // Fetch Data From API
        fetchCountriesData();
        fetchWorldWideData();

        // --------- Set The Last Update ( Time ) - Local Time ---------
        setLastUpdate();
    }







    //   ................. Methods .................

    private void fetchCountriesData() {
        JsonObjectRequest request = new JsonObjectRequest(
                "https://api.covid19api.com/summary",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            // Parsing Countries Data
                            countriesList = parse(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            // TODO: Return
                        adapter.swapCountrylistRecords(countriesList);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Check your internet connection and try again !", Toast.LENGTH_SHORT).show();

                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    private void fetchWorldWideData() {
        JsonObjectRequest request = new JsonObjectRequest(
                "https://api.covid19api.com/summary",
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // TODO: Parse the JSON
                        try {
                            globalStats = parseGlobalStats(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // TODO: Return
                        setUpGlobalStats(globalStats);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Check your internet connection and try again !", Toast.LENGTH_SHORT).show();

                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    private ArrayList<CountryItem> parse(JSONObject json) throws JSONException {
        ArrayList<CountryItem> _countriesList = new ArrayList<>();
        JSONArray countriesArray = json.getJSONArray("Countries");
        for (int i = 0; i < countriesArray.length(); i++) {
            JSONObject o = countriesArray.getJSONObject(i);
            CountryItem item = new CountryItem(
                    o.getString("Country"),
                    o.getInt("TotalConfirmed"),
                    o.getInt("TotalRecovered"),
                    o.getInt("TotalDeaths"),
                    o.getInt("NewConfirmed"),
                    o.getInt("NewRecovered"),
                    o.getInt("NewDeaths")
            );
            _countriesList.add(item);
        }
        return _countriesList;
    }

    private GlobalStats parseGlobalStats(JSONObject json) throws JSONException {
        JSONObject obj = json.getJSONObject("Global");
        GlobalStats _globalStats = new GlobalStats(
                (obj.getInt("TotalConfirmed")),
                (obj.getInt("TotalRecovered")),
                (obj.getInt("TotalDeaths")),
                (obj.getInt("NewConfirmed")),
                (obj.getInt("NewRecovered")),
                (obj.getInt("NewDeaths")));
//                (obj.getString("Date")));

        return _globalStats;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setUpGlobalStats(GlobalStats obj){

        _textView_WorldWide_TotalConfirmed.setText(String.format("%,d",obj.getTotalConfirmed()));
        _textView_WorldWide_TotalDeaths.setText(String.format("%,d",obj.getTotalRecovered()));
        _textView_WorldWide_TotalRecovered.setText(String.format("%,d",obj.getTotalDeaths()));

        _textView_WorldWide_NewConfirmed.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewConfirmed()));
        _textView_WorldWide_NewRecovered.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewRecovered()));
        _textView_WorldWide_NewDeaths.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewDeaths()));

    }

    private void setLastUpdate(){
        //create a date string.
        String date_n = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        _textView_lastUpdate.setText(date_n);
    }

    private void instantiateComponents(){
        //Toolbar
//        _toolbar_home = view.findViewById(R.id.toolbar_home);

        // Total Cases
        _textView_WorldWide_TotalConfirmed = view.findViewById(R.id.textView_WorldWide_TotalConfirmed);
        _textView_WorldWide_TotalRecovered = view.findViewById(R.id.textView_WorldWide_TotalRecovered);
        _textView_WorldWide_TotalDeaths = view.findViewById(R.id.textView_WorldWide_TotalDeaths);

        // New Cases
        _textView_WorldWide_NewConfirmed = view.findViewById(R.id.textView_WorldWide_NewConfirmed);
        _textView_WorldWide_NewRecovered = view.findViewById(R.id.textView_WorldWide_NewRecovered);
        _textView_WorldWide_NewDeaths = view.findViewById(R.id.textView_WorldWide_NewDeaths);

        // Last Update ( Time ) - Local Time
        _textView_lastUpdate = view.findViewById(R.id.textView_lastUpdate);

        _btn_stats = view.findViewById(R.id.btn_stats);
    }





}



