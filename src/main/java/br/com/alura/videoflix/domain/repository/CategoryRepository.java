package br.com.alura.videoflix.domain.repository;

import br.com.alura.videoflix.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitleOrColor(String title, String color);
}
