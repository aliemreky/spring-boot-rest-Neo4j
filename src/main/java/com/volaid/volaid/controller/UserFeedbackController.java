package com.volaid.volaid.controller;

import com.volaid.volaid.model.UserfeedbackModel;
import com.volaid.volaid.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RestController
@RequestMapping("/announcement/user-feedback")
public class UserFeedbackController {


    @Autowired
    private UserFeedbackService userFeedbackService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@Valid @RequestBody UserfeedbackModel request) {

        return userFeedbackService.create(request);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@NotNull Long id) {

        return userFeedbackService.delete(id);
    }


}
