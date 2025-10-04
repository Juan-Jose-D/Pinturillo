package edu.eci.arsw.pinturillo.api;

import edu.eci.arsw.pinturillo.model.Stroke;
import edu.eci.arsw.pinturillo.service.StrokeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strokes")
@CrossOrigin
public class StrokeController {
    private final StrokeService strokeService;

    public StrokeController(StrokeService strokeService) {
        this.strokeService = strokeService;
    }

    @GetMapping
    public List<Stroke> list(@RequestParam(name = "since", defaultValue = "0") long since) {
        return strokeService.getSince(since);
    }

    @PostMapping
    public ResponseEntity<Stroke> create(@RequestBody Stroke s) {
        if (s.getColor() == null || s.getColor().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (s.getSize() <= 0) {
            s.setSize(8);
        }
        Stroke created = strokeService.add(s);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping
    public ResponseEntity<Void> clear() {
        strokeService.clear();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/epoch")
    public long epoch() {
        return strokeService.getEpoch();
    }
}
