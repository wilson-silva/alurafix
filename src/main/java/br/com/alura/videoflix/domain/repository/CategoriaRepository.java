package br.com.alura.videoflix.domain.repository;

import br.com.alura.videoflix.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findBycor(String cor);
}
