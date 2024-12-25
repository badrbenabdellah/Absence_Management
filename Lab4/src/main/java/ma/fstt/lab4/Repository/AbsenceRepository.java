package ma.fstt.lab4.Repository;

import ma.fstt.lab4.Entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByStudentId(Long studentId);
    long countByDate(LocalDate date);
    @Query("SELECT a FROM Absence a WHERE a.date >= :startDate")
    List<Absence> findRecentAbsences(@Param("startDate") LocalDate startDate);
}
