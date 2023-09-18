package com.generation.HelloRESTWorld.model.repositories.implementations;


import com.generation.HelloRESTWorld.model.Student;
import com.generation.HelloRESTWorld.model.repositories.abstractions.StudentRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class JDBCStudentRepository implements StudentRepository {

    public final static String JDBC_URL = "jdbc:mysql://localhost:3306/db_example";
    public final static String JDBC_USER = "root";
    public final static String JDBC_PASSWORD = "root";

    public final static String ALL_STUDENTS = "select id,firstname,lastname,birthdate from student";

    //creiamo un'altra variabile per il metodo findbyid con una nuova query al suo interno
    public final static String FIND_STUDENTS_BY_ID = "select firstname,lastname,birthdate from student where id = ?";

    //creiamo una query per la creazione di un nuovo studente. Avremo la tabello con numero autoincrementato e quindi non inseriamo il valore id
    public static final String CREATE_STUDENT = "insert into student (firstname, lastname, birthdate) values (?,?,?)";

//METODO GETALLSTUDENTS CON TRY LUNGO
//    @Override
//    public Iterable<Student> getAllStudents() {
//
//        Connection con = null;
//        Statement st = null;
//        ResultSet res = null;
//
//        Collection<Student> students = new ArrayList<>();
//        try {
//            //creiamo la connessione
//            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//            //stiamo creando un oggetto di classe che mi permetta di eseguire la query in sql
//            st = con.createStatement();
//            //con il ResultSet Andiamo ad inviare la quesry
//            res = st.executeQuery(ALL_STUDENTS);
//
//            //per leggere il risutato della query della variabile res
//            //abbiamo bisogno di un ciclo while
//            //la condizione sarà il .next() che sta ad indicare fin quando incontri un linea cicla.
//            while (res.next()) {
//
//                //qui andiamo ad indicare dove prendere i vari dettagli per all'interno della query(id,firstname,lastname,birthday)
//                long id = res.getLong("id");
//                String firstName = res.getString("firstName");
//                String lastName = res.getString("lastName");
//                LocalDate birthdate = res.getDate("birthdate").toLocalDate();
//
//                //qui andiamo a creare un o ggetto di tipo student inserendo i dati precedente presi
//                Student student = new Student(id, firstName, lastName, birthdate);
//                //inseriamo lo studente nella lista
//                students.add(student);
//
//            }
//
//            return students;
//
//        } catch (SQLException e) {
//
//            throw new RuntimeException(e);
//
//        } finally {
//            //all'interno del finally andemo a scrivere i try per la chiusura la connessione con il DB
//            // dobbiamo scriveri ogni try per ogni condizione
//            try {
//                if (res != null) {
//                    res.close();
//
//                }
//            } catch (SQLException e) {
//
//            }
//            try {
//                if (st != null) {
//                    st.close();
//
//                }
//            } catch (SQLException e) {
//
//            }
//            try {
//                if (con != null) {
//                    con.close();
//
//                }
//            } catch (SQLException e) {
//
//            }
//
//        }
//
//    }

    @Override
    public Iterable<Student> getAllStudents() {


        Collection<Student> students = new ArrayList<>();
        //inizializzeremo le nostre variabili nel try. TRY WITH RESOURCES
        //possiamo inizializzare solo variabile che hanno implementato l'interfaccia AutoClosable, che avrà sempre il metodo close()
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement st = con.createStatement();
             ResultSet res = st.executeQuery(ALL_STUDENTS);
        ) {


            //per leggere il risutato della query della variabile res
            //abbiamo bisogno di un ciclo while
            //la condizione sarà il .next() che sta ad indicare fin quando incontri un linea cicla.
            while (res.next()) {

                //qui andiamo ad indicare dove prendere i vari dettagli per all'interno della query(id,firstname,lastname,birthday)
                long id = res.getLong("id");
                String firstName = res.getString("firstName");
                String lastName = res.getString("lastName");
                LocalDate birthdate = res.getDate("birthdate").toLocalDate();

                //qui andiamo a creare un o ggetto di tipo student inserendo i dati precedente presi
                Student student = new Student(id, firstName, lastName, birthdate);
                //inseriamo lo studente nella lista
                students.add(student);

            }
            return students;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Optional<Student> findStudentById(long id) {

        Collection<Student> students = new ArrayList<>();


        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement st = con.prepareStatement(FIND_STUDENTS_BY_ID);
        ) {

            //settiamo il primo ed unico parametro che ci arriverà nella richiesta
            st.setLong(1, id);
            //qui inseriamo un try con variabili che eseguirà la query passata
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {

                    //andiamo a togliere la riga seguente perchè già avremo l'id tramite la ricerca
                    //id = res.getLong("id");

                    String firstName = res.getString("firstName");
                    String lastName = res.getString("lastName");
                    LocalDate birthdate = res.getDate("birthdate").toLocalDate();

                }
                return Optional.empty();

            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public Student create(Student s) {

        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             //Statement.RETURN_GENERATED_KEYS serve peravvertire che dovrà riportare il valore creato con il nuovo studente
             PreparedStatement st = con.prepareStatement(CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS);
        ) {

            st.setString(1, s.getFirstName());
            st.setString(2, s.getLastName());
            st.setDate(3, Date.valueOf(s.getBirthdate()));

            //andiamo ad eseguire l'azione di creazione
            st.executeUpdate();
            //qui usiamo questo try per sapere il valore dell'id che è stato appena creato
            //quindi usiamo il resultset res e useremo il metodo della variabile st -> st.getGeneratedKeys();
            try (ResultSet res = st.getGeneratedKeys()) {
                //se trovo la prossima riga
                if (res.next()) {
                    //troviamo la colonna dell'id
                    long id = res.getLong(1);
                    s.setId(id);
                }

            }
            return s;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }


    }
}
