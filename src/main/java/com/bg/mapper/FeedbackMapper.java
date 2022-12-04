package com.bg.mapper;

import com.bg.entity.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 13:07
 */
public interface FeedbackMapper {
    List<Feedback> getFeedbackByPage();
    boolean insertFeedback(Feedback feedback);
    boolean deleteFeedback(@Param("feedbackId")Long feedbackId);
}
