package com.csvbuilderfactory;

import com.census.ICSVBuilder;
import com.opencsvbuilder.OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return new OpenCSVBuilder();
    }
}
