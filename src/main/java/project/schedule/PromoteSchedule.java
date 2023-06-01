package project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.model.Student;
import project.respository.StudentRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PromoteSchedule {

    private final StudentRepository studentRepository;

    @Autowired
    public PromoteSchedule(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Scheduled(
            fixedRate = 31536000000L //(365L * 24 * 60 * 60 * 1000) every year in millis
//            cron = "*/10 * * * * *" // to test
    )
    public String run() {
        log.info("Students promoted by scheduled job");

        // finding out students to be removed
        Set<Student> graduatingStudents = studentRepository.findAll()
                .stream()
                .filter(student -> !"12".equals(student.getClazz()))
                .collect(Collectors.toSet());

        // increasing the class level
        graduatingStudents
                .forEach(student -> student.setClazz(String.valueOf(Long.parseLong(student.getClazz()) + 1)));

        // removing graduating batch of students
        studentRepository.deleteAll();

        studentRepository.saveAll(graduatingStudents);
        return String.format("Students have been promoted for the year %s", LocalDate.now().getYear());
    }
}
