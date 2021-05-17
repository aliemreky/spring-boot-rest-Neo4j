package com.volaid.volaid.controller;

import com.volaid.volaid.entity.AnnouncementComment;
import com.volaid.volaid.service.AnnouncementCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/announcement/{announceId}/comment")
public class AnnouncementCommentController {

    @Autowired
    private AnnouncementCommentService commentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<AnnouncementComment> getAllAnnounceCommentsAction(@PathVariable(value = "announceId") Long announceId) {

        return commentService.getAllAnnouncementComments(announceId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> createAction(@Valid @RequestBody AnnouncementComment request, @PathVariable(value = "announceId") Long announceId) {

        return commentService.create(request, announceId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> updateAction(@PathVariable(value = "id") Long id, @Valid @RequestBody AnnouncementComment request) {

        return commentService.update(id, request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public AnnouncementComment getCommentAction(@PathVariable(value = "id") Long id) {

        return commentService.getComment(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> deleteAction(@PathVariable(value = "id") Long id) {

        return commentService.delete(id);
    }

}
