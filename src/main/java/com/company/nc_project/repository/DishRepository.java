package com.company.nc_project.repository;

import com.company.nc_project.filter.Filter;
import com.company.nc_project.model.Client;
import com.company.nc_project.model.Country;
import com.company.nc_project.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface DishRepository extends CrudRepository<Dish, UUID> {
    Set<Dish> findAll();

    Set<Dish> findAllByCountry(Country country);

    Set<Dish> findAllByCountryAndRecipeContainingAndAndCaloriesIsLessThan(Country country, String recipe, Integer calories);

    Set<Dish> getAllByClientContaining(Client client);

    default Set<Dish> findAllByFilter(Filter filter){
        return findAllByCountryAndRecipeContainingAndAndCaloriesIsLessThan(filter.getCountry(), filter.getRecipe(), filter.getCalories());
    }
}
