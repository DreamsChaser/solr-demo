package cn.com.sirius.all.domain;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author wangdi9
 * @date 2019/8/16 11:14
 */
@SolrDocument(solrCoreName = "book_core")
public class Book {

    @Id
    @Field
    private String id;

    @Field
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
