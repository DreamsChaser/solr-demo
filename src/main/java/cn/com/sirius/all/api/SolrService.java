package cn.com.sirius.all.api;

import java.util.List;


/**
 * @author wangdi9
 * @date 2019/8/15 11:25
 */
public interface SolrService<T> {


    void add(T t);

    void delete(String query);

    T update(T t);

    List<T> query(String query);

    List<T> queryAll();

    T queryById(String id);
}
