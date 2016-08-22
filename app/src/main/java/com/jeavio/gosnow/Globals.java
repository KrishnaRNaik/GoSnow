package com.jeavio.gosnow;

import com.jeavio.gosnow.Model.User;

/**
 * Created by Krishna on 8/17/2016.
 */
public class Globals {
    private static Globals instance;

    // Global variable
    private User user;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public void setUser(User curUser ){
        this.user=curUser;
    }
    public User getUser(){
        if(this.user == null)
            this.user = new User();

        return this.user;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
