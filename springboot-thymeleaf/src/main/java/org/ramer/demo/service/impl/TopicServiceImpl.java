package org.ramer.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.Topic;
import org.ramer.demo.repository.TopicRepository;
import org.ramer.demo.service.TopicService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService{
    @Resource
    private TopicRepository topicRepository;

    @Override
    public Topic saveOrUpdate(Topic topic) {
        return topicRepository.saveAndFlush(topic);
    }

    @Override
    public Page<Topic> getByPage(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page, size, sort);
        return topicRepository.findAll(pageable);
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
    }
}
