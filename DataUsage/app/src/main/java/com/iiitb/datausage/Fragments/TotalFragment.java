package com.iiitb.datausage.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.iiitb.datausage.Model.StaticDataModel;
import com.iiitb.datausage.Model.StaticVariables;
import com.iiitb.datausage.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TotalFragment extends Fragment
{
    TextView wifi;
    TextView mobileData;

    //Pie Chart Variables
    PieChart mChart;
    // we're going to display pie chart for Wifi and Mobile Data Usage
    private long[] yValues = {0,0};
    private String[] xValues = {"Mobile Usage", "Wifi Usage"};

    // colors for different sections in pieChart
    public static  final int[] MY_COLORS =
            {
                    Color.rgb(255,105,180), Color.rgb(0,191,255) ,Color.rgb(220,20,60), Color.rgb(65,105,225), Color.rgb(255,255,51)
            };

    public TotalFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_total, container, false);


        //Piechart code
        mChart = (PieChart) view.findViewById(R.id.totalPieChart);
        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
            {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(getActivity(), xValues[e.getXIndex()] + " is " + e.getVal() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        // setting sample Data for Pie Chart
        long wTotal = StaticDataModel.wifiRX + StaticDataModel.wifiTX;
        long mTotal = StaticDataModel.mobileRX + StaticDataModel.mobileTX;

        yValues[0] = mTotal;
        yValues[1] = wTotal;
        Log.d("Total Fragment", "Val: " + mTotal + " " + wTotal);
        setDataForPieChart();

        wifi = (TextView) view.findViewById(R.id.tx_label);
        mobileData = (TextView) view.findViewById(R.id.rx_label);


        wifi.setText(StaticVariables.dataConverter(wTotal));
        mobileData.setText(StaticVariables.dataConverter(mTotal));

        BarChart chart = (BarChart) view.findViewById(R.id.totalBarchart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(1400, 1400);
        chart.invalidate();

        return view;
    }
    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;
        long wTotal = StaticDataModel.wifiRX + StaticDataModel.wifiTX;
        long mTotal = StaticDataModel.mobileRX + StaticDataModel.mobileTX;

        yValues[0] = mTotal;
        yValues[1] = wTotal;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        long x = yValues[0]/(1024*1024);
        BarEntry v1e1 = new BarEntry(x, 0); // Jan
        valueSet1.add(v1e1);
        //BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        //valueSet1.add(v1e2);
        //BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        //valueSet1.add(v1e3);
        //BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        //valueSet1.add(v1e4);
        //BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        //valueSet1.add(v1e5);
        //BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        //valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        long y = yValues[1]/(1024*1024);
        BarEntry v2e1 = new BarEntry(y, 0); // Jan
        valueSet2.add(v2e1);
        //BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        //valueSet2.add(v2e2);
        //BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        //valueSet2.add(v2e3);
        //BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        //valueSet2.add(v2e4);
        //BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        //valueSet2.add(v2e5);
        //BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        //valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Data Transmitted");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Data Received");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("in MB");
        //xAxis.add("FEB");
        //xAxis.add("MAR");
        //xAxis.add("APR");
        //xAxis.add("MAY");
        //xAxis.add("JUN");
        return xAxis;
    }

    //Functions for Pie Chart
    public void setDataForPieChart()
    {
        long total = yValues[0] + yValues[1];
        double wifi = (yValues[1]*1.0)/total * 100;
        double mobileData = ((yValues[0] * 1.0) /total) * 100;

        Log.d("Pie Values", "" + wifi + mobileData);

        yValues[0] = Math.round(mobileData);
        yValues[1] = Math.round(wifi);


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);

        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }


    public class MyValueFormatter implements ValueFormatter
    {
        private DecimalFormat mFormat;

        public MyValueFormatter()
        {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
        {
            // write your logic here
            return mFormat.format(value) + "%"; // e.g. append a dollar-sign
        }
    }


}