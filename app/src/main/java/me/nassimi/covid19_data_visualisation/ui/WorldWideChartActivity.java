package me.nassimi.covid19_data_visualisation.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.nassimi.covid19_data_visualisation.R;
import me.nassimi.covid19_data_visualisation.VolleyApplication;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class WorldWideChartActivity extends AppCompatActivity {
//    private Button _btn_list;

    public static final int[] CUSTOM = {
            rgb("#00E676"), rgb("#FB3C63")
    };


    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList totalCases;
    private TextView _textView_lastUpdate;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_wide_chart);
        pieChart = findViewById(R.id.pieChart);

        _textView_lastUpdate = findViewById(R.id.textView_lastUpdate);
        setLastUpdate();

        fetchChartData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chart);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }

    @Override
    public void onBackPressed(){
        finish();
    }
    private void fetchChartData() {
        JsonObjectRequest request = new JsonObjectRequest(
                "https://api.covid19api.com/summary",
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            plot(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void plot(JSONObject obj) throws JSONException {

        totalCases = new ArrayList<>();

//        totalCases.add(new PieEntry((float)obj.getJSONObject("Global").getInt("TotalConfirmed"), "TotalConfirmed"));
        totalCases.add(new PieEntry((float)obj.getJSONObject("Global").getInt("TotalRecovered"), ""));
        totalCases.add(new PieEntry((float)obj.getJSONObject("Global").getInt("TotalDeaths"), ""));

        pieDataSet = new PieDataSet(totalCases,"");

        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Disable legend & description
        pieChart.getLegend().setEnabled(false);   // Hide the legend
        pieChart.setDescription(null);

        // For Label Entry ( Total Deaths ect .. ) on the Chart
//        pieChart.setEntryLabelColor(getColor(R.color.textColorSecondary));
        pieChart.setUsePercentValues(true);

        // pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());

        pieDataSet.setColors(CUSTOM);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(getColor(R.color.colorPrimaryDark));
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setSliceSpace(8f);
        pieChart.setExtraOffsets( 12f,  12f,  12f,  12f);

    }

    private void setLastUpdate(){
        //create a date string.
        String date_n = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        _textView_lastUpdate.setText(date_n);
    }


}

