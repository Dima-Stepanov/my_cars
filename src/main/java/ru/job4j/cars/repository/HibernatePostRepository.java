package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernatePostRepository реализация хранилища модели Post,
 * в базе данных.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 03.04.2023
 */
@Repository
@AllArgsConstructor
@Slf4j
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        return crudRepository.optional(
                "FROM Post AS p WHERE p.id =:postId",
                Post.class,
                Map.of("postId", postId)
        );
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.run(
                "DELETE FROM Post AS p WHERE p.id =:postId",
                Map.of("postId", postId)
        );
    }

    @Override
    public Collection<Post> findAllPost() {
        return crudRepository.query(
                "FROM Post AS p",
                Post.class
        );
    }

    @Override
    public Collection<Post> findAllPostLastDayOrderByCreated() {
        return crudRepository.query(
                "FROM Post AS p WHERE p.created >= CURRENT_DATE()",
                Post.class
        );
    }

    @Override
    public Collection<Post> findAllPostWithPhotos() {
        return crudRepository.query(
                """
                        FROM Post AS p 
                        File AS f 
                        WHERE p = f.post
                        """,
                Post.class
        );
    }

    @Override
    public Collection<Post> findAllPostByBrand(int brandId) {
        return crudRepository.query(
                """
                        FROM Post AS p 
                        JOIN FETCH p.model AS m 
                        JOIN FETCH m.brand AS b 
                        WHERE b.id =:brandId
                        """,
                Post.class,
                Map.of("brandId", brandId)
        );
    }
}
