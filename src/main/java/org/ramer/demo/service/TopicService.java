package org.ramer.demo.service;

import org.ramer.demo.domain.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService{
    Topic saveOrUpdate(Topic topic);

    Page<Topic> getByPage(int page, int size);

    List<Topic> getAll();
}
