package com.zhang.contract.service;

import com.zhang.contract.entity.Attachment;
import com.zhang.contract.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttachmentService {

    @Resource
    private AttachmentMapper attachmentMapper;

    public Attachment selectAttachment(int con_id) {
        return attachmentMapper.selectAttachment(con_id);
    }

    public int insertAttachment(Attachment params) {
        return attachmentMapper.insertAttachment(params);
    }

    public int updateAttachment(Attachment params) {
        return attachmentMapper.updateAttachment(params);
    }

    public int deleteAttachment(int con_id) {
        return attachmentMapper.deleteAttachment(con_id);
    }
}
