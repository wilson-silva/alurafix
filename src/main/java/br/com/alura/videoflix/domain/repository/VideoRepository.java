package br.com.alura.videoflix.domain.repository;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByUrl(String url);

    List<Video> findByCategoryId(Long id);

    Optional<Video> findByTitle(String title);

}
