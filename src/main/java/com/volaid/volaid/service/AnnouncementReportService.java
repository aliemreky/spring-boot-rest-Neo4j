package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.AnnouncementReport;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.AnnouncementReportModel;
import com.volaid.volaid.repository.AnnouncementReportRepository;
import com.volaid.volaid.repository.AnnouncementRepository;
import com.volaid.volaid.service.GeneralService.AuthenticationUser;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AnnouncementReportService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementReportRepository announcementReportRepository;

    @Autowired
    private AuthenticationUser authenticationUser;

    public Map<String, String> create(AnnouncementReportModel request) {

        Map<String, String> output = new HashMap<>();

        Optional<Announcement> announceOptional = announcementRepository.findById(request.getAnnounceId());

        if(announceOptional.isPresent()){

            AnnouncementReport announceReport = new AnnouncementReport();
            announceReport.setSubject(request.getSubject());
            announceReport.setContent(request.getContent());
            announceReport.setUser(authenticationUser.getAuthUser());
            announceReport.setAnnouncement(announceOptional.get());
            announceReport.setCreatedAt(Calendar.getInstance().getTime());

            try {
                announcementReportRepository.save(announceReport);
            }catch (Exception ex){
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.UNKNOWN_ERROR);
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

        Optional<AnnouncementReport> reportOptional = announcementReportRepository.findById(id);

        if(reportOptional.isPresent()){

            announcementReportRepository.delete(reportOptional.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_DELETE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
        }

        return output;
    }

}
