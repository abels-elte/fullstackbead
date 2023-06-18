package hu.elte.inf.notes.note;

import hu.elte.inf.notes.auth.User;
import hu.elte.inf.notes.folder.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("notes")
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String id;

    @DBRef
    private User owner;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime editedOn;

    private String title;
    private String content;
    private String backgroundColor;
    private String foregroundColor;

    @DBRef
    private Folder parent;
}
