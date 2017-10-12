package com.msg.model;

import java.util.List;
import java.util.Set;

public interface MsgDAO_interface {

    public void insert(MsgVO msgVO);
    public void update(MsgVO msgVO);
    public void delete(String msg_no);
    public MsgVO findByPrimaryKey(String msg_no);
    public List<MsgVO> getAll();
    public Set<String> getAllPairByMem(String mem_ac);
    public Set<MsgVO> getAllByPair(String mem_ac1,String mem_ac2);
}
