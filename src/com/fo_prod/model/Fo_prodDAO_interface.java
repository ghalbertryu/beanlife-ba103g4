package com.fo_prod.model;

import java.util.List;


public interface Fo_prodDAO_interface {
    public void insert(Fo_prodVO fo_prodVO);
    public void update(Fo_prodVO fo_prodVO);
    public void delete(String prod_no, String mem_ac);
    public Fo_prodVO findByPrimaryKey(String prod_no, String mem_ac);
    public List<Fo_prodVO> getAll();
    public int countByProd(String prod_no);
    public List<Fo_prodVO> getByMem(String mem_ac);
}
