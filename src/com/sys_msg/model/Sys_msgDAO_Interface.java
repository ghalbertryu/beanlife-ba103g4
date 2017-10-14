package com.sys_msg.model;

import java.util.List;
import java.util.Set;

import com.msg.model.MsgVO;

public interface Sys_msgDAO_Interface {
    public void insert(Sys_msgVO sys_msgVO);
    public void update(Sys_msgVO sys_msgVO);
    public void delete(String sys_msg_no);
    public Sys_msgVO findByPrimaryKey(String sys_msg_no);
    public List<Sys_msgVO> getAll();
    public Set<Sys_msgVO> getAllByMem(String mem_ac);
}
