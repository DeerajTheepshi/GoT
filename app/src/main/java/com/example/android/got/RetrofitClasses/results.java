package com.example.android.got.RetrofitClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class results {
    public String name;
    public String imageLink;
    public String spouse;
    public String culture;
    public String house;
    public List<String> titles;
    public String director;
    public String airDate;
    public Boolean male;
    public String _id;
    public int season;
    public List<String> locations;


    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getSpouse() {
        return spouse;
    }

    public String getCulture() {
        return culture;
    }

    public String getHouse() {
        return house;
    }

    public List<String> getTiles() {
        return titles;
    }

    public String getDirector() {
        return director;
    }

    public String getAirDate() {
        String apiDate =  airDate,reqDate = "";
        SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reqFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date inputDate = apiFormat.parse(apiDate);
            reqDate = reqFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  reqDate;
    }

    public String get_id() {
        return _id;
    }

    public List<String> getLocations() {
        return locations;
    }

    public Boolean getMale() {
        return male;
    }

    public int getSeason() {
        return season;
    }

    public results(String name, String imageLink, String spouse, String culture, String house, List<String> titles, String director, String airDate, Boolean male, String _id, int season, List<String> locations) {

        this.name = name;
        this.imageLink = imageLink;
        this.spouse = spouse;
        this.culture = culture;
        this.house = house;
        this.titles = titles;
        this.director = director;
        this.airDate = airDate;
        this.male = male;
        this._id = _id;
        this.season = season;
        this.locations = locations;
    }
}
