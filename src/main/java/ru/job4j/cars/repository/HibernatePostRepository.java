package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

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

    private final String hqlQuery = new StringBuilder()
            .append("FROM Post AS po ")
            .append("LEFT JOIN FETCH po.user AS us ")
            .append("LEFT JOIN FETCH po.priceHistory AS ph ")
            .append("LEFT JOIN FETCH po.participates AS pa ")
            .append("LEFT JOIN FETCH po.car AS ca ")
            .append("LEFT JOIN FETCH ca.carModel AS cm ")
            .append("LEFT JOIN FETCH cm.carBoard AS cb ")
            .append("LEFT JOIN FETCH ca.engine AS en ")
            .append("LEFT JOIN FETCH ca.owners AS ow ")
            .append("LEFT JOIN FETCH ow.user AS uw ")
            .append("LEFT JOIN FETCH po.files AS fi ")
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
