package com.alash.sms.controller;

import com.alash.sms.entity.Student;
import com.alash.sms.repository.StudentRepository;
import com.alash.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/")

public class StudentController {
 Logger logger = Logger.getLogger(StudentController.class.getName());
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentService.getAllStudents();
    }

//    @GetMapping("/students/new")
//    public Student createStudentForm() {
//
//        Student student = new Student();
//        return student;
//    }

    @PostMapping("/students")
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping("/students/{id}")
    public Student editStudentForm(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
       logger.info("Inside method update");
        Student existingStudent = studentService.getStudentById(id);
        logger.info(existingStudent.getEmail());
        logger.info(student.getEmail());
        logger.info("Got student........");
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        return studentService.updateStudent(existingStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        studentService.deleteStudent(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);


    }


}
