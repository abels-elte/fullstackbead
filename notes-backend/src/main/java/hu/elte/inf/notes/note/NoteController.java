package hu.elte.inf.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {

    @Autowired
    private NoteRepository repository;

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    @GetMapping("/notes/{id}")
    public Optional<Note> getNoteById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/notes")
    public Note create() {
        return repository.save(new Note());
    }

    @PostMapping("/notes/{id}")
    public ResponseEntity<Note> updateById(@PathVariable("id") String id, @RequestBody Note newNote) {
        if (id.equals(newNote.getId())) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(repository.save(newNote));
    }

}
