package ru.job4j.cars.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.dao.*;
import ru.job4j.cars.model.dto.PostDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 15.04.2023
 */
class PostMapperTest {
    private PostMapper postMapper = new PostMapper();

    @BeforeEach
    public void initPostModel() {

    }

    @Test
    void whenGetPostDtoThenReturnPostDto() {
        CarBrand carBrand = new CarBrand(1, "carBrand");
        CarModel carModel = new CarModel(1, "carModel", carBrand);
        User user1 = new User(1, "login1", "password1");
        User user2 = new User(2, "login2", "password2");
        Owner owner1 = new Owner(1, "owner1", user1);
        Owner owner2 = new Owner(2, "owner2", user2);
        Set<Owner> owners = Set.of(owner1, owner2);
        Engine engine = new Engine(1, "engine");
        Car car = new Car(1, "car", carModel, engine, owners);
        PriceHistory priceHistory1 = new PriceHistory(1, 100, 200, LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS));
        PriceHistory priceHistory2 = new PriceHistory(2, 200, 500, LocalDateTime.now().minusDays(2).truncatedTo(ChronoUnit.SECONDS));
        Set<PriceHistory> priceHistories = Set.of(priceHistory1, priceHistory2);
        File file1 = new File(1, "name1", "path1");
        File file2 = new File(2, "name2", "path2");
        Post post = Post.of()
                .id(1).description("description")
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .user(user1)
                .priceHistory(priceHistories)
                .participates(Set.of(user1, user2))
                .car(car)
                .files(Set.of(file1, file2))
                .build();

        PostDTO expectedPostDto = PostDTO.of().id(post.getId())
                .description(post.getDescription())
                .created(post.getCreated())
                .done(post.getDone())
                .beforePrice(priceHistory1.getBefore())
                .afterPrice(priceHistory2.getAfter())
                .carBrand(carBrand.getName())
                .carModel(carModel.getName())
                .engine(engine.getName())
                .owners(owners.size())
                .participates(post.getParticipates().size())
                .fileId(new int[]{file1.getId(), file2.getId()})
                .build();

        PostDTO actualPostDto = postMapper.getPostDto(post);
        assertThat(actualPostDto).usingRecursiveComparison().isEqualTo(expectedPostDto);
    }
}