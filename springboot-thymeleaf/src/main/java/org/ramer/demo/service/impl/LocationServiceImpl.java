package org.ramer.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.Location;
import org.ramer.demo.repository.LocationRepository;
import org.ramer.demo.service.LocationService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService{
    @Resource
    private LocationRepository locationRepository;

    @Override
    public Location saveOrUpdate(Location location) {
        return locationRepository.saveAndFlush(location);
    }

    @Override
    public Page<Location> getByPage(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page, size, sort);
        return locationRepository.findAll(pageable);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
    }
}
