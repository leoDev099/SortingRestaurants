package com.example.leonardo.sortingrestaurants.model.events;

/**
 * Created by leonardo on 16/01/18.
 */

public class ToolbarEvent {

    public final String name;
    public final Boolean isBackButtonPresent;

    public ToolbarEvent(String name, Boolean isButtonBackPresent) {
        this.name = name;
        this.isBackButtonPresent = isButtonBackPresent;
    }
}
