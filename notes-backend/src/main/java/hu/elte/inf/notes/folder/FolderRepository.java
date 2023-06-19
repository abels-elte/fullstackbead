package hu.elte.inf.notes.folder;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends MongoRepository<Folder, String> {
    @Query("{ownerId: '?0', isDefault: true}")
    Optional<Folder> getDefaultFolderByOwner(String userId);

    @Query("{ownerId:  '?0'}")
    List<Folder> getAllByOwnerId(String ownerId);

    @Query("{ownerId:  '?1', _id:  '?0'}")
    Optional<Folder> getFolderById(String folderId, String ownerId);

    @DeleteQuery("{ownerId:  '?1', _id:  '?0'}")
    Optional<Folder> deleteById(String folderId, String ownerId);
}