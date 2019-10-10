package com.isoft.community.service;

import com.isoft.community.dto.PaginationDTO;
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
    public PaginationDTO list(Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = qustionMapper.count();     //查询到总的问题数
        paginationDTO.setPagination(totalCount, page, size);          //通过该方法直接将所有需要展示的元素计算出来，不是用bootstrap的分页

        //如果页码数>totalPage，则为最后一页
        if (page >= paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        //如果页码数<1，则为第一页
        if (page < 1) {
            page = 1;
        }

        //size*(page-1)
        Integer offset = size*(page-1);                               //offset(分隔数),page是第几页，size是每一页所占的条数

        List<Question> questions = qustionMapper.List(offset , size);        //通过questionMapper的list()查到所有的Question对象
        List<QuestionDTO> QuestionDTOList = new ArrayList<>();

        for(Question question : questions){
            User user = userMapper.findByID(question.getCreator());   //通过循环question对象找到里面的Creator属性,用userMapper的findByID()方法找到对应的id
            QuestionDTO questionDTO = new QuestionDTO();              //实例化QuestionDTO
            BeanUtils.copyProperties(question , questionDTO);         //用java的BeanUtils.copyProperties()方法将得到的question对象的属性值复制给questionDTO对象
            questionDTO.setUser(user);                                //再将通过userMapper的findByID方法获取的user对象放置到questionDTO里面的user对象中
            QuestionDTOList.add(questionDTO);                         //将得到的questionDTO对象添加到QuestionDTOList集合中
        }
        paginationDTO.setQuestions(QuestionDTOList);    //paginationDTO里面有questionDTO集合，因此将QuestionDTOList导入其中


        return paginationDTO;
    }
}
