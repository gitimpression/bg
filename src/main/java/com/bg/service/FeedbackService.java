package com.bg.service;

import com.bg.entity.Feedback;
import com.github.pagehelper.PageInfo;

/**
 * @author ctp
 * @date 2022/12/4 13:14
 */
public interface FeedbackService {
    PageInfo<Feedback> getFeedbackByPage(Integer pageNum, Integer pageSize);
    boolean insertFeedback(Feedback feedback);
    boolean deleteFeedback(Long feedbackId);
}
