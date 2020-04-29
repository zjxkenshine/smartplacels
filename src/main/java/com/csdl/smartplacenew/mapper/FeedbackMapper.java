package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedbackMapper {


    List<Feedback> findAllFeedback(@Param("feedbackpath") String feedbackpath, @Param("userid") Integer userid);

    Integer getAllCount(@Param("feedbackpath") String feedbackpath, @Param("userid") Integer userid);


    public void addFeedback(@Param("feedbackpath") String feedbackpath,
                            @Param("feedbacktype") String feedbacktype,
                            @Param("details") String details,
                            @Param("handle") String handle,
                            @Param("submitime") String submitime,
                            @Param("roadsisitua") String roadsisitua,
                            @Param("userid") Integer userid
    );


    public void updateFeddBackFeedbackpath(@Param("newfeedbackpath") String newfeedbackpath,
                                           @Param("oldfeedbackpath") String oldfeedbackpath,
                                           @Param("feedbacktype") String feedbacktype,
                                           @Param("userid") Integer userid);


}
