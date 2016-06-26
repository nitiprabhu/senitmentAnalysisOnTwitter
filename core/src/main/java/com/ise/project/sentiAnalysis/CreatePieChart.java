package com.ise.project.sentiAnalysis;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Slice;

import java.util.List;

/**
 * Created by India on 11-May-16.
 */
public class CreatePieChart {
    public static com.googlecode.charts4j.PieChart getPieChart(String titleName, List<Integer> listValues)
    {
        Slice s1 = Slice.newSlice(listValues.get(0), Color.newColor("CACACA"), "Negaative", "Negative");
        Slice s2 = Slice.newSlice(listValues.get(1), Color.newColor("DF7417"), "Neutral",
                "Neutral");
        Slice s3 = Slice.newSlice(listValues.get(2), Color.newColor("951800"), "Positive",
                "Positive");
        com.googlecode.charts4j.PieChart pieChart = GCharts.newPieChart(s1, s2, s3);
        pieChart.setThreeD(true);
        pieChart.setTitle(titleName,Color.RED,20);
        pieChart.setSize(720,360);
        return pieChart;
    }
}
