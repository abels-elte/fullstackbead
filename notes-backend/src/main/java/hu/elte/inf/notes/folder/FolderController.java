package hu.elte.inf.folders.folder;


import hu.elte.inf.notes.folder.Folder;
import hu.elte.inf.notes.folder.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FolderController {
    @Autowired
    private FolderRepository repository;

    @GetMapping("/folders")
    public List<Folder> getAllFolders() {
        return repository.findAll();
    }

    @GetMapping("/folders/{id}")
    public Optional<Folder> getNoteById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/folders")
    public Folder create() {
        return repository.save(new Folder());
    }

    @PostMapping("/folders/{id}")
    public ResponseEntity<Folder> updateById(@PathVariable("id") String id, @RequestBody Folder newNote) {
        if (id.equals(newNote.getId())) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(repository.save(newNote));
    }
}
