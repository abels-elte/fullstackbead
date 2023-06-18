package hu.elte.inf.notes.folder;

import hu.elte.inf.notes.auth.AuthProvider;
import hu.elte.inf.notes.auth.User;
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
                        folderRepository.save(new Folder(null, user, true, "", List.of()))
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
        folder.setOwner(new User(authProvider.currentUser().id(), null, null, null));

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

}
