package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;
import ru.job4j.cars.model.filemodel.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    private static SessionFactory sessionFactory;
    private static CrudRepository crudPost;
    private static HibernatePostRepository postRepository;
    private CarBrand carBrand;
    private CarModel carModel;
    private Engine engine;
    private User user1;
    private User user2;
    private Owner owner1;
    private Owner owner2;
    private PriceHistory priceHistory1;
    private PriceHistory priceHistory2;
    private File file1;
    private File file2;

    @BeforeAll
    public static void initCarRepository() {
        sessionFactory = HibernateConfigurationTest.getSessionFactory();
        crudPost = new CrudRepository(sessionFactory);
        postRepository = new HibernatePostRepository(crudPost);
    }

    @AfterAll
    public static void closeResource() {
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    private void deleteEntity() {
    //    crud.run("delete from Post as p where p.id >:pId",
     //           Map.of("pIf", 0));
        crudPost.run("delete from Car as c where c.id >:cId",
                Map.of("cId", 0));
        crudPost.run("delete from Owner as o where o.id >:oId",
                Map.of("oId", 0));
        crudPost.run("delete from User as u where u.id >:uId",
                Map.of("uId", 0));
        crudPost.run("delete from Engine as e where e.id >:eId",
                Map.of("eId", 0));
        crudPost.run("delete from CarModel as cm where cm.id >: cmId",
                Map.of("cmId", 0));
        crudPost.run("delete from CarBrand as cb where cb.id >:cbId",
                Map.of("cbId", 0));
        crudPost.run("delete from PriceHistory as ph where ph.id >:phId",
                Map.of("phId", 0));
        crudPost.run("delete from Files AS f where f.id >:fId",
                Map.of("fId", 0));
    }

    @AfterEach
    public void deleteAfter() {
        deleteEntity();
    }

    @BeforeEach
    public void initEntity() {
        carBrand = new CarBrand(0, "CarBrandPost");
        crudPost.run(session -> session.persist(carBrand));

        carModel = new CarModel(0, "CarModelPost", carBrand);
        crudPost.run(session -> session.persist(carModel));

        engine = new Engine(0, "EnginePost");
        crudPost.run(session -> session.persist(engine));

        user1 = new User(0, "User1LoginPost", "User1Pass");
        user2 = new User(0, "User2LoginPost", "User2Pass");
        crudPost.run(session -> session.persist(user1));
        crudPost.run(session -> session.persist(user2));

        owner1 = new Owner(0, "Owner1Post", user1);
        owner2 = new Owner(0, "Owner2Post", user2);
        crudPost.run(session -> session.persist(owner1));
        crudPost.run(session -> session.persist(owner2));

        priceHistory1 = new PriceHistory(0, 0, 150000,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(10));
        priceHistory2 = new PriceHistory(0, 150000, 123456,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        crudPost.run(session -> session.persist(priceHistory1));
        crudPost.run(session -> session.persist(priceHistory2));

        file1 = new File(0, "file1", "..//files/file1.jpg");
        file2 = new File(0, "file2", "..//files/file2.jpg");
        crudPost.run(session -> session.persist(file1));
        crudPost.run(session -> session.persist(file2));
    }

    @Test
    void save() {
    }

    @Test
    void findPostById() {
    }

    @Test
    void update() {
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
        var posts = postRepository.findAllPostByBrand(99);
        assertThat(posts.isEmpty()).isTrue();
    }
}