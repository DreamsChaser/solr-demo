package cn.com.sirius.all.service;

import cn.com.sirius.all.api.BookSolrService;
import cn.com.sirius.all.domain.Book;
import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangdi9
 * @date 2019/8/16 11:17
 */
@Service
public class BookSolrServiceImpl implements BookSolrService {

    @Autowired
    SolrClient solrClient;

    @Override
    public void add(Book book) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",book.getId());
        document.setField("description",book.getDescription());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String query) {
        try {
            solrClient.deleteByQuery(query);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book update(Book book) {
        try {
            solrClient.addBean(book);
            solrClient.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> query(String query) {
        List<Book> bookList = new ArrayList<Book>();
        SolrQuery solrQuery = new SolrQuery();
        //设置默认搜索的域
        solrQuery.set("df", "description");
        solrQuery.setQuery(query);
        //高亮显示
        solrQuery.setHighlight(true);
        //设置高亮显示的域
        solrQuery.addHighlightField("description");
        //高亮显示前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //后缀
        solrQuery.setHighlightSimplePost("</font>");
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            if (queryResponse == null){
                return null;
            }
            SolrDocumentList solrDocumentList = queryResponse.getResults();
            if (solrDocumentList.isEmpty()){
                return null;
            }
            //获取高亮
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (SolrDocument solrDocument : solrDocumentList){
                Book book;
                List<String> list = map.get(solrDocument.get("id")).get("description");
                if (!CollectionUtils.isEmpty(list)){
                    solrDocument.setField("description",list.get(0));
                }
                String bookStr = JSONUtil.toJSON(solrDocument);
                book = JSON.parseObject(bookStr,Book.class);
                bookList.add(book);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public List<Book> queryAll() {
        List<Book> bookList = new ArrayList<Book>();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            if (queryResponse != null){
                bookList = queryResponse.getBeans(Book.class);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public Book queryById(String id) {
        Book book = null;
        try {
            SolrDocument solrDocument = solrClient.getById(id);
            String str = JSONUtil.toJSON(solrDocument);
            book = JSON.parseObject(str,Book.class);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }
}
