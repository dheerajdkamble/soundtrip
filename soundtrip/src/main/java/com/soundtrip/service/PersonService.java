package com.soundtrip.service;

import java.util.List;

import com.soundtrip.dto.Person;

/**
 * Service to handle Persons.
 */
public interface PersonService {
    List<Person> getAllPersons();

    void addPerson(Person person);

    void deletePerson(int id);
}
