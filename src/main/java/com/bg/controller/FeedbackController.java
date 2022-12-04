package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.Feedback;
import com.bg.service.FeedbackService;
import com.bg.util.ComRet;
import com.bg.util.DateTimeUtil;
import com.bg.util.JwtUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/12/4 13:06
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Log("查看反馈")
    @GetMapping
    public ComRet getFeedbackByPage(Integer pageNum, Integer pageSize){
        if (pageNum == null || pageSize == null){
            return ComRet.fail("参数有误");
        }
        PageInfo<Feedback> pageInfo = feedbackService.getFeedbackByPage(pageNum, pageSize);
        return ComRet.ok().add("pageInfo", pageInfo);
    }

    @Log("新增反馈")
    @PutMapping
    public ComRet insertFeedback(@RequestHeader Map<String,String> headers,
                                 @RequestBody Feedback feedback){
        if (feedback == null){
            return ComRet.fail("参数有误");
        }
        String userId = JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY);
        feedback.setUsrId(Long.parseLong(userId));
        feedback.setTime(DateTimeUtil.getDataTime());
        boolean b = feedbackService.insertFeedback(feedback);
        if (b){
            return ComRet.ok("反馈成功");
        }else{
            return ComRet.fail("反馈失败");
        }
    }

    @Log("删除反馈")
    @DeleteMapping
    public ComRet deleteFeedback(@RequestBody Map<String,String> body){
        String feedbackId = body.get("feedbackId");
        if (StringUtils.isEmpty(feedbackId)){
            return ComRet.fail("删除失败，参数有误");
        }
        boolean b = feedbackService.deleteFeedback(Long.parseLong(feedbackId));
        if (b){
            return ComRet.ok("删除成功");
        }else{
            return ComRet.fail("删除失败");
        }
    }
}
