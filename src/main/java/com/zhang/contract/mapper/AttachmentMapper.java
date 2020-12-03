package com.zhang.contract.mapper;

import com.zhang.contract.entity.Attachment;

public interface AttachmentMapper {

    public Attachment selectAttachment(int con_id);

    public int insertAttachment(Attachment params);

    public int updateAttachment(Attachment params);

    public int deleteAttachment(int con_id);
}
