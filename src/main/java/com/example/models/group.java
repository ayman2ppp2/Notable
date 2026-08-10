package com.example.models;

import java.util.List;

/**
 * Group
 */
class Group {
    private String name;
    private List<Integer> userIds;
   

    public Group(String name, List<Integer> userIds) {
        this.name = name;
        this.userIds = userIds;
       
    }
    
}