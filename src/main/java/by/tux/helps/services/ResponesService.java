package by.tux.helps.services;

import by.tux.helps.constants.Status;
import by.tux.helps.models.RequestModel;
import by.tux.helps.models.ResponseModel;
import by.tux.helps.repository.RequestsRepository;
import by.tux.helps.repository.ResponsesRepository;
import by.tux.helps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponesService {
    private final ResponsesRepository responsesRepository;

    @Autowired
    public ResponesService(ResponsesRepository responsesRepository) {
        this.responsesRepository = responsesRepository;
    }

    public boolean addResponse(long requestId,
                               String responseText,
                               String status){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRequestId(requestId);
        responseModel.setText(responseText);
        responseModel.setStatus(status);
        responsesRepository.save(responseModel);
        return true;
    }

    public List<ResponseModel> getByRequestId(long id) {
        return responsesRepository.getByRequestId(id);
    }

    public boolean setStatus(String responseId, String newStatus) {
        ResponseModel responseModel = responsesRepository.getById(Long.parseLong(responseId));
        responseModel.setStatus(newStatus);
        responsesRepository.save(responseModel);
        return true;
    }

    public boolean checkStatusById(long requestId) {
        List<ResponseModel> responseModelList = responsesRepository.getByRequestId(requestId);
        for (ResponseModel i : responseModelList){
            if (i.getStatus().equals(Status.ACCEPTED))
                 return true;
        }
        return false;
    }
}
