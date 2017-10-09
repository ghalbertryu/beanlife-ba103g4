package com.mgr.model;

import java.util.*;

public interface MgrDAO_interface {
    public void insert(MgrVO mgrVO);
    public void update(MgrVO mgrVO);
    public void delete(String MGR_NO);
    public MgrVO findByPrimaryKey(String MGR_NO);
    public List<MgrVO> getAll();
}
