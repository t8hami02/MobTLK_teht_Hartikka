package com.example.lab4_1_mobtlk;

import java.io.Serializable;

public class AlueetClass implements Serializable {

    String nimi ="";
    String countyCode = "";
    String parentArea ="";
    int id = 0;


    public AlueetClass(String nimiJson, String countyCodeJson, String parentAreaJson, int idJson) {
        nimi = nimiJson;
        id = idJson;
        countyCode = countyCodeJson;
        parentArea = parentAreaJson;
    }

    public String getNimi() {
        return nimi;
    }

    public int getId() {
        return id;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public String getParentArea() {
        return parentArea;
    }
}
