package com.company.nc_project.repository;

import com.company.nc_project.filter.Filter;
import com.company.nc_project.model.Client;
import com.company.nc_project.model.Country;
import com.company.nc_project.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface DishRepository extends CrudRepository<Dish, UUID> {
    Collection<Dish> findAll();

    Collection<Dish> findAllByCountry(Country country);

    Collection<Dish> findAllByCountryAndRecipeContainingAndCaloriesIsLessThan(Country country, String recipe, Integer calories);

    Collection<Dish> getAllByClientContaining(Client client);

    default Collection<Dish> findAllByFilter(Filter filter){
        return findAllByCountryAndRecipeContainingAndCaloriesIsLessThan(filter.getCountry(), filter.getRecipe(), filter.getCalories());
    }
}
