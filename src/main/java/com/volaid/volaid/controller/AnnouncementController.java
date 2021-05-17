package com.volaid.volaid.controller;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.model.AnnouncementModel;
import com.volaid.volaid.service.AnnouncementCategoryService;
import com.volaid.volaid.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {


    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementCategoryService announcementCategoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Announcement> getAllAnnounceAction() {

        return announcementService.getAllAnnouncement();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@Valid @RequestBody AnnouncementModel request) {

        return announcementService.create(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Announcement getAnnounceAction(@PathVariable(value = "id") Long id) {

        return announcementService.getAnnouncement(id);
    }    

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> updateAction(@Valid @RequestBody AnnouncementModel model) {

        return announcementService.update(model);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteAction(@PathVariable(value = "id") Long id) {

        return announcementService.delete(id);
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Announcement> getAllAnnoncementOfCategoryAction(@PathVariable(value = "categoryId") Long categoryId) {

        return announcementCategoryService.getAllAnnounceListOfCategory(categoryId);
    }

}
