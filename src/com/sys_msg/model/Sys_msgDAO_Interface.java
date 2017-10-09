package com.sys_msg.model;

import java.util.List;

public interface Sys_msgDAO_Interface {
    public void insert(Sys_msgVO sys_msgVO);
    public void update(Sys_msgVO sys_msgVO);
    public void delete(String sys_msg_no);
    public Sys_msgVO findByPrimaryKey(String sys_msg_no);
    public List<Sys_msgVO> getAll();
}
