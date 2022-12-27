package com.android.example.bakingapp15;

public class Ingredients {
    public Double quantity;
    public String  measure;
    public String ingredient;

    Ingredients(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure    = measure;
        this.ingredient = ingredient;
    }
}
