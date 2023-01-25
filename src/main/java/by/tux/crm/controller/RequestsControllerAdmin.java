package by.tux.crm.controller;

import by.tux.crm.configuration.User;
import by.tux.crm.models.RequestModel;
import by.tux.crm.repository.RequestsRepository;
import by.tux.crm.repository.UserRepository;
import by.tux.crm.services.RequestsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/requests/admin")
public class RequestsControllerAdmin {
    private final RequestsRepository requestsRepository;
    private final RequestsService requestsService;
    private final UserRepository userRepository;

    public RequestsControllerAdmin(RequestsRepository requestsRepository, RequestsService requestsService, UserRepository userRepository) {
        this.requestsRepository = requestsRepository;
        this.requestsService = requestsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/my")
    public String myRequests(Principal principal, Model model){
        User currentUser = userRepository.findUserByLogin(principal.getName());
        List<RequestModel> requestModel = requestsService.getUserRequests(currentUser.getId());
        if (requestModel==null)
            return "requests/empty";
        model.addAttribute("requestModel", requestModel);
        return "requests/my";
    }

    @GetMapping("/{id}")
    public String detailRequest(@PathVariable long id,
                                Model model) {
        RequestModel requestModel = requestsService.getRequest(id);
        model.addAttribute("model", requestModel);
        return "requests/detail";
    }

    @GetMapping("/add")
    public String addRequest() {
        return "requests/add";
    }

    @PostMapping("/add")
    public String addRequest(@RequestParam(value = "title")  String title,
                             @RequestParam(value = "text") String text,
                             @RequestParam(value = "login") String login){
        requestsService.addResponse(title,text,login);
        return "redirect:/";
    }
}
