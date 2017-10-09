package com.msg.model;

import java.util.List;

public interface MsgDAO_interface {

    public void insert(MsgVO msgVO);
    public void update(MsgVO msgVO);
    public void delete(String msg_no);
    public MsgVO findByPrimaryKey(String msg_no);
    public List<MsgVO> getAll();
}
