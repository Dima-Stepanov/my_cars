package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.cars.mapper.PostMapper;
import ru.job4j.cars.model.dao.Post;
import ru.job4j.cars.model.dto.PostDTO;
import ru.job4j.cars.repository.PostRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * SimplePostService слой бизнес логики обработки модели Post
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 12.04.2023
 */
@Service
@AllArgsConstructor
@Slf4j
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    @Override
    public boolean create(Post post) {
        try {
            postRepository.create(post);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Post post) {
        try {
            postRepository.update(post);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<PostDTO> findPostById(int postId) {
        Optional<Post> post = postRepository.findPostById(postId);
        return post.map(mapper::getPostDto);
    }

    @Override
    public Collection<PostDTO> findAllPost() {
        return postRepository.findAllPost().stream()
                .map(mapper::getPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PostDTO> findAllPostLastDayOrderByCreated() {
        return postRepository.findAllPostLastDayOrderByCreated().stream()
                .map(mapper::getPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PostDTO> findAllPostWithPhotos() {
        return postRepository.findAllPostWithPhotos().stream()
                .map(mapper::getPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PostDTO> findAllPostByBrand(int postId) {
        return postRepository.findAllPostByBrand(postId).stream()
                .map(mapper::getPostDto)
                .collect(Collectors.toList());
    }
}
