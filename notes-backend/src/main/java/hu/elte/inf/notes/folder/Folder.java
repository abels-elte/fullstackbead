package hu.elte.inf.notes.folder;

import hu.elte.inf.notes.note.Note;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("folders")
public class Folder {
    @Id
    private String id;

    @Indexed
    private String ownerId;

    @Indexed
    private boolean isDefault;

    private String name;

    @DBRef
    private List<Note> notes;

    public void removeNote(Note note) {
        this.notes.remove(note);
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
