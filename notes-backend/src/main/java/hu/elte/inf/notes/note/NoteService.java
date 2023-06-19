package hu.elte.inf.notes.note;

import hu.elte.inf.notes.auth.AuthProvider;
import hu.elte.inf.notes.auth.User;
import hu.elte.inf.notes.folder.Folder;
import hu.elte.inf.notes.folder.FolderRepository;
import hu.elte.inf.notes.folder.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FolderRepository folderRepository;

    public List<Note> getAllNotes() {
        return noteRepository.getAllByOwnerId(authProvider.currentUser().id());
    }

    public Optional<Note> getNoteById(String id) {
        return noteRepository.getNoteById(id, authProvider.currentUser().id());
    }

    @Transactional
    public Note createNote(Note note) {
        var userId = authProvider.currentUser().id();
        var folder = folderRepository.getFolderById(note.getParentId(), userId);
        var defaultFolder = folderRepository.getDefaultFolderByOwner(userId);

        note.setId(null);
        note.setOwnerId(userId);

        var f = folder.orElseGet(defaultFolder::get);

        note.setParentId(f.getId());

        var savedNote = noteRepository.save(note);

        f.addNote(savedNote);
        folderRepository.save(f);

        return savedNote;

    }

    public Optional<Note> updateNote(String id, Note note) {
        return noteRepository.getNoteById(id, authProvider.currentUser().id()).map(oldNote -> {

            oldNote.setTitle(note.getTitle());
            oldNote.setContent(note.getContent());

            return noteRepository.save(oldNote);
        });
    }

    @Transactional
    public Optional<Note> deleteNote(String id) {
        var userId = authProvider.currentUser().id();
        var deleted = noteRepository.deleteById(id, userId);

        deleted.ifPresent(note -> {
            var parent = folderRepository.getFolderById(note.getParentId(), userId).get();
            parent.removeNote(note);
            folderRepository.save(parent);
        });

        return deleted;
    }


}
