package com.generation.HelloRESTWorld.model.services.implementations;

import com.generation.HelloRESTWorld.model.Student;
import com.generation.HelloRESTWorld.model.services.abstractions.StudentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

//usiamo l'annotations @Service per indicare questa come classe di Servzi
@Service
//implementiamo i l'interfaccia dei servizi degli studenti e quindi implementiamo i metodi presenti nell'interfaccia
public class StudentServiceImpl implements StudentService {


    @Override
    public Iterable<Student> getAllStudents() {
        return null;
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return Optional.empty();
    }

    @Override
    public Student create(Student s) {
        return null;
    }
}
