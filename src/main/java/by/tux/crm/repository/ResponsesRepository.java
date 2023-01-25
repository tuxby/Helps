package by.tux.crm.repository;

import by.tux.crm.models.ResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ResponsesRepository extends JpaRepository<ResponseModel, Long> {

}