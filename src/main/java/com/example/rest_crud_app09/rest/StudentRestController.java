package com.example.rest_crud_app09.rest;


import com.example.rest_crud_app09.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")

public class StudentRestController {

    private List<Student> theStudents;


        // definim @PostConstructor ca sa incercam studentii nostri o singura data
        @PostConstruct
        public void loadData() {

            theStudents = new ArrayList<>();

            theStudents.add(new Student("Muntean", "Eugen"));
            theStudents.add(new Student("Ojog", "Maria"));
            theStudents.add(new Student("Gonzales", "Pedri"));


        }

    // definim endpointu-ul pentru students
        @GetMapping("/students")
        public List<Student> getStudents() {
            return theStudents;
        }

        // definim un endoint ca sa afisam toti studentii nostri dupa index

        
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        //verificam din nou studentID si dimensiunea listei
        if ((studentId >= theStudents.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student Id not found- " + studentId);

        }
        return theStudents.get(studentId);

    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex) {

            // crearea StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // returnarea ReponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {

        // crearea StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // returnarea ReponseEntity

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }



}
