package hu.elte.inf.notes.note;

import hu.elte.inf.notes.folder.Folder;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    @Query("{owner:  '?0'}")
    List<Note> getAllByOwnerId(String ownerId);


    @Query("{owner:  '?1', _id:  '?0'}")
    Optional<Note> getNoteById(String id, String ownerId);


    @DeleteQuery("{owner:  '?1', _id:  '?0'}")
    Optional<Note> deleteById(String id, String ownerId);
}