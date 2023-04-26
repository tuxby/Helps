package by.tux.helps.services;

import by.tux.helps.configuration.User;
import by.tux.helps.constants.Status;
import by.tux.helps.models.RequestModel;
import by.tux.helps.repository.RequestsRepository;
import by.tux.helps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestsService {
    private final RequestsRepository requestsRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestsService(RequestsRepository requestsRepository, UserRepository userRepository) {
        this.requestsRepository = requestsRepository;
        this.userRepository = userRepository;
    }

    public boolean addResponse(String title,
                               String text,
                               String login){
        User user = userRepository.findUserByLogin(login);
        if (user == null)
            return false;

        RequestModel requestModel = new RequestModel();

        requestModel.setTitle(title);
        requestModel.setText(text);
        requestModel.setUserId(user.getId());

        requestModel.setStatus(Status.NEW);

        requestsRepository.save(requestModel);

        return true;
    }

    public List<RequestModel> getUserRequests(long id){
        List<RequestModel> requestModel = requestsRepository.findByUserId(id);
        if (requestModel.isEmpty()) {
            return null;
        }
        return requestModel;
    }

    public RequestModel getRequest(long id){
        RequestModel requestModel = requestsRepository.findById(id);
        return requestModel;
    }

    public List<RequestModel> getRequestWithStatus(String status) {
        return requestsRepository.findRequestModelByStatus(status);
    }

    public List<RequestModel> getAll() {
        return requestsRepository.findAll();
    }

    public boolean setStatus(long requestId, String newStatus){
        RequestModel requestModel = requestsRepository.getById(requestId);
        requestModel.setStatus(newStatus);
        requestsRepository.save(requestModel);
        return true;
    }
}
