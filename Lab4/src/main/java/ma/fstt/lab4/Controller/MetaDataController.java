package ma.fstt.lab4.Controller;


import ma.fstt.lab4.service.AbsenceService;
import ma.fstt.lab4.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/metadata")
public class MetaDataController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AbsenceService absenceService;

    @GetMapping
    public ResponseEntity<Map<String, Long>> getMetadata() {
        Map<String, Long> metadata = new HashMap<>();
        metadata.put("totalStudents", studentService.getTotalStudents());
        metadata.put("totalAbsences", absenceService.getTotalAbsences());
        metadata.put("absencesToday", absenceService.getAbsencesForToday());

        return ResponseEntity.ok(metadata);
    }
}
