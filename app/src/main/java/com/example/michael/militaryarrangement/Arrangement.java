package com.example.michael.militaryarrangement;

import java.sql.Time;

/**
 * Class Arrangement is the class that hold the definition for this and that///
 */

public class Arrangement {
    private static long myID ;
    private String myUsername;
    private Time myTimeOfStart;
    private Time myTimeOfEnd ;
    private String myCheck ;


    public Arrangement() {

    }

    /**
     * Constructor Arrangement
     * @param id is of type Long and holds location_id of the arrangement
     * @param username is of type String and holds the username of the soldier that do the arrangement
     * @param TimeOfStart is of type Time and holds the start time of the arrangement
     * @param TimeOfEnd is of type Time and holds the end time of the arrangement
     * @param Check is of type String and holds the primary key of the arrangement
     */
    public Arrangement (long id , String username , Time TimeOfStart , Time TimeOfEnd , String Check)
    {
        myID = id ;
        myUsername = username ;
        myTimeOfStart = TimeOfStart ;
        myTimeOfEnd = TimeOfEnd ;
        myCheck = Check ;
    }

    //getters

    public long getMyID() {
        return this.myID;
    }

    public String getMyUsername() {
        return this.myUsername;
    }

    public Time getMyTimeOfStart() {
        return this.myTimeOfStart;
    }

    public Time getMyTimeOfEnd() {
        return this.myTimeOfEnd;
    }

    public String getMyCheck () {
        return this.myCheck;
    }

    //setters

    public static void setMyID(long id){
        myID=id;
    }

    public void setMyUsername(String username) {
        this.myUsername = username;
    }

    public void setMyTimeOfStart(Time TimeOfStart) {
        this.myTimeOfStart = TimeOfStart;
    }

    public void setMyTimeOfEnd(Time TimeOfEnd) {
        this.myTimeOfEnd = TimeOfEnd;
    }

    public void setMyCheck (String Check)
    {
        this.myCheck = Check ;
    }

}
