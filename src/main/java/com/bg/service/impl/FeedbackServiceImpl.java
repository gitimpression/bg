package com.bg.service.impl;

import com.bg.entity.Feedback;
import com.bg.mapper.FeedbackMapper;
import com.bg.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 13:14
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public PageInfo<Feedback> getFeedbackByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Feedback> feedbackList = feedbackMapper.getFeedbackByPage();
        return new PageInfo<>(feedbackList, 5);
    }

    @Override
    public boolean insertFeedback(Feedback feedback) {
        return feedbackMapper.insertFeedback(feedback);
    }

    @Override
    public boolean deleteFeedback(Long feedbackId) {
        return feedbackMapper.deleteFeedback(feedbackId);
    }
}
