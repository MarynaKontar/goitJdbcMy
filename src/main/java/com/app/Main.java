package com.app;

/**
 * Created by User on 23.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        DAO dao = new DataBaseDAO();

        Person person = new Person();
        person.setAge(30);
        person.setName("Denis");
        System.out.println("----CREATE-----");
        dao.create(person);

        Person readPerson = dao.read(1);
        System.out.println("----READ----");
        System.out.println(readPerson);

        Person personUpdate = new Person();
        personUpdate.setName("Boris");
        personUpdate.setAge(45);
        Person oldPerson = dao.update(1,personUpdate);
        System.out.println("----UPDATE----");
        System.out.println("----OLD person----");
        System.out.println(oldPerson);
        System.out.println("----CURRENT person----");
        Person currenPerson = dao.read(1);
        System.out.println(currenPerson);

        System.out.println("----DELETE----");
        dao.delete(1);
        // System.out.println("----current person:-----");
        // Person currentPersonAfterDelete=dao.read(1);
        // System.out.println(currentPersonAfterDelete);


    }
}
