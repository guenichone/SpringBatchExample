package orga.barrak.springbatch;

import orga.barrak.springbatch.monitoring.InstanceCounter;

public class Person extends InstanceCounter {

    private String lastName;
    private String firstName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return super.toString() + " firstName: " + firstName + ", lastName: " + lastName;
    }

}
