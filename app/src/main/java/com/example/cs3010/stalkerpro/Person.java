package com.example.cs3010.stalkerpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Conrad on 4/22/2015.
 */
public class Person {

    // main data fields
    public UUID puuid;
    public String name;
    public String created_at;
    public String modified_at;

    // make an empty profile
    public Person() {
        puuid = UUID.randomUUID();
        name = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        created_at = dateFormat.format(date);
        modified_at = dateFormat.format(date);
    }

    public Person clone(Person person)
    {
        Person newPerson = new Person();
        newPerson.puuid = person.puuid;
        newPerson.name = person.name;
        newPerson.created_at = person.created_at;
        newPerson.modified_at = person.modified_at;
        return newPerson;
    }


}
