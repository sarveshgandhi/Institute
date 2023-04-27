package project.respository;

import org.springframework.stereotype.Repository;
import project.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private static final AtomicReference<List<Student>> school = new AtomicReference<>(new ArrayList<>());

    public void addAll(List<Student> students) {
        school.get().addAll(students);
    }

    public int getStrength() {
        return school.get().size();
    }

    public List<Student> getAllStudents() {
        return school.get();
    }

    public void add(Student student) {
        school.get().add(student);
    }

    public String promoteSchool() {
        // keeping in mind, a student is not being readmitted, they're being promoted
        // finding out students to be removed
        Set<Student> graduatingStudents = school.get()
                .stream()
                .filter(student -> "12".equals(student.getClazz()))
                .collect(Collectors.toSet());

        // increasing the class level
        school.get()
                .forEach(student -> student.setClazz(String.valueOf(Long.parseLong(student.getClazz())+1)));

        // removing graduating batch of students
        school.get().removeAll(graduatingStudents);
        return String.format("Students have been promoted for the year %s", LocalDate.now().getYear());
    }
}
