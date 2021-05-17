package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.AnnouncementComment;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.repository.AnnouncementCommentRepository;
import com.volaid.volaid.repository.AnnouncementRepository;
import com.volaid.volaid.service.GeneralService.AuthenticationUser;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnnouncementCommentService {

    @Autowired
    private AnnouncementCommentRepository announcementCommentRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AuthenticationUser authUser;

    public Set<AnnouncementComment> getAllAnnouncementComments(Long announceId) {

        Optional<Announcement> announce = announcementRepository.findById(announceId);

        return announce.get().getAnnouncementComments();
    }

    public Map<String, String> create(AnnouncementComment request, Long announceId){

        Map<String, String> output = new HashMap<>();

        if(request != null){

            Optional<Announcement> announcement = announcementRepository.findById(announceId);

            if(announcement.isPresent()){

                AnnouncementComment newComment = new AnnouncementComment();
                newComment.setUser(authUser.getAuthUser());
                newComment.setAnnouncement(announcement.get());
                newComment.setComment(request.getComment());
                newComment.setCreatedAt(Calendar.getInstance().getTime());

                announcementCommentRepository.save(newComment);

                output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
                output.put(ServiceConstants.RESULT, ServiceConstants.OK);

                return output;

            } else{
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE ,ErrorMessagesConstants.NOT_FOUND);
            }

        }else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE ,ErrorMessagesConstants.INVALID_OBJECT);
        }

    }

    public Map<String, String> update(Long commentId, AnnouncementComment request){

        Map<String, String> output = new HashMap<>();

        Optional<AnnouncementComment> updateableComment = announcementCommentRepository.findById(commentId);

        if(updateableComment.isPresent()){

            AnnouncementComment announcementComment = updateableComment.get();
            announcementComment.setComment(request.getComment());
            announcementCommentRepository.save(announcementComment);

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_UPDATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            return output;

        }else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE ,ErrorMessagesConstants.NOT_FOUND);
        }
    }

    public Map<String, String> delete(Long commentId){

        Map<String, String> output = new HashMap<>();

        Optional<AnnouncementComment> optionalComment = announcementCommentRepository.findById(commentId);

        if(optionalComment.isPresent()){

            announcementCommentRepository.delete(optionalComment.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_UPDATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            return output;

        }else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE ,ErrorMessagesConstants.NOT_FOUND);
        }

    }

    public AnnouncementComment getComment(Long commentId){

        Optional<AnnouncementComment> comment = announcementCommentRepository.findById(commentId);

        return comment.orElse(null);

    }



}
