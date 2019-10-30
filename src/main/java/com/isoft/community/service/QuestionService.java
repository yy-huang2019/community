package com.isoft.community.service;

import com.isoft.community.dto.PaginationDTO;
import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
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
        Integer totalPage;              //总页数

        Integer totalCount = qustionMapper.count();     //查询到总的问题数

        if (totalCount % size == 0) {             //size每一页数量,totalCount总问题数
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //如果页码数>totalPage，则为最后一页
        if (page >= totalPage) {
            page = totalPage;
        }

        //如果页码数<1，则为第一页
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);          //通过该方法直接将所有需要展示的元素计算出来，不是用bootstrap的分页

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

    //个人问题页面的展示
    public PaginationDTO list(Integer user_id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;              //总页数

        Integer totalCount = qustionMapper.countByUserID(user_id);     //查询到总的问题数

        if (totalCount % size == 0) {             //size每一页数量,totalCount总问题数
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //如果页码数>totalPage，则为最后一页
        if (page >= totalPage) {
            page = totalPage;
        }

        //如果页码数<1，则为第一页
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);          //通过该方法直接将所有需要展示的元素计算出来，不是用bootstrap的分页

        //size*(page-1)
        Integer offset = size*(page-1);                               //offset(分隔数),page是第几页，size是每一页所占的条数

        List<Question> questions = qustionMapper.ListByUserId(user_id , offset , size);        //通过questionMapper的list()查到所有的Question对象
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

    //问题的回复传递到questionController
    public QuestionDTO getByID(Integer id) {

        Question question = qustionMapper.getByID(id);             //找到问题的发起人的id

        if (question == null){        //(写该处的原因)最开始通过CustomizeExceptionHandle测试问题页面找到该处问题id为null,则通过自己定义一个exception回传异常信息
            //如果找到的数据不存在，则抛出自定义的异常，CustomizeException的构造方法传ICustomizeErrorCode的参数
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        User user = userMapper.findByID(question.getCreator());   //通过循环question对象找到里面的Creator属性,用userMapper的findByID()方法找到对应的id
        QuestionDTO questionDTO = new QuestionDTO();               //实例化QuestionDTO
        BeanUtils.copyProperties(question,questionDTO);            //将question的属性全部赋值给questionDTO
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){                      //通过是否获得id值来判断是否是新的问题或是原来需要根新的问题
            //创建
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            qustionMapper.create(question);
        }else {
            //更新
            question.setGmt_modified(System.currentTimeMillis());
            int update = qustionMapper.update(question);
            if(update != 1){                             //更新不成功
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);     //如果很多地方都要用到throw该异常，则都需要自定义该参数，这是通过重新封装CustomizeErrorCode
            }
        }
    }

    public void incView(Integer id) {
        Question question = qustionMapper.getByID(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        qustionMapper.incView(id);
    }
}

