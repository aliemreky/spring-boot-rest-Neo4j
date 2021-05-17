package com.volaid.volaid.controller;

import com.volaid.volaid.model.UserWarningModel;
import com.volaid.volaid.service.UserWarningService;
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
@RequestMapping("/user/warning")
public class UserWarningController {

    @Autowired
    private UserWarningService userWarningService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@Valid @RequestBody UserWarningModel request) {

        return userWarningService.create(request);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@NotNull Long id) {

        return userWarningService.delete(id);
    }

}
