package br.com.alura.videoflix.repository;

import br.com.alura.videoflix.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
