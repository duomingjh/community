package com.luntang.community.community.mapper;

import com.luntang.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    public void create(Question question);
}
