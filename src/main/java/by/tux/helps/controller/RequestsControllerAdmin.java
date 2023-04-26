package by.tux.helps.controller;

import by.tux.helps.configuration.User;
import by.tux.helps.constants.Status;
import by.tux.helps.models.RequestModel;
import by.tux.helps.models.ResponseModel;
import by.tux.helps.repository.ResponsesRepository;
import by.tux.helps.repository.UserRepository;
import by.tux.helps.services.RequestsService;
import by.tux.helps.services.ResponesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/requests/admin")
public class RequestsControllerAdmin {
    private final RequestsService requestsService;
    private final ResponesService responesService;
    private final UserRepository userRepository;

    public RequestsControllerAdmin(RequestsService requestsService, ResponesService responesService, UserRepository userRepository) {
        this.requestsService = requestsService;
        this.responesService = responesService;
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    public String newRequests(Model model){
        List<RequestModel> requestModel = requestsService.getRequestWithStatus(Status.NEW);
        model.addAttribute("requestModel", requestModel);
        return "requests/admin/new";
    }

    @GetMapping("/my")
    public String myRequests(Principal principal, Model model){
//        User currentUser = userRepository.findUserByLogin(principal.getName());
        List<RequestModel> requestList = requestsService.getRequestWithStatus(Status.CONFIRMATION);
        model.addAttribute("requestList", requestList);
        return "requests/admin/my";
    }

    @GetMapping("/all")
    public String myRequests(Model model){
        List<RequestModel> requestList = requestsService.getAll();
        model.addAttribute("requestList", requestList);
        return "requests/admin/all";
    }

    @GetMapping("/{id}")
    public String detailRequest(@PathVariable long id,
                                Model model) {
        RequestModel requestModel = requestsService.getRequest(id);
        List<ResponseModel> responseList = responesService.getByRequestId(id);
        model.addAttribute("model", requestModel);
        model.addAttribute("responseList", responseList);

        return "requests/admin/detail";
    }

    @PostMapping("/{id}")
    public String addRequest(@PathVariable String id,
                             @RequestParam(value = "text") String text){
        if (responesService.addResponse(Long.parseLong(id), text, Status.AWAITING)) {
            requestsService.setStatus(Long.parseLong(id), Status.CONFIRMATION);
        }
        return "redirect:/requests/admin/my";
    }
}
