package hu.elte.inf.notes.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    public String id;
    private String username;
    private String displayName;
}

