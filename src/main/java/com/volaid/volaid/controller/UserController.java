package com.volaid.volaid.controller;

import com.volaid.volaid.entity.User;
import com.volaid.volaid.model.UserModel;
import com.volaid.volaid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUser() {

        return userService.getAllUser();
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createUserAction(@Valid @RequestBody UserModel request) {

        return userService.createUser(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserAction(@PathVariable("id") Long id) {

        return userService.getUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> updateAction(@Valid @RequestBody UserModel request, @ModelAttribute("profilePic") MultipartFile postPic) {

        return userService.updateProfileInfo(request, postPic);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteAction(@PathVariable("id") Long id) {

        return userService.activeAndDeactivedUser(id);
    }

    @RequestMapping(value = "/email-verification", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> emailVerification(@QueryParam("token") String token) {

        return userService.emailVerification(token);
    }

}
