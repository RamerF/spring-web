package org.ramer.demo.service;

import org.ramer.demo.domain.Location;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationService{
    Location saveOrUpdate(Location location);

    Page<Location> getByPage(int page, int size);

    List<Location> getAll();
}
