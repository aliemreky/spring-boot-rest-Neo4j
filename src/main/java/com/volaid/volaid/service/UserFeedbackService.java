package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.User;
import com.volaid.volaid.entity.UserFeedback;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.UserfeedbackModel;
import com.volaid.volaid.repository.AnnouncementRepository;
import com.volaid.volaid.repository.UserFeedbackRepository;
import com.volaid.volaid.repository.UserRepository;
import com.volaid.volaid.service.GeneralService.AuthenticationUser;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserFeedbackService {

    private static Logger LOGGER = Logger.getLogger(UserFeedbackService.class);

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AuthenticationUser authenticationUser;

    public Map<String, String> create(UserfeedbackModel userfeedbackModel){

        Map<String, String> output = new HashMap<>();

        Optional<User> toUserOptional = userRepository.findById(userfeedbackModel.getToUserId());
        Optional<Announcement> announceOptional = announcementRepository.findById(userfeedbackModel.getAnnounceId());

        if(toUserOptional.isPresent() && announceOptional.isPresent()){

            UserFeedback feedback = new UserFeedback();
            feedback.setScore(userfeedbackModel.getScore());
            feedback.setComment(userfeedbackModel.getComment());
            feedback.setFromUser(authenticationUser.getAuthUser());
            feedback.setToUser(toUserOptional.get());
            feedback.setAnnouncement(announceOptional.get());

            try {
                userFeedbackRepository.save(feedback);
            }catch (Exception ex){
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.UNKNOWN_ERROR);
            }

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        }else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.MISSING_CONTENT);
        }

        return output;
    }

    public Map<String, String> delete(Long id){

        Map<String, String> output = new HashMap<>();

        Optional<UserFeedback> userFeedback = userFeedbackRepository.findById(id);

        if(userFeedback.isPresent()){

            userFeedbackRepository.delete(userFeedback.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_DELETE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
        }

        return output;
    }

}
