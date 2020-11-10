package me.nassimi.covid19_data_visualisation.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.VolleyApplication;
import me.nassimi.covid19_data_visualisation.models.CountryItem;
import me.nassimi.covid19_data_visualisation.models.GlobalStats;
import me.nassimi.covid19_data_visualisation.ui.adapters.RegionListAdapter;
import java.util.ArrayList;

public class CountryFragment extends Fragment {
    private CountryCodePicker ccp;
    private GlobalStats countryStats;
    private View view;
    private RegionListAdapter adapter ;
    private ArrayList<CountryItem> regionList = new ArrayList<>();
    static final String MA = "MA";

    // WorldWide
    private TextView _textView_WorldWide_TotalConfirmed, _textView_WorldWide_TotalRecovered, _textView_WorldWide_TotalDeaths;
    private TextView _textView_WorldWide_NewConfirmed, _textView_WorldWide_NewRecovered, _textView_WorldWide_NewDeaths;


    public CountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_country, container, false);

        ccp = view.findViewById(R.id.ccp);

        instantiateComponents();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Toast.makeText(getContext(),"Test ......." + regionList.get(2).getCountryName(),Toast.LENGTH_LONG).show();


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                // --------- Fetch & Show Global Country Selected Stats's

                String _countryCode = ccp.getSelectedCountryNameCode();
                fetchCountriesData(_countryCode);



                // --------- Fetch Data & Setup the Adapter ---------
//                RecyclerView rv = view.findViewById(R.id.rv_region_container);
//                rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//                adapter = new RegionListAdapter(this.getActivity(), text);
//                fetchRegionDataTest();


            }
        });




    }


    private void setupAdapter(){

            RecyclerView rv = view.findViewById(R.id.rv_region_container);
            rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            adapter = new RegionListAdapter(this.getActivity(), regionList);
            rv.setAdapter(adapter);
            fetchRegionData();


    }



    // Test ..
    private void fetchRegionDataTest(){
        JsonObjectRequest request = new JsonObjectRequest(
                "https://services3.arcgis.com/hjUMsSJ87zgoicvl/arcgis/rest/services/Covid_19/FeatureServer/0/query?where=1%3D1&outFields=RegionFr,Cases,Deaths,Recoveries,Nom_R%C3%A9gion_FR&returnGeometry=false&outSR=4326&f=json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            // Parsing Countries Data
                            parseRegionDataTest(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // TODO: Swap DATA

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: "
                                + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    private void parseRegionDataTest( JSONObject obj) throws  JSONException{
        JSONArray a = obj.getJSONArray("features");
        JSONObject o = a.getJSONObject(4).getJSONObject("attributes");
        String test = o.getString("RegionFr");
        Toast.makeText(getContext(),"Test .." + test,Toast.LENGTH_LONG).show();
    }

    // .. Test !



    // Region ..
    private void fetchRegionData(){
        JsonObjectRequest request = new JsonObjectRequest(
                "https://services3.arcgis.com/hjUMsSJ87zgoicvl/arcgis/rest/services/Covid_19/FeatureServer/0/query?where=1%3D1&outFields=RegionFr,Cases,Deaths,Recoveries,Nom_R%C3%A9gion_FR&returnGeometry=false&outSR=4326&f=json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            // Parsing Countries Data
                            regionList = parseRegionData(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // TODO: Swap DATA
                        adapter.swapRegionListRecords(regionList);

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

    private ArrayList<CountryItem> parseRegionData(JSONObject obj) throws JSONException{
        ArrayList<CountryItem> _regionList = new ArrayList<>();

        JSONArray country = obj.getJSONArray("features");
        for(int i=0 ; i < country.length() ; i++){
            JSONObject o = country.getJSONObject(i).getJSONObject("attributes");
            CountryItem currentRegion = new CountryItem(
                    o.getString("RegionFr"),
                    o.getInt("Cases"),
                    o.getInt("Recoveries"),
                    o.getInt("Deaths")

            );

            _regionList.add(currentRegion);
        }

        return _regionList;
    }
    // .. Region !


    // Global Country Stats ..
    private void fetchCountriesData(final String countryCode) {
        JsonObjectRequest request = new JsonObjectRequest(
                "https://api.covid19api.com/summary",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            // Parsing Countries Data
                            countryStats = parse(jsonObject, countryCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // TODO: Return
                        setUpCountryStats(countryStats);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getActivity(), "Unable to fetch data: "
//                                + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Check your internet connection and try again !", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    private GlobalStats parse(JSONObject obj, String countryCode) throws JSONException {
        GlobalStats _countryStats = null;
        JSONArray country = obj.getJSONArray("Countries");
        for (int i = 0; i < country.length(); i++) {

            JSONObject o = country.getJSONObject(i);

            if ( (o.getString("CountryCode").equals(countryCode)) && countryCode.equals(MA)) {
                _countryStats = new GlobalStats(
                        (o.getInt("TotalConfirmed")),
                        (o.getInt("TotalRecovered")),
                        (o.getInt("TotalDeaths")),
                        (o.getInt("NewConfirmed")),
                        (o.getInt("NewRecovered")),
                        (o.getInt("NewDeaths")));
                setupAdapter();
            } else if (o.getString("CountryCode").equals(countryCode)) {

                _countryStats = new GlobalStats(
                        (o.getInt("TotalConfirmed")),
                        (o.getInt("TotalRecovered")),
                        (o.getInt("TotalDeaths")),
                        (o.getInt("NewConfirmed")),
                        (o.getInt("NewRecovered")),
                        (o.getInt("NewDeaths")));

            }

        }
        return _countryStats;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setUpCountryStats(GlobalStats obj) {
        _textView_WorldWide_TotalConfirmed.setText(String.format("%,d",obj.getTotalConfirmed()));
        _textView_WorldWide_TotalDeaths.setText(String.format("%,d",obj.getTotalRecovered()));
        _textView_WorldWide_TotalRecovered.setText(String.format("%,d",obj.getTotalDeaths()));

        _textView_WorldWide_NewConfirmed.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewConfirmed()));
        _textView_WorldWide_NewRecovered.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewRecovered()));
        _textView_WorldWide_NewDeaths.setText(getString(R.string.new_decorator) + String.format("%,d",obj.get_NewDeaths()));

        //Test Data Region


    }

    private void instantiateComponents() {
        _textView_WorldWide_TotalConfirmed = view.findViewById(R.id.ctextView_WorldWide_TotalConfirmed);
        _textView_WorldWide_TotalRecovered = view.findViewById(R.id.ctextView_WorldWide_TotalRecovered);
        _textView_WorldWide_TotalDeaths = view.findViewById(R.id.ctextView_WorldWide_TotalDeaths);
        // New Cases
        _textView_WorldWide_NewConfirmed = view.findViewById(R.id.ctextView_WorldWide_NewConfirmed);
        _textView_WorldWide_NewRecovered = view.findViewById(R.id.ctextView_WorldWide_NewRecovered);
        _textView_WorldWide_NewDeaths = view.findViewById(R.id.ctextView_WorldWide_NewDeaths);


    }
    // .. Global Country Stats !

}