package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

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

    private final String hqlQuery = new StringBuilder()
            .append("FROM Post AS p ")
            .append("LEFT JOIN FETCH p.user AS u ")
            .append("LEFT JOIN FETCH p.priceHistory AS ph ")
            .append("LEFT JOIN FETCH p.participates AS pa ")
            .append("LEFT JOIN FETCH p.car AS c ")
            .append("LEFT JOIN FETCH c.carModel AS m ")
            .append("LEFT JOIN FETCH m.carBoard AS b ")
            .append("LEFT JOIN FETCH c.engine AS e ")
            .append("LEFT JOIN FETCH c.owners AS o ")
            .append("LEFT JOIN FETCH o.user AS uo ")
            .append("LEFT JOIN FETCH p.files AS f ")
            .toString();

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        var query = hqlQuery + "WHERE p.id =:postId;";
        return crudRepository.optional(
                query,
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
                           JOIN FETCH p.files AS f 
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
