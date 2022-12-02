package com.bg.service.impl;

import com.bg.entity.Notice;
import com.bg.mapper.NoticeMapper;
import com.bg.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/11/30 19:54
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Notice getNoticeById(Long noticeId,Boolean isVisited) {
        if (!isVisited){ // 已经访问过
            noticeMapper.updateNoticeVisits(noticeId);
        }
        return noticeMapper.getNoticeById(noticeId);
    }

    @Override
    public PageInfo<Notice> getAllNoticePage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> noticeList = noticeMapper.getAllNoticePage();
        return new PageInfo<>(noticeList, 5);
    }

    @Override
    public boolean insertNotice(Notice notice) {

        return noticeMapper.insertNotice(notice);
    }

    @Override
    public boolean deleteNotice(Long noticeId) {
        return noticeMapper.deleteNotice(noticeId);
    }

    @Override
    public Long deleteNoticeBatch(List<Long> idList) {
        return noticeMapper.deleteNoticeBatch(idList);
    }

    @Override
    public boolean updateNotice(Notice notice) {
        return noticeMapper.updateNotice(notice);
    }

    @Override
    public PageInfo<Notice> searchKeyword(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> noticeList = noticeMapper.searchKeyword(keyword);
        return new PageInfo<>(noticeList, 5);
    }
}
