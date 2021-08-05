package com.company.nc_project.filter;

import com.company.nc_project.model.Country;

public class Filter {
    private Country country;
    private String recipe="";
    private Integer calories = Integer.MAX_VALUE;

    public Filter(Country country, String recipe, Integer calories) {
        this.country = country;
        this.recipe = recipe;
        this.calories = calories;
    }

    public Country getCountry() {
        return country;
    }

    public String getRecipe() {
        return recipe;
    }

    public Integer getCalories() {
        return calories;
    }
}
