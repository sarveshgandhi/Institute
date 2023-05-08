package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.model.Student;
import project.schedule.PromoteSchedule;
import project.service.StudentService;

@RestController
public class AdmissionsController {

    private final StudentService studentService;
    private final PromoteSchedule promoteSchedule;

    @Autowired
    public AdmissionsController(StudentService studentService,
                                PromoteSchedule promoteSchedule) {
        this.studentService = studentService;
        this.promoteSchedule = promoteSchedule;
    }

    @PostMapping("/admitBatch")
    public ResponseEntity<String> addAllStudents(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(studentService.addBatchStudentsAsJson(file), HttpStatus.OK);
    }

    @PostMapping("/admit")
    public ResponseEntity<String> admitStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.OK);
    }

    @GetMapping("/promote")
    public ResponseEntity<String> promote() {
        return new ResponseEntity<>(promoteSchedule.run(), HttpStatus.OK);
    }
}
