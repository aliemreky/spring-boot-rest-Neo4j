package com.volaid.volaid.service;

import com.volaid.volaid.entity.Announcement;
import com.volaid.volaid.entity.AnnouncementCategory;
import com.volaid.volaid.exception.ResponseApiError;
import com.volaid.volaid.model.AnnounceCateogryModel;
import com.volaid.volaid.repository.AnnouncementCategoryRepository;
import com.volaid.volaid.util.ErrorMessagesConstants;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.SuccessMessagesConstants;
import org.neo4j.ogm.exception.core.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnnouncementCategoryService {

    @Autowired
    private AnnouncementCategoryRepository categoryRepository;

    public List<AnnouncementCategory> getAllCategoryList() {
        List<AnnouncementCategory> categoryList = new ArrayList<>();
        for (AnnouncementCategory cat : categoryRepository.findAll()) {
            categoryList.add(cat);
        }

        return categoryList;
    }

    public Set<Announcement> getAllAnnounceListOfCategory(Long categoryId){

        Optional<AnnouncementCategory> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {

            return category.get().getAnnouncements();

        } else {
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE,ErrorMessagesConstants.NOT_FOUND);
        }
    }

    public Map<String, String> create(AnnounceCateogryModel cateogryModel) {

        Map<String, String> output = new HashMap<>();

        if (cateogryModel != null && !cateogryModel.getCategoryName().isEmpty()) {

            if (!checkName(cateogryModel.getCategoryName())) {

                AnnouncementCategory newCategory = new AnnouncementCategory();
                newCategory.setCategoryName(cateogryModel.getCategoryName());
                newCategory.setCategoryIcon(cateogryModel.getCategoryIcon());

                categoryRepository.save(newCategory);

                output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
                output.put(ServiceConstants.RESULT, ServiceConstants.OK);

            } else {
                throw new ResponseApiError(ServiceConstants.FAILURE_CODE,ErrorMessagesConstants.CATEGORY_EXISTS);
            }

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE,ErrorMessagesConstants.EMPTY_CONTENT);
        }

        return output;
    }

    public Map<String, String> update(AnnounceCateogryModel cateogryModel) {

        Map<String, String> output = new HashMap<>();

        Optional<AnnouncementCategory> category = categoryRepository.findById(cateogryModel.getId());

        if(category.isPresent()){

            category.get().setCategoryName(cateogryModel.getCategoryName());
            category.get().setCategoryIcon(cateogryModel.getCategoryIcon());

            categoryRepository.save(category.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        } else{
            throw new ResponseApiError(ServiceConstants.FAILURE_CODE, ErrorMessagesConstants.NOT_FOUND);
        }

        return output;

    }

    public AnnouncementCategory getCategory(Long id) {

        Optional<AnnouncementCategory> comment = categoryRepository.findById(id);

        return comment.orElse(null);

    }

    public Map<String, String> delete(Long id){

        Map<String, String> output = new HashMap<>();

        Optional<AnnouncementCategory> optionalcategory = categoryRepository.findById(id);

        if(optionalcategory.isPresent()){

            categoryRepository.delete(optionalcategory.get());

            output.put(ServiceConstants.MESSAGE, SuccessMessagesConstants.SUCCESS_CREATE_PROCESS);
            output.put(ServiceConstants.RESULT, ServiceConstants.OK);

        }else{
            throw new NotFoundException(ErrorMessagesConstants.NOT_FOUND);
        }

        return output;

    }

    private Boolean checkName(String categoryName) {

        Integer countCategory = categoryRepository.countCategoryByCategoryName(categoryName);

        if (countCategory > 0)
            return true;

        return false;
    }

}