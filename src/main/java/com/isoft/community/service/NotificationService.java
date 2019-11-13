package com.isoft.community.service;

import com.isoft.community.dto.NotificationDTO;
import com.isoft.community.dto.PaginationDTO;
import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.enums.NotificationStatusEnum;
import com.isoft.community.enums.NotificationTypeEnum;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import com.isoft.community.mapper.NotificationMapper;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.Notification;
import com.isoft.community.model.Question;
import com.isoft.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;              //总页数

        Integer totalCount = notificationMapper.countByUserID(id);     //查询到总的问题数

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

        List<Notification> notifications = notificationMapper.ListByUserId(id , offset , size);        //通过questionMapper的list()查到所有的Question对象

        if(notifications.size() == 0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for(Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType_name(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);

        return paginationDTO;
    }

    public Integer unreadCount(Integer userId) {
        int status = NotificationStatusEnum.UNREAD.getStatus();
        return notificationMapper.countUserID(userId,status);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }if(!Objects.equals(notification.getReceiver() , user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setType_name(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
