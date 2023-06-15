package hu.elte.inf.notes.folder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends MongoRepository<Folder, String> { }