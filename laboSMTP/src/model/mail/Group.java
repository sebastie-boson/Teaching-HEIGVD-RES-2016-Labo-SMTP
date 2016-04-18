package model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a group of persons (objects from the class Person).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class Group {
    private List<Person> personsList;

    /**
     * constructor of the class Group
     */
    public Group() {
        personsList = new ArrayList<Person>();
    }

    /**
     * add a person to the group
     *
     * @param person the person to add
     */
    public void addPerson(Person person) {
        personsList.add(person);
    }

    /**
     * get the person at the specified index of the group
     *
     * @param index index specified
     * @return the person in the group
     */
    public Person getPersonAt(int index) {
        return personsList.get(index);
    }

    /**
     * remove a person in the group at the specified index
     *
     * @param index index specified
     * @return the removed person
     */
    public Person removePersonAt(int index) {
        return personsList.remove(index);
    }

    /**
     * get the number of persons in the group
     *
     * @return the number of persons
     */
    public int getGroupSize() {
        return personsList.size();
    }
}
