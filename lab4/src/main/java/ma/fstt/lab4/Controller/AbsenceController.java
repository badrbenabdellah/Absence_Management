package ma.fstt.lab4.Controller;


import ma.fstt.lab4.Entity.Absence;
import ma.fstt.lab4.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping
    public ResponseEntity<List<Absence>> getAllAbsences() {
        return ResponseEntity.ok(absenceService.getAllAbsences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        return absenceService.getAbsenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Absence>> getAbsencesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(absenceService.getAbsencesByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<Absence> addAbsence(@RequestBody Absence absence) {
        return ResponseEntity.ok(absenceService.addAbsence(absence));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(@PathVariable Long id, @RequestBody Absence absence) {
        absence.setId(id);
        return ResponseEntity.ok(absenceService.updateAbsence(absence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Absence>> getRecentAbsences(@RequestParam(defaultValue = "2") int days) {
        List<Absence> recentAbsences = absenceService.getRecentAbsences(days);
        return ResponseEntity.ok(recentAbsences);
    }
}
