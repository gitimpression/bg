package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.Notice;
import com.bg.entity.User;
import com.bg.service.NoticeService;
import com.bg.service.UserService;
import com.bg.util.ComRet;
import com.bg.util.DateTimeUtil;
import com.bg.util.JwtUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 公告
 * @author ctp
 * @date 2022/11/30 19:51
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;
    @Resource
    private UserService userService;

    @Log("查询分页公告")
    @GetMapping
    public ComRet getAllNoticePage(Integer pageNum, Integer pageSize, String keyword){
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1){
            return ComRet.fail("参数有误");
        }else{
            PageInfo<Notice> pageInfo;
            if (StringUtils.isEmpty(keyword)){
                pageInfo = noticeService.getAllNoticePage(pageNum, pageSize);
            }else{
                pageInfo = noticeService.searchKeyword(keyword, pageNum, pageSize);
            }
            return ComRet.ok().add("pageInfo", pageInfo);
        }
    }

    @Log("查询单挑公告详情")
    @GetMapping("/detail")
    public ComRet getNoticeById(Long noticeId, Boolean isVisited){// "36&isVisited=false" 出要处理
        if (noticeId == null || noticeId < 1){
            return ComRet.fail("参数有误");
        }
        isVisited = isVisited == null ? false : isVisited;
        Notice notice = noticeService.getNoticeById(noticeId, isVisited);
        return ComRet.ok().add("notice", notice);
    }

    @Log("发布公告")
    @PutMapping
    public ComRet insertNotice(@RequestHeader Map<String,String> headers,
                               @RequestBody Notice notice){
        // TODO 权限校验
        if (notice == null){
            return ComRet.fail("参数有误");
        }
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        User user = userService.getUserById(userId);
        if (user == null){
            return ComRet.fail("发布失败");
        }
        notice.setPublisherId(user.getId());
        notice.setCreateTime(DateTimeUtil.getDataTime());
        notice.setVisits(0L);
        notice.setPublisherName(user.getUsername());
        boolean b = noticeService.insertNotice(notice);
        if (b){
            return ComRet.ok("发布成功！");
        }else{
            return ComRet.fail("发布失败");
        }
    }

    @Log("修改公告")
    @PostMapping
    public ComRet updateNotice(@RequestBody Notice notice){
        // TODO 权限校验
        if (notice == null){
            return ComRet.fail("参数有误");
        }

        boolean b = noticeService.updateNotice(notice);
        if (b){
            return ComRet.ok("修改成功！");
        }else{
            return ComRet.fail("修改失败");
        }
    }

    @Log("删除公告")
    @DeleteMapping
    public ComRet deleteNotice(@RequestBody Notice notice){
        if (notice.getId() == null){
            return ComRet.fail("参数有误");
        }
        boolean b = noticeService.deleteNotice(notice.getId());
        if (b){
            return ComRet.ok("删除成功！");
        }else{
            return ComRet.fail("删除失败");
        }
    }

    @Log("批量删除公告")
    @DeleteMapping("/batch")
    public ComRet deleteNoticeBatch(@RequestBody Map<String,List<Long>> body){
        List<Long> idList = body.get("idList");
        // TODO 权限校验
        if (idList == null || idList.size() == 0){
            return ComRet.fail("参数有误");
        }
        Long batch = noticeService.deleteNoticeBatch(idList);
        if (batch > 0){
            return ComRet.ok("删除成功！");
        }else{
            return ComRet.fail("删除失败");
        }
    }

}
