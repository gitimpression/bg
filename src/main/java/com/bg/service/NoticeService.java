package com.bg.service;

import com.bg.entity.Notice;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告服务
 * @author ctp
 * @date 2022/11/30 19:53
 */
public interface NoticeService {
    Notice getNoticeById(@Param("noticeId") Long noticeId,Boolean isVisited);
    PageInfo<Notice> getAllNoticePage(Integer pageNum, Integer pageSize);
    boolean insertNotice(Notice notice);
    boolean deleteNotice(Long noticeId);
    Long deleteNoticeBatch(List<Long> idList);
    boolean updateNotice(Notice notice);
    PageInfo<Notice> searchKeyword(String keyword, Integer pageNum, Integer pageSize);
}
