package by.tux.helps.controller;

import by.tux.helps.configuration.User;
import by.tux.helps.constants.Status;
import by.tux.helps.models.RequestModel;
import by.tux.helps.models.ResponseModel;
import by.tux.helps.repository.UserRepository;
import by.tux.helps.services.RequestsService;
import by.tux.helps.services.ResponesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/requests/user")
public class RequestsControllerUser {
    private final RequestsService requestsService;
    private final ResponesService responesService;
    private final UserRepository userRepository;

    public RequestsControllerUser(RequestsService requestsService, ResponesService responesService, UserRepository userRepository) {
        this.requestsService = requestsService;
        this.responesService = responesService;
        this.userRepository = userRepository;
    }

    @GetMapping("/my")
    public String myRequests(Principal principal, Model model){
        User currentUser = userRepository.findUserByLogin(principal.getName());
        List<RequestModel> requestList = requestsService.getUserRequests(currentUser.getId());
        model.addAttribute("requestModel", requestList);
        return "requests/user/my";
    }

    @GetMapping("/{id}")
    public String detailRequest(@PathVariable long id,
                                Model model) {
        RequestModel requestModel = requestsService.getRequest(id);
        List<ResponseModel> responseList = responesService.getByRequestId(id);
        model.addAttribute("requestModel", requestModel);
        model.addAttribute("responseList", responseList);
        return "requests/user/detail";
    }
    @PostMapping("/{id}")
    public String detailRequest(@PathVariable long id,
                                @RequestParam(value = "response_id") String responseId,
                                @RequestParam(value = "status") String status){
        responesService.setStatus(responseId,status);
        boolean responseStatus = responesService.checkStatusById(Long.parseLong(responseId));
        if (responseStatus){
            requestsService.setStatus(id,Status.COMPLETED);
        }
        return "requests/user/my";
    }

    @GetMapping("/add")
    public String addRequest() {
        return "requests/user/add";
    }

    @PostMapping("/add")
    public String addRequest(@RequestParam(value = "title")  String title,
                             @RequestParam(value = "text") String text,
                             @RequestParam(value = "login") String login){
        requestsService.addResponse(title,text,login);
        return "redirect:/requests/user/my";
    }
}
