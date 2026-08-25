package com.example.models;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * task
 */
 @Entity
 @Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean done;

    @Column
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable= false)
    private User owner;

    public Task(){
    }
    public Task(String content, boolean done,User owner){
        this.content= content;
        this.done = done;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }

    public User getOwner(){
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setContent(String content) {
        this.content = content;
    }
}