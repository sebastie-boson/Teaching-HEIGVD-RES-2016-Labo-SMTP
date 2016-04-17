package model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebbos on 16.04.2016.
 */
public class Group {
    private List<Person> personsList;

    public Group() {
        personsList = new ArrayList<Person>();
    }

    public void addPerson(Person person) {
        personsList.add(person);
    }

    public Person getPersonAt(int index) {
        return personsList.get(index);
    }

    public Person removePersonAt(int index) {
        return personsList.remove(index);
    }

    public int getGroupSize() {
        return personsList.size();
    }
}
