package by.tux.helps.repository;

import by.tux.helps.models.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RequestsRepository extends JpaRepository<RequestModel, Long> {

    RequestModel findById(long id);
    List<RequestModel> findByUserId(long id);

    List<RequestModel> findRequestModelByStatus(String status);
}