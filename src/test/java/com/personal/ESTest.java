package com.personal;

import com.personal.pojo.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HealApplication.class})
public class ESTest {
    @Autowired
    ElasticsearchTemplate template;

    @Test
    public void createIndex() {
        template.createIndex(Position.class);
        template.putMapping(Position.class);

    }

    @Test
    void deleteIndex() {
        template.deleteIndex(Position.class);
    }



}
