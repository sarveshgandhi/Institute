package project.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.exception.CustomException;

import static java.util.Objects.nonNull;

@Component
public class JsonValidator {

    public void validateFileUpload(MultipartFile file) {

        if (nonNull(file.getOriginalFilename()) && !file.getOriginalFilename().endsWith(".json")) {
            throw new CustomException("File format not supported");
        }

        if (file.isEmpty()) {
            throw new CustomException("File uploaded was found empty");
        }
    }
}
