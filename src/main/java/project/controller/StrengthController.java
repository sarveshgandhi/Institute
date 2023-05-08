package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.model.Student;
import project.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/strength")
public class StrengthController {

    private final StudentService studentService;

    @Autowired
    public StrengthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/school")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/class/{className}")
    public ResponseEntity<List<Student>> getStudentsFromClass(@PathVariable("className") Long className) {
        return ResponseEntity.ok(studentService.getStudentsFromClass(className));
    }
}
