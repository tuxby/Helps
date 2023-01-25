package by.tux.crm.services;

import by.tux.crm.configuration.User;
import by.tux.crm.constants.Status;
import by.tux.crm.models.RequestModel;
import by.tux.crm.repository.RequestsRepository;
import by.tux.crm.repository.UserRepository;
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
        requestModel.setManagerId(0);

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
}
