package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
        return ResponseEntity.ok(studentService.addBatchStudentsAsJson(file));
    }

    @GetMapping("/promote")
    public ResponseEntity<String> promote() {
        return ResponseEntity.ok(promoteSchedule.run());
    }
}
