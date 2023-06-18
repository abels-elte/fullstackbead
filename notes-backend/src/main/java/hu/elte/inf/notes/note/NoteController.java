package hu.elte.inf.notes.note;

import hu.elte.inf.notes.auth.AuthProvider;
import hu.elte.inf.notes.auth.UserInfo;
import hu.elte.inf.notes.auth.UserPrincipal;
import hu.elte.inf.notes.folder.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("")
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("{id}")
    public Optional<Note> getNoteById(@PathVariable("id") String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("")
    public Note create(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PostMapping("{id}")
    public ResponseEntity<Note>  updateById(@PathVariable("id") String id, @RequestBody Note newNote) {
        if (id.equals(newNote.getId())) return ResponseEntity.badRequest().body(null);
        return noteService.updateNote(id, newNote).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable("id") String id) {
        return noteService.deleteNote(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }
}
