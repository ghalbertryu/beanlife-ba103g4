package com.fo_store.model;

import java.util.List;



public interface Fo_storeDAO_interface {
    public void insert(Fo_storeVO fo_storeVO);
    public void update(Fo_storeVO fo_storeVO);
    public void delete(String store_no, String mem_ac);
    public Fo_storeVO findByPrimaryKey(String store_no, String mem_ac);
    public List<Fo_storeVO> getAll();
    public int countByStore(String store_no);
    public List<Fo_storeVO> getByMem(String mem_ac);
}
