package me.nassimi.covid19_data_visualisation.models;

public class CountryItem {
    //   private int flag;
    private String countryName;
    private int TotalConfirmed,TotalRecovered,TotalDeaths;
    private int NewConfirmed,NewRecovered,NewDeaths;

    public CountryItem(String countryName, int totalConfirmed, int totalRecovered, int totalDeaths) {
        this.countryName = countryName;
        TotalConfirmed = totalConfirmed;
        TotalRecovered = totalRecovered;
        TotalDeaths = totalDeaths;
    }

    public CountryItem(String countryName, int totalConfirmed, int totalRecovered, int totalDeaths,
                       int newConfirmed, int newRecovered, int newDeaths) {
        this.countryName = countryName;
        TotalConfirmed = totalConfirmed;
        TotalRecovered = totalRecovered;
        TotalDeaths = totalDeaths;
        NewConfirmed = newConfirmed;
        NewRecovered = newRecovered;
        NewDeaths = newDeaths;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public int getTotalRecovered() {
        return TotalRecovered;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public int getNewRecovered() {
        return NewRecovered;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }
}
