package project.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.respository.StudentRepository;

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
        return studentRepository.promoteSchool();
    }
}
