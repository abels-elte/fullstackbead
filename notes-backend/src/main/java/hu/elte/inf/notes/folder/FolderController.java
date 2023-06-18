package hu.elte.inf.notes.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("folders")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @GetMapping("")
    public List<Folder> getAllFolders() {
        return folderService.getAllFolders();
    }

    @GetMapping("{id}")
    public Optional<Folder> getNoteById(@PathVariable("id") String id) {
        return folderService.getFolderById(id);
    }

    @PostMapping("")
    public Folder create(@RequestBody Folder folder) {
        return folderService.create(folder);
    }

    @PostMapping("{id}")
    public ResponseEntity<Folder> updateById(@PathVariable("id") String id, @RequestBody Folder newNote) {
        if (id.equals(newNote.getId())) return ResponseEntity.badRequest().body(null);
        return folderService.updateFolder(id, newNote).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Folder> deleteFolder(@PathVariable("id") String id) {
        return folderService.deleteFolder(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }
}
