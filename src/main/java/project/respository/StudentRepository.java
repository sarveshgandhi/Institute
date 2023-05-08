package project.respository;

import org.springframework.stereotype.Repository;
import project.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private static final AtomicReference<List<Student>> school = new AtomicReference<>(new ArrayList<>());
    private static Long admissions = 0L;

    public void addAll(List<Student> requestedStudents) {
        List<Student> students;
        synchronized (admissions) {
             students = requestedStudents.stream()
                    .map(student -> student.toBuilder()
                            .admissionNumber(++admissions)
                            .build())
                    .collect(Collectors.toList());
        }
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

        school.set(
                school.get()
                        .stream()
                        .filter(student -> !"12".equals(student.getClazz())) // removing graduating batch of students
                        .map(student -> student.toBuilder() // increasing the class level
                                .clazz(String.valueOf(Long.parseLong(student.getClazz())+1))
                                .build()
                        )
                        .collect(Collectors.toList())
        );
        return String.format("Students have been promoted for the year %s", LocalDate.now().getYear());
    }
}
