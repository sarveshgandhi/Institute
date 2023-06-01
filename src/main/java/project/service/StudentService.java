package project.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.exception.CustomException;
import project.mapper.StudentMapper;
import project.model.Student;
import project.respository.StudentRepository;
import project.validator.JsonValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final JsonValidator jsonValidator;

    @Autowired
    public StudentService(StudentMapper studentMapper,
                          StudentRepository studentRepository,
                          JsonValidator jsonValidator) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
        this.jsonValidator = jsonValidator;
    }

    public String addBatchStudentsAsJson(MultipartFile file) {
        jsonValidator.validateFileUpload(file);
        JSONArray jsonUpload;
        try {
            jsonUpload = new JSONArray(new String(file.getBytes()));
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }

        validateJsonStructureForStudent(jsonUpload);

        List<Student> newStudents = studentMapper.map(jsonUpload);
        studentRepository.saveAll(newStudents);
        return String.format("Successfully admitted %s students to school, total strength = %s",
                newStudents.size(), getAllStudents().size());
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> getStudentsFromClass(Long className) {
        validateInput(className);
        String clazz = className.toString();
        List<Student> allStudents = getAllStudents();
        return allStudents
                .stream()
                .filter(student -> clazz.equals(student.getClazz()))
                .collect(Collectors.toList());
    }

    public String addStudent(Student student) {
        validateStudent(student);
        student.setCreatedAt(LocalDateTime.now());
        studentRepository.save(student);
        return "Student added successfully";
    }

    private void validateStudent(Student selectedStudent) {
        if (isNull(selectedStudent) || isNull(selectedStudent.getEnrollmentNumber())) {
            throw new CustomException("Given student is not valid");
        }
        if (isNull(selectedStudent.getClazz())) {
            throw new CustomException("A student should be enrolled in a class, error in input");
        }
    }

    private void validateJsonStructureForStudent(JSONArray jsonUpload) {
        IntStream.range(0, jsonUpload.length())
                .boxed()
                .forEach(index -> {
                    JSONObject selectedStudent = (JSONObject) jsonUpload.get(index);
                    if (!selectedStudent.has("Enrollment Number")) {
                        throw new CustomException("File format not supported");
                    }
                    if (!selectedStudent.has("Class")) {
                        throw new CustomException("A student should be enrolled in a class, exception for {}",
                                selectedStudent.get("Enrollment Number"));
                    }
                });
    }


    private void validateInput(Long className) {
        if (isEmpty(className)) {
            throw new CustomException("Class can not be empty");
        }
    }
}
