package ru.job4j.cars.repository;

import org.hibernate.PropertyValueException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;
import ru.job4j.cars.model.filemodel.*;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernatePostRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2023
 */
class HibernatePostRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crud;
    private static HibernatePostRepository postRepository;
    private CarBrand carBrand;
    private CarModel carModel;
    private Engine engine;
    private User user1;
    private User user2;
    private Owner owner1;
    private Owner owner2;
    private Car car;
    private PriceHistory priceHistory1;
    private PriceHistory priceHistory2;
    private File file1;
    private File file2;

    @BeforeAll
    public static void initCarRepository() {
        sf = HibernateConfigurationTest.getSessionFactory();
        crud = new CrudRepository(sf);
        postRepository = new HibernatePostRepository(crud);
    }

    @AfterAll
    public static void closeResource() {
        if (!sf.isClosed()) {
            sf.close();
        }
    }

    private void deleteEntity() {
        crud.run("DELETE FROM PriceHistory AS ph WHERE ph.id >:phId",
                Map.of("phId", 0));
        crud.run("DELETE FROM File AS fi WHERE fi.id >:fileId",
                Map.of("fileId", 0));
        crud.run("DELETE FROM Post AS po WHERE po.id >:postId",
                Map.of("postId", 0));
        crud.run("DELETE FROM Car AS ca WHERE ca.id >:carId",
                Map.of("carId", 0));
        crud.run("DELETE FROM Owner AS ow WHERE ow.id >:ownerId",
                Map.of("ownerId", 0));
        crud.run("DELETE FROM User AS us WHERE us.id >:userId",
                Map.of("userId", 0));
        crud.run("DELETE FROM Engine AS en WHERE en.id >:engineId",
                Map.of("engineId", 0));
        crud.run("DELETE FROM CarModel AS cm WHERE cm.id >:cmId",
                Map.of("cmId", 0));
        crud.run("DELETE FROM CarBrand AS cb WHERE cb.id >:cbId",
                Map.of("cbId", 0));
    }

    @BeforeEach
    public void initEntity() {
        carBrand = new CarBrand(0, "CarBrandPost");
        crud.run(session -> session.persist(carBrand));

        carModel = new CarModel(0, "CarModelPost", carBrand);
        crud.run(session -> session.persist(carModel));

        engine = new Engine(0, "EnginePost");
        crud.run(session -> session.persist(engine));

        user1 = new User(0, "User1LoginPost", "User1Pass");
        user2 = new User(0, "User2LoginPost", "User2Pass");
        crud.run(session -> session.persist(user1));
        crud.run(session -> session.persist(user2));

        owner1 = new Owner(0, "Owner1Post", user1);
        owner2 = new Owner(0, "Owner2Post", user2);
        crud.run(session -> session.persist(owner1));
        crud.run(session -> session.persist(owner2));

        car = new Car(0, "carName", carModel, engine, Set.of(owner1, owner2));
        crud.run(session -> session.persist(car));

        priceHistory1 = new PriceHistory(0, 200000, 150000,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(10));
        priceHistory2 = new PriceHistory(0, 150000, 123456,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        file1 = new File(0, "file1", "..//files/file1.jpg");
        file2 = new File(0, "file2", "..//files/file2.jpg");
    }

    @AfterEach
    public void deleteAfter() {
        deleteEntity();
    }

    @Test
    void whenCreatePostAndFindByIdThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participatesAdd(user1).participatesAdd(user2)
                .car(car)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    public void whenCreatedPostByUserNullThenReturnPersistenceExceptionAndPropertyValueExceptionAndMessageNotNull() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participatesAdd(user1).participatesAdd(user2)
                .car(car)
                .filesAdd(file1).filesAdd(file2)
                .build();
        assertThatThrownBy(() -> postRepository.create(post))
                .isInstanceOf(PersistenceException.class)
                .hasCauseInstanceOf(PropertyValueException.class)
                .hasMessageContaining("not-null");
    }

    @Test
    public void whenCreatePostByCarNullThenReturnPersistenceExceptionAndPropertyValueExceptionAndMessageNotNull() {
        var post = Post.of()
                .description("postDescription")
                .user(user1)
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participatesAdd(user1).participatesAdd(user2)
                .filesAdd(file1).filesAdd(file2)
                .build();
        assertThatThrownBy(() -> postRepository.create(post))
                .isInstanceOf(PersistenceException.class)
                .hasCauseInstanceOf(PropertyValueException.class)
                .hasMessageContaining("not-null");
    }

    @Test
    public void whenCreatePostByPriceHistoryNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .participatesAdd(user1).participatesAdd(user2)
                .car(car)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    public void whenCreatePostByParticipatesNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .car(car)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    public void whenCreatePostByFilesNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participatesAdd(user1).participatesAdd(user2)
                .car(car)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenFindPostByIdThenReturnOptionalEmpty() {
        var actual = postRepository.findPostById(-1);
        assertThat(actual).isEmpty();
    }

    @Test
    void whenUpdatePostDescriptionAndFindByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participatesAdd(user1).participatesAdd(user2)
                .car(car)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        post.setDescription("newDescriptionOfUpdate");
        postRepository.update(post);
        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void delete() {
    }

    @Test
    void findAllPost() {
    }

    @Test
    void findAllPostLastDayOrderByCreated() {
    }

    @Test
    void findAllPostWithPhotos() {
    }

    @Test
    void findAllPostByBrand() {
        var posts = postRepository.findAllPostByBrand(-1);
        assertThat(posts.isEmpty()).isTrue();
    }
}