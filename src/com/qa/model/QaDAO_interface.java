package com.qa.model;

import java.util.List;


public interface QaDAO_interface {
    public void insert(QaVO qaVO);
    public void update(QaVO qaVO);
    public void delete(String qa_no);
    public QaVO findByPrimaryKey(String qa_no);
    public List<QaVO> getAll();
    public List<QaVO> getByProd(String prod_no);
}
