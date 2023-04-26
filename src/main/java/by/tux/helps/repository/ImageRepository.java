package by.tux.helps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tux.helps.models.ImageModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findById(Long id);

    List<ImageModel> findByAuthorId(Long authorId);
}
