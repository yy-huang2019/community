package com.isoft.community.service;

import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.mapper.QustionMapper;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.Question;
import com.isoft.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QustionMapper qustionMapper;

    public List<QuestionDTO> list(){
        List<Question> questions = qustionMapper.List();
        List<QuestionDTO> QuestionDTOList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question , questionDTO);
            questionDTO.setUser(user);
            QuestionDTOList.add(questionDTO);
        }
        return QuestionDTOList;
    }
}
