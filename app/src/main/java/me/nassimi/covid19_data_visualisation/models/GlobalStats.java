package me.nassimi.covid19_data_visualisation.models;

public class GlobalStats {
    int _TotalConfirmed,_TotalRecovered,_TotalDeaths;
    int _NewConfirmed ,_NewRecovered,_NewDeaths;
    String _CountryCode;

    public String get_CountryCode() {
        return _CountryCode;
    }

    public GlobalStats(int _TotalConfirmed, int _TotalRecovered, int _TotalDeaths,
                       int _NewConfirmed, int _NewRecovered, int _NewDeaths) {
        this._TotalConfirmed = _TotalConfirmed;
        this._TotalRecovered = _TotalRecovered;
        this._TotalDeaths = _TotalDeaths;
        this._NewConfirmed = _NewConfirmed;
        this._NewRecovered = _NewRecovered;
        this._NewDeaths = _NewDeaths;
    }

    public int get_NewConfirmed() {
        return _NewConfirmed;
    }

    public int get_NewRecovered() {
        return _NewRecovered;
    }
    public int get_NewDeaths() {
        return _NewDeaths;
    }

//    String _LastUpdate;


    public int getTotalConfirmed() {
        return _TotalConfirmed;
    }

    public int getTotalRecovered() {
        return _TotalRecovered;
    }

    public int getTotalDeaths() {
        return _TotalDeaths;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public String getLastUpdate(){
//        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        LocalDate _lastUpdateDate = LocalDate.parse( _LastUpdate, pattern);
//        String s  = "Last update , " + _lastUpdateDate;
//        _LastUpdate = s;
//        return _LastUpdate ;
//    }
//


}
