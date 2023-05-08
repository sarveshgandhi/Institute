package project.mapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import project.model.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StudentMapper {

    public List<Student> map(JSONArray jsonUpload) {
        return IntStream.range(0, jsonUpload.length())
                .boxed()
                .map(index -> mapToStudent(jsonUpload.getJSONObject(index)))
                .collect(Collectors.toList());

    }

    private Student mapToStudent(JSONObject jsonObject) {
        return Student.builder()
                .enrollmentNumber(jsonObject.getLong("Enrollment Number"))
                .fullName(jsonObject.getString("Full Name"))
                .clazz(String.valueOf(jsonObject.getLong("Class")))
                .email(jsonObject.getString("Email"))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
