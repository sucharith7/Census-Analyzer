package com.censusdata;

import com.opencsv.bean.CsvBindByName;

public class CensusData {
    @CsvBindByName(column = "SrNo",required = true)
    private int srNo;

    @CsvBindByName(column = "StateName",required = true)
    private String stateName;

    @CsvBindByName(column = "TIN",required = true)
    private int tin;

    @CsvBindByName(column = "StateCode",required = true)
    private String stateCode;

    public CensusData() {

    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getTIN() {
        return tin;
    }

    public void setTIN(int tin) {
        this.tin = tin;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return  "srNo='" + srNo + '\'' +
                ", stateName='" + stateName + '\'' +
                ", tin='" + tin + '\'' +
                ", stateCode='" + stateCode + '\''
                +"\n";
    }
}