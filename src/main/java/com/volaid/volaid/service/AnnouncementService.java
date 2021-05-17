package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.AnnouncementCategory;
import com.volaid.volaid.entity.AnnouncementPhoto;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.AnnouncementModel;
import com.volaid.volaid.repository.AnnouncementCategoryRepository;
import com.volaid.volaid.repository.AnnouncementPhotoRepository;
import com.volaid.volaid.repository.AnnouncementRepository;
import com.volaid.volaid.service.GeneralService.AuthenticationUser;
import com.volaid.volaid.service.GeneralService.ImageUploadService;
import com.volaid.volaid.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AnnouncementService {

    // private static Logger LOGGER = Logger.getLogger(AnnouncementService.class);

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementPhotoRepository photoRepository;

    @Autowired
    private AnnouncementCategoryRepository categoryRepository;

    @Autowired
    private AuthenticationUser authUser;

    @Autowired
    private ImageUploadService imageUploadService;

    public List<Announcement> getAllAnnouncement() {
        List<Announcement> announcementArrayList = new ArrayList<>();
        for (Announcement announce : announcementRepository.findAll()) {
            announcementArrayList.add(announce);
        }

        return announcementArrayList;
    }

    public Map<String, String> create(AnnouncementModel request) {

        Map<String, String> output = new HashMap<>();

        if (request != null) {

            Announcement newAnnounce = new Announcement();

            newAnnounce.setTitle(request.getTitle());
            newAnnounce.setUser(authUser.getAuthUser());
            newAnnounce.setAnnounceStatus(true);
            newAnnounce.setDeliveryInfo(request.getDeliveryInfo());
            newAnnounce.setDescription(request.getDescription());
            newAnnounce.setLocationName(request.getLocationName());
            newAnnounce.setLocationAddress(request.getLocationAddress());
            newAnnounce.setCreatedAt(Calendar.getInstance().getTime());

            Optional<AnnouncementCategory> categoryOptional = categoryRepository.findById(request.getId());

            if(categoryOptional.isPresent()){
                newAnnounce.setAnnouncementCategory(categoryOptional.get());
            }else{
                newAnnounce.setAnnouncementCategory(null);
            }

            announcementRepository.save(newAnnounce);

            if(request.getPhotos().length > 0) {

                for (MultipartFile file : request.getPhotos()) {

                    AnnouncementPhoto photo = new AnnouncementPhoto();
                    photo.setPhoto(imageUploadService.SingleImageUpload(file));
                    photo.setRealName(file.getOriginalFilename());
                    photo.setAnnouncement(newAnnounce);

                    photoRepository.save(photo);

                }
            }

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            return output;

        } else {
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE,ErrorMessagesConstants.EMPTY_CONTENT);
        }

    }

    public Map<String, String> update(AnnouncementModel request) {

        Map<String, String> output = new HashMap<>();

        if (request.getId() > 0) {

            Optional<Announcement> updateAnnounce = announcementRepository.findById(request.getId());

            if (!updateAnnounce.isPresent()) {
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
            }

            Announcement announce = updateAnnounce.get();

            announce.setTitle(request.getTitle());
            announce.setDescription(request.getDescription());
            announce.setDeliveryInfo(request.getDeliveryInfo());
            announce.setLocationName(request.getLocationName());
            announce.setLocationAddress(request.getLocationAddress());
            announce.setUpdatedAt(Calendar.getInstance().getTime());

            Optional<AnnouncementCategory> categoryOptional = categoryRepository.findById(request.getId());

            if(categoryOptional.isPresent()){
                announce.setAnnouncementCategory(categoryOptional.get());
            }else{
                announce.setAnnouncementCategory(null);
            }

            // TODO: create aşamasında yüklenen resimlerin update'de değişiklik yapılması

            announcementRepository.save(announce);

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_UPDATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            return output;

        } else {
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.INVALID_OBJECT);
        }
    }

    public Map<String, String> delete(Long id) {

        Map<String, String> output = new HashMap<>();

        Optional<Announcement> updateAnnounce = announcementRepository.findById(id);

        Validation.isValid(updateAnnounce);

        updateAnnounce.get().setAnnounceStatus(false);

        output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_UPDATE_PROCESS);
        output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        return output;

    }

    public Announcement getAnnouncement(Long id) {

        if (id > 0) {
            return announcementRepository.findById(id).get();
        } else {
            return null;
        }

    }

}
