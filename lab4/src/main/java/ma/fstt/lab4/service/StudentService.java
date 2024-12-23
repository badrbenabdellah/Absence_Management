package ma.fstt.lab4.service;

import ma.fstt.lab4.Entity.Student;
import ma.fstt.lab4.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(Math.toIntExact(id));
    }

    public Student addStudent(Student student) {
        if (student.getId() != null) {
            throw new IllegalArgumentException("New student cannot have an ID.");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Student updatedStudent) {
        Student existingStudent = studentRepository.findById(Math.toIntExact(updatedStudent.getId()))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Mettez à jour uniquement les champs nécessaires
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setClassName(updatedStudent.getClassName());

        // Ne touchez pas à la liste des absences
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Cannot delete student. Student with ID " + id + " does not exist.");
        }
        studentRepository.deleteById(Math.toIntExact(id));
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }
}
