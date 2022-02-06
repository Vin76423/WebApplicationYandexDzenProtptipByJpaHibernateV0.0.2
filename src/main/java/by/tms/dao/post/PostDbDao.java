package by.tms.dao.post;

import static java.util.Objects.nonNull;

import by.tms.entity.Post;
import by.tms.entity.SearchParameters;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class PostDbDao implements PostDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<Post> getAllPosts() {
        return entityManager.createNamedQuery("Post.findAll", Post.class).getResultList();
    }

    @Override
    public Post getPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        return entityManager.createNamedQuery("Post.findByTitle", Post.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    public Post getPostById(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        return entityManager.find(Post.class, postId);
    }

    /**
     * Criteria in JPA example:
     */
    @Override
    public List<Post> getPostsByParameters(SearchParameters searchParameters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = query.from(Post.class);

        if (nonNull(searchParameters.getTitle())) {
            query.where(criteriaBuilder.equal(postRoot.get("title"), searchParameters.getTitle()));
        }
        //TODO: can add other criteria: if (..) { query.where(..) }

        CriteriaQuery<Post> selectCriteriaQuery = query.select(postRoot);

        TypedQuery<Post> result = entityManager.createQuery(selectCriteriaQuery);
        return result.getResultList();
    }

    @Override
    public void createPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null!");
        }
        entityManager.persist(post);
    }

    @Override
    public void updatePost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null!");
        }
        entityManager.merge(post);
    }

    @Override
    public void deletePost(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        Post post = entityManager.getReference(Post.class, postId);
        entityManager.remove(post);
    }



    @Override
    public boolean existPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        long result = (Long) entityManager.createNamedQuery("Post.existByTitle")
                .setParameter("title", title)
                .getSingleResult();
        return result > 0;
    }

    @Override
    public boolean existPostById(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        Post post = entityManager.find(Post.class, postId);
        return post != null;
    }
}
