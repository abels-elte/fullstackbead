package hu.elte.inf.notes.folder;

import hu.elte.inf.notes.auth.AuthProvider;
import hu.elte.inf.notes.auth.User;
import hu.elte.inf.notes.note.Note;
import hu.elte.inf.notes.note.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AuthProvider authProvider;

    public Folder defaultFolder(User user) {
        return folderRepository
                .getDefaultFolderByOwner(user.id)
                .orElseGet(() ->
                        folderRepository.save(new Folder(null, user.id, true, "", List.of()))
                );
    }

    public List<Folder> getAllFolders() {
        return folderRepository.getAllByOwnerId(authProvider.currentUser().id());
    }

    public Optional<Folder> getFolderById(String folderId) {
        return folderRepository.getFolderById(folderId, authProvider.currentUser().id());
    }

    public Folder create(Folder folder) {
        folder.setId(null);
        folder.setDefault(false);
        folder.setNotes(Collections.emptyList());
        folder.setOwnerId(authProvider.currentUser().id());

        return folderRepository.save(folder);
    }

    public Optional<Folder> updateFolder(String folderId, Folder folder) {
        return folderRepository.getFolderById(folderId, authProvider.currentUser().id()).map(oldFolder -> {
            oldFolder.setName(folder.getName());
            return folderRepository.save(oldFolder);
        });
    }

    @Transactional
    public Optional<Folder> deleteFolder(String folderId) {
        var deleted = folderRepository.deleteById(folderId, authProvider.currentUser().id());

        deleted.ifPresent(folder ->
            noteRepository.deleteAll(folder.getNotes())
        );

        return deleted;
    }

    @Transactional
    public Optional<Note> moveTo(String targetId, String noteId) {
        var userId = authProvider.currentUser().id();

        var optionalTargetFolder = folderRepository.getFolderById(targetId, userId);
        var optionalNote = noteRepository.getNoteById(noteId, userId);
        var optionalSource = folderRepository.getFolderById(optionalNote.map(Note::getParentId).orElse(""), userId);

        if (optionalNote.isEmpty() || optionalTargetFolder.isEmpty() || optionalSource.isEmpty())
            return Optional.empty();

        var note = optionalNote.get();
        var targetFolder = optionalTargetFolder.get();
        var sourceFolder = optionalSource.get();

        sourceFolder.removeNote(note);
        targetFolder.addNote(note);
        note.setParentId(targetFolder.getId());

        folderRepository.saveAll(List.of(sourceFolder, targetFolder));
        noteRepository.save(note);

        return Optional.of(note);
    }
}
