package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String addAllStudents(@RequestParam("file") MultipartFile file) {
        return studentService.addBatchStudentsAsJson(file);
    }

    @PostMapping("/admit")
    public String admitStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/promote")
    public String promote() {
        // noted a student is not being re-admitted, they're being promoted
        return promoteSchedule.run();
    }
}
