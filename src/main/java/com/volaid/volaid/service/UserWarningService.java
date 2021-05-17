package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.User;
import com.volaid.volaid.entity.UserWarning;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.UserWarningModel;
import com.volaid.volaid.repository.AnnouncementRepository;
import com.volaid.volaid.repository.UserRepository;
import com.volaid.volaid.repository.UserWarningRepository;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserWarningService {

    @Autowired
    private UserWarningRepository userWarningRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> create(UserWarningModel request) {

        Map<String, String> output = new HashMap<>();

        Optional<Announcement> announceOptional = announcementRepository.findById(request.getAnnounceId());
        Optional<User> userOptional = userRepository.findById(request.getUserId());

        if(announceOptional.isPresent() && userOptional.isPresent()){

            int countOfUserWarning = userWarningRepository.getCountOfUserWarning(userOptional.get());
            if(countOfUserWarning >= ServiceConstants.MAX_USER_WARNING_COUNT){

                // TODO: user penalty tablosuna kayıt eklenecek. Süre 1 hafta olacak şekilde ayarlanacak.

            } else{
                UserWarning warning = new UserWarning();
                warning.setReason(request.getReason());
                warning.setAnnouncement(announceOptional.get());
                warning.setUser(userOptional.get());

                try {
                    userWarningRepository.save(warning);
                }catch (Exception ex){
                    throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.UNKNOWN_ERROR);
                }
            }

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);
        }else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
        }

        return output;
    }

    public Map<String, String> delete(Long id){

        Map<String, String> output = new HashMap<>();

        Optional<UserWarning> reportOptional = userWarningRepository.findById(id);

        if(reportOptional.isPresent()){

            userWarningRepository.delete(reportOptional.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_DELETE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
        }

        return output;
    }

}
