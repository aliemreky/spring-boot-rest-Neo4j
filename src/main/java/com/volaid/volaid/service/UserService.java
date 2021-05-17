package com.volaid.volaid.service;

import com.volaid.volaid.entity.User;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.UserModel;
import com.volaid.volaid.repository.UserRepository;
import com.volaid.volaid.service.GeneralService.EmailService;
import com.volaid.volaid.service.GeneralService.ImageUploadService;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static Logger LOGGER = Logger.getLogger(UserService.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUploadService imageUpload;

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userList.add(user);
        }

        return userList;
    }

    public Map<String, String> createUser(UserModel request) {

        Map<String, String> output = new HashMap<>();

        if (checkUserExists(request)) {
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.USER_EXISTS);
        }

        String userToken = UUID.randomUUID().toString();

        User newUser = new User();

        newUser.setEmailVerify(false);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setEmailPhoneShow(request.getEmailPhoneShow());
        newUser.setToken(userToken);

        try {
            userRepository.save(newUser);
        }catch (Exception ex){
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ex.getMessage());
        }

        output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.CREATED_USER);
        output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        /*
        emailService.sendSimpleMessage(
                request.getEmail(),
                "Email Doğrulaması",
                "Merhaba " + request.getUsername() + ", hesabını doğrulamak için aşağıdaki linke tıklayınız. " +
                        "http://localhost:8080/api/email-verification?token=" + userToken
        );
        */

        return output;
    }

    public Map<String, String> updateProfileInfo(UserModel request, MultipartFile file) {

        Map<String, String> output = new HashMap<>();

        Optional<User> editUserOptional = userRepository.findById(request.getId());

        if (editUserOptional.isPresent()) {

            User updateUser = editUserOptional.get();

            if (!updateUser.getDeleted()) {

                updateUser.setFirstName(request.getFirstName());
                updateUser.setLastName(request.getLastName());
                updateUser.setPhoneNumber(request.getPhoneNumber());
                updateUser.setUsername(request.getUsername());
                updateUser.setEmailPhoneShow(request.getEmailPhoneShow());

                if (file != null) {
                    updateUser.setProfilePhoto(imageUpload.SingleImageUpload(file));
                } else {
                    updateUser.setProfilePhoto("default_profile_picture.png");
                }

                userRepository.save(updateUser);

                output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
                output.put(ServiceConstants.RESULT, ServiceConstants.OK);

                return output;
            }

            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.USER_DEACTIVATED);

        } else {

            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.USER_NOT_FOUND);
        }

    }

    public Map<String, String> activeAndDeactivedUser(Long id) {

        Map<String, String> output = new HashMap<>();

        Optional<User> editUserOptional = userRepository.findById(id);

        if (editUserOptional.isPresent()) {

            User user = editUserOptional.get();

            if (user.getDeleted() != null && user.getDeleted()) {

                user.setDeleted(false);

            } else {
                user.setDeleted(true);
                user.setDeletedAt(Calendar.getInstance().getTime());
            }

            userRepository.save(user);

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            return output;

        } else {

            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.USER_NOT_FOUND);
        }

    }

    public Map<String, String> emailVerification(String token){

        Map<String, String> output = new HashMap<>();

        if(!StringUtils.isEmpty(token)){

            Optional<User> userOptional = userRepository.findbyToken(token);

            if(userOptional.isPresent()){

                User user = userOptional.get();

                Date startDate = user.getCreatedDate();
                Date endDate = new Date();

                long diff = endDate.getTime() - startDate.getTime();

                long diffHours = TimeUnit.HOURS.toMinutes(diff);

                // 24 saat'den önce email verification yapabilir
                if(diffHours <= 24){

                    user.setEmailVerify(true);
                    userRepository.save(user);

                    output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_PROCESS);
                    output.put(ServiceConstants.RESULT, ServiceConstants.OK);
                }else{
                    output.put(ServiceConstants.MESSAGE, ErrorMessagesConstants.INVALID_PROCESS);
                    output.put(ServiceConstants.RESULT, ServiceConstants.FAIL);
                }

                return output;

            }else{
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.USER_NOT_FOUND);
            }

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.INVALID_OBJECT);
        }
    }

    public User getUser(Long id) {

        if (id > 0) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }

    }

    private Boolean checkUserExists(UserModel user) {

        Integer userCount = userRepository.countUserByEmail(user.getEmail());

        if (userCount > 0) {
            LOGGER.error("The User " + user.getEmail() + " already exists.");
            return true;
        }

        return false;
    }

}
