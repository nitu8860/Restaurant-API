package com.example.Restaurant.API.service;

import com.example.Restaurant.API.exception.ResourceNotFoundException;
import com.example.Restaurant.API.model.Food;
import com.example.Restaurant.API.model.Restaurant;
import com.example.Restaurant.API.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoodByRestaurantId(Long restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId);
    }

    public Food createFood(Long restaurantId, Food food) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        food.setRestaurant(restaurant);
        return foodRepository.save(food);
    }

    public Food updateFood(Long restaurantId, Long id, Food food) {
        Food existingFood = getFoodByIdAndRestaurantId(id, restaurantId);
        existingFood.setName(food.getName());
        existingFood.setPrice(food.getPrice());
        return foodRepository.save(existingFood);
    }

    public void deleteFoodById(Long restaurantId, Long id) {
        Food food = getFoodByIdAndRestaurantId(id, restaurantId);
        foodRepository.delete(food);
    }

    private Food getFoodByIdAndRestaurantId(Long id, Long restaurantId) {
        return foodRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found with id " + id + " for restaurant with id " + restaurantId));
    }

}
