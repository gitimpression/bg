package com.bg.mapper;

import com.bg.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/11/30 19:55
 */
public interface NoticeMapper {
    Notice getNoticeById(@Param("noticeId") Long noticeId);
    List<Notice> getAllNoticePage();
    boolean insertNotice(Notice notice);
    boolean deleteNotice(@Param("noticeId") Long noticeId);
    Long deleteNoticeBatch(List<Long> idList);
    boolean updateNotice(Notice notice);
    boolean updateNoticeVisits(@Param("noticeId") Long noticeId);
    List<Notice> searchKeyword(@Param("keyword") String keyword);
}
