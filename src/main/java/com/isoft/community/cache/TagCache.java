package com.isoft.community.cache;

import com.isoft.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//设置标签
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","java","C","C++","Python","html","Node","Ruby","css","objective-c","sass","bash","less"));
        tagDTOS.add(program);

        TagDTO platform = new TagDTO();
        platform.setCategoryName("平台框架");
        platform.setTags(Arrays.asList("django","ruby-on-rails","tornado","laravel","spring"));
        tagDTOS.add(platform);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("apache","负载均衡","windows-server","docker","linux"));
        tagDTOS.add(server);

        TagDTO DB = new TagDTO();
        DB.setCategoryName("数据库");
        DB.setTags(Arrays.asList("MySql","Redis","MongoBb","Oracle","nosql memcached","SqlServer"));
        tagDTOS.add(DB);

        return tagDTOS;
    }

    //判断是否有标签
    public static String filterIvalid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }

}
