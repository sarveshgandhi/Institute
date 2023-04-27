package project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Student {

    private Long enrollmentNumber;
    private String fullName;
    private LocalDateTime createdAt;
    private String email;

    @JsonProperty("class")
    private String clazz;
}
