package com.generation.HelloRESTWorld.controllers;

import com.generation.HelloRESTWorld.model.Student;
import com.generation.HelloRESTWorld.model.services.abstractions.StudentService;
import com.generation.HelloRESTWorld.model.services.implementations.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


//@restController: serve per segnalare a spring che questa sarà una classe CONTROLLER
//Il CONTROLLER avrà al suo interno delle funzioni per gestire le richieste dell'utente
@RestController
public class StudentController {

    //questo non è bello, perchè permettiamo che il controller dipende direttamente dalla classe studentServiceImpl
    private StudentService studentService;

    //andiamo a inserire l'annotations @Autowired che ci servirà
    @Autowired
    //creiamo il costruttore per il controller StudentService
    public StudentController(StudentService studentService){
        this.studentService =   studentService;
    }

    //ora andiamo ad usare l'annotations GetMapping poichè andrà ad indicare a spring il percorso che dovrà effettuare
    //nel caso di una richiesta da parte dell'utente.
    //in questo caso nella richiesta di : /student.
    //Risponderà solo alle richieste GET
    @GetMapping(value = "/student")
    //Iterable è la classe generale delle collection
    public Iterable<Student> getAllStudent() {

        Student s1 = new Student(1, "Mario", "Rossi", LocalDate.of(1990, 2, 20));
        Student s2 = new Student(2, "Luigi", "Morandi", LocalDate.of(1995, 5, 15));

        //IL NOME DELLE VARIABILI SONO IN MINUSCOLO!!!!!

        //andiamo a creare un collection in arraylist di studenti
        Collection<Student> students = new ArrayList<>();

        //andiamoad aggiungere gli studenti creati nell'ArrayList
        students.add(s1);
        students.add(s2);

        //torniamo la collection degli studenti
        return students;

    }
}
