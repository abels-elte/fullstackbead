package hu.elte.inf.notes.folder;

import hu.elte.inf.notes.note.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("folders")
public class Folder {
    @Id
    private String id;

    @DBRef
    private String ownerId;

    private String name;

    @DBRef
    private List<Note> notes;
}
