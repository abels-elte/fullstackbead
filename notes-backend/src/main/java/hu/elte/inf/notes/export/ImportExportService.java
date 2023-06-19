package hu.elte.inf.notes.export;

import hu.elte.inf.notes.auth.AuthProvider;
import hu.elte.inf.notes.folder.Folder;
import hu.elte.inf.notes.folder.FolderRepository;
import hu.elte.inf.notes.note.Note;
import hu.elte.inf.notes.note.NoteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class ImportExportService {

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private NoteRepository noteRepository;

    /* Based on these:
        - https://stackoverflow.com/a/40498539
        - https://www.baeldung.com/convert-string-to-input-stream
    */
    public void export(OutputStream outputStream) throws IOException {
        var folders = folderRepository.getAllByOwnerId(authProvider.currentUser().id());

        var zipOutputStream = new ZipOutputStream(outputStream);

        for (Folder folder : folders) {
            var folderName = folder.isDefault() ? folder.getId() : folder.getName() + "-" +  folder.getId();

            zipOutputStream.putNextEntry(new ZipEntry(folderName + "/"));

            for (Note note : folder.getNotes()) {
                zipOutputStream.putNextEntry(new ZipEntry(folderName + "/" + note.getTitle() + "-" + note.getId() + ".txt"));

                var stringReader = new StringReader(note.getContent());
                IOUtils.copy(stringReader, zipOutputStream, StandardCharsets.UTF_8);

                zipOutputStream.closeEntry();
            }
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
    }

    @Transactional
    public void importZip(InputStream inputStream) throws IOException {
        var zipInputStream = new ZipInputStream(inputStream);
        var userId = authProvider.currentUser().id();
        var folder = folderRepository.save(new Folder(null, userId, false, "Imported", new ArrayList<>()));

        ZipEntry entry = null;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            var entryName = entry.getName();
            if(entryName.endsWith("/")) continue;


            var noteName = entryName.substring(entryName.lastIndexOf("/") + 1, entryName.length() - 4); // exclude .txt extension
            var noteContent = IOUtils.toString(zipInputStream, StandardCharsets.UTF_8);

            var savedNote = noteRepository.save(new Note(null, userId, null, null, noteName, noteContent, folder.getId()));
            folder.addNote(savedNote);

            zipInputStream.closeEntry();
        }

        folderRepository.save(folder);
        zipInputStream.close();
    }

}
