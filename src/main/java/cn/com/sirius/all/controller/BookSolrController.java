package cn.com.sirius.all.controller;

import cn.com.sirius.all.api.BookSolrService;
import cn.com.sirius.all.domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangdi9
 * @date 2019/8/16 11:35
 */
@Controller
@RequestMapping("/book")
public class BookSolrController {

    @Resource
    BookSolrService bookSolrServiceImpl;

    @RequestMapping("/list")
    public String list(){
        return "book_search";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Book add(@RequestBody Book book){
        bookSolrServiceImpl.add(book);
        return book;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("query") String query){
        bookSolrServiceImpl.delete(query);
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Book> queryAll(){
        return bookSolrServiceImpl.queryAll();
    }

    @RequestMapping("/queryById")
    @ResponseBody
    public Book queryById(@RequestParam("id")String id){
        return bookSolrServiceImpl.queryById(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Book update(@RequestBody Book book){
        return bookSolrServiceImpl.update(book);
    }

    @RequestMapping("/query")
    @ResponseBody
    public List<Book> query(@RequestParam("query")String query){
        return bookSolrServiceImpl.query(query);
    }

}
