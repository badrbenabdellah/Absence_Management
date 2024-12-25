package ma.fstt.lab4.service;

import ma.fstt.lab4.Entity.Absence;
import ma.fstt.lab4.Entity.Student;
import ma.fstt.lab4.Repository.AbsenceRepository;
import ma.fstt.lab4.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Optional<Absence> getAbsenceById(Long id) {
        return absenceRepository.findById(id);
    }

    public List<Absence> getAbsencesByStudentId(Long studentId) {
        return absenceRepository.findByStudentId(studentId);
    }

    public Absence addAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }

    public Absence updateAbsence(Absence updatedAbsence) {
        // Récupérer l'absence existante
        Absence existingAbsence = absenceRepository.findById(updatedAbsence.getId())
                .orElseThrow(() -> new RuntimeException("Absence not found"));

        // Mettre à jour uniquement les champs nécessaires
        existingAbsence.setDate(updatedAbsence.getDate());
        existingAbsence.setJustification(updatedAbsence.getJustification());

        // Récupérer l'étudiant existant pour éviter toute recréation
        Student student = studentRepository.findById(Math.toIntExact(updatedAbsence.getStudent().getId()))
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existingAbsence.setStudent(student);

        return absenceRepository.save(existingAbsence);
    }

    public void deleteAbsence(Long id) {
        if (!absenceRepository.existsById(id)) {
            throw new IllegalArgumentException("Absence not found");
        }
        absenceRepository.deleteById(id);
    }

    public long getTotalAbsences() {
        return absenceRepository.count();
    }

    public long getAbsencesForToday() {
        return absenceRepository.countByDate(LocalDate.now());
    }

    public List<Absence> getRecentAbsences(int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);
        return absenceRepository.findRecentAbsences(startDate);
    }
}
