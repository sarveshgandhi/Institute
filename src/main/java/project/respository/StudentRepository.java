package project.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, ListCrudRepository<Student, Long> {
}
