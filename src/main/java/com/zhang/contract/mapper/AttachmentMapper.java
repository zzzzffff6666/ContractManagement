package com.zhang.contract.mapper;

import com.zhang.contract.entity.Attachment;

public interface AttachmentMapper {

    public Attachment selectAttachment(String con_name);

    public int insertAttachment(Attachment params);

    public int updateAttachment(Attachment params);

    public int deleteAttachment(String con_name);
}
