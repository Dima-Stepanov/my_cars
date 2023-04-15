package ru.job4j.cars.repository;

import org.hibernate.PropertyValueException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.dao.*;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private CarBrand carBrand2;
    private CarModel carModel;
    private CarModel carModel2;
    private Engine engine;
    private User user1;
    private User user2;
    private Owner owner1;
    private Owner owner2;
    private Car car1;
    private Car car2;
    private PriceHistory priceHistory1;
    private PriceHistory priceHistory2;
    private File file1;
    private File file2;

    @BeforeAll
    public static void deleteBeforeAll() {
        deleteEntity();
    }

    @BeforeAll
    static void initCarRepository() {
        sf = HibernateConfigurationTest.getSessionFactory();
        crud = new CrudRepository(sf);
        postRepository = new HibernatePostRepository(crud);
    }

    @AfterAll
    static void closeResource() {
        if (!sf.isClosed()) {
            sf.close();
        }
    }

    private static void deleteEntity() {
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
    void initEntity() {
        carBrand = new CarBrand(0, "CarBrandPost");
        crud.run(session -> session.persist(carBrand));
        carBrand2 = new CarBrand(0, "CarBrandPost2");
        crud.run(session -> session.persist(carBrand2));

        carModel = new CarModel(0, "CarModelPost", carBrand);
        crud.run(session -> session.persist(carModel));
        carModel2 = new CarModel(0, "CarModelPost2", carBrand2);
        crud.run(session -> session.persist(carModel2));

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

        car1 = new Car(0, "carName", carModel, engine, Set.of(owner1));
        crud.run(session -> session.persist(car1));
        car2 = new Car(0, "carName2", carModel2, engine, Set.of(owner2));
        crud.run(session -> session.persist(car2));

        priceHistory1 = new PriceHistory(0, 200000, 150000,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(10));
        priceHistory2 = new PriceHistory(0, 150000, 123456,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        file1 = new File(0, "file1", "..//files/file1.jpg");
        file2 = new File(0, "file2", "..//files/file2.jpg");
    }

    @AfterEach
    void deleteAfter() {
        deleteEntity();
    }

    @Test
    void whenCreatePostAndFindByIdThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenCreatedPostByUserNullThenReturnPersistenceExceptionAndPropertyValueExceptionAndMessageNotNull() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        assertThatThrownBy(() -> postRepository.create(post))
                .isInstanceOf(PersistenceException.class)
                .hasCauseInstanceOf(PropertyValueException.class)
                .hasMessageContaining("not-null");
    }

    @Test
    void whenCreatePostByCarNullThenReturnPersistenceExceptionAndPropertyValueExceptionAndMessageNotNull() {
        var post = Post.of()
                .description("postDescription")
                .user(user1)
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .filesAdd(file1).filesAdd(file2)
                .build();
        assertThatThrownBy(() -> postRepository.create(post))
                .isInstanceOf(PersistenceException.class)
                .hasCauseInstanceOf(PropertyValueException.class)
                .hasMessageContaining("not-null");
    }

    @Test
    void whenCreatePostByPriceHistoryNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenCreatePostByParticipatesNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenCreatePostByFilesNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .build();
        postRepository.create(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(post.getId()).isGreaterThan(0);
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenCreatePostByPriceHistoryParticipatesFileIsNullThenReturnNewPostAndIdGreaterZero() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .car(car1)
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
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);
        post.setDescription("newDescriptionOfUpdate");
        postRepository.update(post);
        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenUpdatePostCreatedAndDoneAndFndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setCreated(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS));
        post.setDone(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        postRepository.update(post);
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenUpdatePostUserAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1).priceHistoryAdd(priceHistory2)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setUser(user2);
        postRepository.update(post);
        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    void whenUpdatePostPriceHistoryAddAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistoryAdd(priceHistory1)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setPriceHistory(Set.of(priceHistory2));

        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getPriceHistory().size()).isEqualTo(2);
    }

    @Test
    void whenUpdatePostPriceHistoryAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setPriceHistory(Set.of(priceHistory2));

        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getPriceHistory().size()).isEqualTo(1);
    }

    @Test
    void whenUpdatePostParticipateEmptyAddUserAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setParticipates(Set.of(user2));

        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getParticipates().size()).isEqualTo(1);
    }

    @Test
    void whenUpdatePostParticipateSizeTwoRemoveUserAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .participateAdd(user1).participateAdd(user2)
                .user(user1)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setParticipates(Set.of(user1));
        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getParticipates().size()).isEqualTo(1);
    }

    @Test
    void whenUpdatePostFilesEmptyAddFileAndByIdThenReturnUpdatePostOptional() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .car(car1)
                .build();
        postRepository.create(post);

        post.setFiles(Set.of(file1));

        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getFiles().size()).isEqualTo(1);
    }

    @Test
    void whenUpdatePostFilesSizeTwoRemoveFileAndByIdThenReturnUpdatePostOptionalFileSizeOne() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .car(car1)
                .filesAdd(file1).filesAdd(file2)
                .build();
        postRepository.create(post);

        post.setFiles(Set.of(file2));

        postRepository.update(post);

        var postInDb = postRepository.findPostById(post.getId());
        assertThat(postInDb.get().getFiles().size()).isEqualTo(1);
    }

    @Test
    void whenDeletePostThenFindByIdReturnOptionalEmpty() {
        var post = Post.of()
                .description("postDescription")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .participateAdd(user1).participateAdd(user2)
                .car(car1)
                .build();
        postRepository.create(post);

        postRepository.delete(post.getId());
        var postInDb = postRepository.findPostById(post.getId());

        assertThat(postInDb).isEmpty();
    }

    @Test
    void whenFindAllPostThenReturnCollectionEmpty() {
        var allPost = postRepository.findAllPost();
        assertThat(allPost.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllPostThenReturnCollectionPost() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .filesAdd(file1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().minusDays(5).truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var expectedAllPosts = List.of(post1, post2);
        var actualAllPosts = postRepository.findAllPost();

        assertThat(actualAllPosts).usingRecursiveComparison().isEqualTo(expectedAllPosts);
    }

    @Test
    void whenFindAllPostLastDayOrderByCreatedThenReturnCollectionEmpty() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .filesAdd(file1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().minusDays(5).truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var actualLostDayPosts = postRepository.findAllPostLastDayOrderByCreated();

        assertThat(actualLostDayPosts.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllPostLastDayOrderByCreatedThenReturnCollectionSize1() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .filesAdd(file1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var expectedLostDayPost = List.of(post2);
        var actualLostDayPosts = postRepository.findAllPostLastDayOrderByCreated();

        assertThat(actualLostDayPosts).usingRecursiveComparison().isEqualTo(expectedLostDayPost);
    }

    @Test
    void whenFindAllPostLastDayThenReturnCollectionPost() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .filesAdd(file1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var expectedLostDayPosts = List.of(post1, post2);
        var actualLostDayPosts = postRepository.findAllPostLastDayOrderByCreated();

        assertThat(actualLostDayPosts).usingRecursiveComparison().isEqualTo(expectedLostDayPosts);
    }

    @Test
    void whenFindAllPostWithPhotosThenReturnCollectionPostSizeOne() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2).filesAdd(file1)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var expectedAllPostsWithPhoto = List.of(post2);
        var actualAllPostsWithPhoto = postRepository.findAllPostWithPhotos();

        assertThat(actualAllPostsWithPhoto).usingRecursiveComparison().isEqualTo(expectedAllPostsWithPhoto);
    }

    @Test
    void whenFindAllPostWithPhotosThenReturnCollectionEmpty() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var actualAllPostsWithPhoto = postRepository.findAllPostWithPhotos();

        assertThat(actualAllPostsWithPhoto.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllPostByBrandThenReturnCollectionPostSizeOne() {
        var post1 = Post.of()
                .description("postDescription1")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory1)
                .user(user1)
                .participateAdd(user2)
                .car(car1)
                .filesAdd(file1)
                .build();
        var post2 = Post.of()
                .description("postDescription2")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .priceHistoryAdd(priceHistory2)
                .user(user2)
                .participateAdd(user1)
                .car(car2)
                .filesAdd(file2)
                .build();
        postRepository.create(post1);
        postRepository.create(post2);

        var expectedAllPostsByBrandPost2 = List.of(post1);
        var actualAllPostsByBrandPost2 = postRepository.findAllPostByBrand(post1.getCar().getCarModel().getCarBrand().getId());

        assertThat(actualAllPostsByBrandPost2).usingRecursiveComparison().isEqualTo(expectedAllPostsByBrandPost2);
    }

    @Test
    void whenFindAllPostByBrandThenReturnCollectionsEmpty() {
        var posts = postRepository.findAllPostByBrand(-1);
        assertThat(posts.isEmpty()).isTrue();
    }
}