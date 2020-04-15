package com.example.michael.militaryarrangement;

import android.media.TimedText;

import java.security.PrivateKey;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Class Soldier is the class that hold the definition for this and that///
 */

public class Soldier {
    private static long myID;
    private String myUsername ;
    private String myPhone;
    private String myPassword;

    public Soldier(){

    }

    /**
     * Constructor Soldier
     * @param id is of type Long and holds the id of the soldier
     * @param username is of type int and holds the username of the soldier
     * @param phone is of type String and holds the phone number of the soldier
     * @param password is of type String and holds the password of soldier's username
     */
    public Soldier(long id , String username , String phone , String password)
    {
        myID = id;
        myUsername = username ;
        myPhone = phone;
        myPassword = password ;
    }

    //getters
    public long getMyID() {

        return this.myID;
    }

    public String getMyUsername() {

        return this.myUsername;
    }

    public String getMyPhone() {

        return this.myPhone;
    }

    public String getMyPassword() {

        return this.myPassword;
    }

    //setters
    public static void setMyID(long id){
        myID=id;
    }

    public void setMyUsername(String username) {
        this.myUsername = username;
    }

    public void setMyPhone(String phone) {
        this.myPhone = phone;
    }

    public void setMyPassword(String password) {
        this.myPassword = password;
    }

}
