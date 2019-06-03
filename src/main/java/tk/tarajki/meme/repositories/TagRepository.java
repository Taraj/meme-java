package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{
    Tag findTagByName(String name);
}
