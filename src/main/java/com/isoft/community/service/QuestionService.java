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


    //组装Qusetion和User的中间层，当一个请求需要多个Mapper(QusetionMapper和UserMapperd组成QusetionDTO(Qusetion中多了User对象获取avatar_url))进行组装时，Service层提供相应的组装功能
    public List<QuestionDTO> list(){
        List<Question> questions = qustionMapper.List();              //通过questionMapper的list()查到所有的Question对象
        List<QuestionDTO> QuestionDTOList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findByID(question.getCreator());   //通过循环question对象找到里面的Creator属性,用userMapper的findByID()方法找到对应的id
            QuestionDTO questionDTO = new QuestionDTO();              //实例化QuestionDTO
            BeanUtils.copyProperties(question , questionDTO);         //用java的BeanUtils.copyProperties()方法将得到的question对象的属性值复制给questionDTO对象
            questionDTO.setUser(user);                                //再将通过userMapper的findByID方法获取的user对象放置到questionDTO里面的user对象中
            QuestionDTOList.add(questionDTO);                         //将得到的questionDTO对象添加到QuestionDTOList集合中
        }
        return QuestionDTOList;
    }
}
