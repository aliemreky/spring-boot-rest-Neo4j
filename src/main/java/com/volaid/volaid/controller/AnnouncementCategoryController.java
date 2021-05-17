package com.volaid.volaid.controller;

import com.volaid.volaid.entity.AnnouncementCategory;
import com.volaid.volaid.model.AnnounceCateogryModel;
import com.volaid.volaid.service.AnnouncementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/announcement/category")
public class AnnouncementCategoryController {

    @Autowired
    AnnouncementCategoryService announcementCategoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AnnouncementCategory> getAllCategoryAction() {

        return announcementCategoryService.getAllCategoryList();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@Valid @RequestBody AnnounceCateogryModel cateogryModel) {

        return announcementCategoryService.create(cateogryModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> updateAction(@Valid @RequestBody AnnounceCateogryModel cateogryModel) {

        return announcementCategoryService.update(cateogryModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public AnnouncementCategory getSingleCategoryAction(@PathVariable(value = "id") Long id) {

        return announcementCategoryService.getCategory(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteAction(@PathVariable(value = "id") Long id) {

        return announcementCategoryService.delete(id);
    }

}
