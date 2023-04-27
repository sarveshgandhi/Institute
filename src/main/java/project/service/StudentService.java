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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentMapper studentMapper,
                          StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    public String addBatchStudentsAsJson(MultipartFile file) {
        validateFileUpload(file);
        JSONArray jsonUpload;
        try {
            jsonUpload = new JSONArray(new String(file.getBytes()));
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }

        validateJsonStructure(jsonUpload);

        List<Student> newStudents = studentMapper.map(jsonUpload);
        studentRepository.addAll(newStudents);
        return String.format("Successfully admitted %s students to school, total strength = %s",
                newStudents.size(), studentRepository.getStrength());
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public List<Student> getStudentsFromClass(Long className) {
        validateInput(className);
        String clazz = className.toString();
        List<Student> allStudents = studentRepository.getAllStudents();
        return allStudents
                .stream()
                .filter(student -> clazz.equals(student.getClazz()))
                .collect(Collectors.toList());
    }

    public String addStudent(Student student) {
        validateStudent(student);
        studentRepository.add(student);
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

    private void validateJsonStructure(JSONArray jsonUpload) {
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

    private void validateFileUpload(MultipartFile file) {

        if (nonNull(file.getOriginalFilename()) && !file.getOriginalFilename().endsWith(".json")) {
            throw new CustomException("File format not supported");
        }

        if (file.isEmpty()) {
            throw new CustomException("File uploaded was found empty");
        }
    }

    private void validateInput(Long className) {
        if (isEmpty(className)) {
            throw new CustomException("Class can not be empty");
        }
    }
}
