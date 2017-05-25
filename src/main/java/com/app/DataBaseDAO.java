package com.app;

import java.sql.*;

import com.mysql.jdbc.Driver;


/**
 * Created by User on 23.05.2017.
 */
public class DataBaseDAO implements DAO {
    /*
    1. DRIVER.JAR in classpath   //внизу прописаны два варианта регистрации, но сейчас можно уже не прописывать напрямую (автоматически происходит0
    2. Register Driver in program
    3. Use javax.sql.*
     */

    //1
//    static{
//    try{
//        Class.forName("com.mysql.jdbs.Driver");
//    } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//    }
//    }

    //1 второй вариант
//    static {
//        try {
//            DriverManager.registerDriver(new Driver());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    //CREATE - INSERT  into persons(name, age) values('Test', 25);
    public void create(Person p) {
        Connection con = null;
        try {
            con = getConnection();
            Statement statement = con.createStatement();
            String sql = "INSERT into persons(name, age) VALUES('" + p.getName() + "', " + p.getAge() + ")";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


    public Person read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM person WHERE ID="+id;
            ResultSet rs = statement.executeQuery(sql);//ссылка на курсор в БД
            Person person = new Person();
            while (rs.next()) {//есть ли еще данные? и сдвинули ли курсор
                String personName = rs.getString("name");
                int personAge = rs.getInt("age");
                person.setAge(personAge);
                person.setName(personName);
            }
            return person;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Person update(int id, Person person) {
        try (Connection con = getConnection()) {
            Person oldPerson = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE person set name=?, age=?, WHERE id=?");
            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setInt(3, id);
            ps.execute();
//           statement.executeUpdate("UPDATE person SET name=");
            return oldPerson;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {

        try(Connection con = getConnection()){
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM person WHERE id="+id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    Connection con=null;
    //Oracle - пакет
    //процедуры и ФУНКЦИИ
    //процедуры - это не void метод
    //функции - это методы с void
    //CollableStatement cb = con.prepareCall("");
    //cb.registerOutParameter();
    //вот он и вызывает и то и другое

    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/persons","root","111111");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/persons","root","111111");
    }
}

