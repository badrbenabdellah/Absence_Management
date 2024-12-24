package ma.fstt.lab4.Repository;

import ma.fstt.lab4.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>{


}