package by.tux.helps.repository;

import by.tux.helps.models.ResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ResponsesRepository extends JpaRepository<ResponseModel, Long> {

    List<ResponseModel> getByRequestId(long id);
}