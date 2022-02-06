package by.tms.dao.post;

import by.tms.entity.Comment;
import by.tms.entity.Post;
import by.tms.entity.SearchParameters;
import java.util.List;
import java.util.Optional;

public interface PostDao {
    List<Post> getAllPosts();

    Post getPostByTitle(String title);

    Post getPostById(long id);

    List<Post> getPostsByParameters(SearchParameters searchParameters);

    void createPost(Post post);

    void updatePost(Post post);

    void deletePost(long postId);

    boolean existPostByTitle(String title);

    boolean existPostById(long id);
}
