package org.ramer.demo.service.impl;


import java.util.List;
import javax.annotation.Resource;
import org.ramer.demo.domain.Privilege;
import org.ramer.demo.repository.PrivilegeRepository;
import org.ramer.demo.service.PrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by RAMER on 6/6/2017.
 */
@Service
public class PrivilegeServicesImpl implements PrivilegeService {
  @Resource
  private PrivilegeRepository privilegeRepository;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Privilege saveOrUpdate(Privilege privilege) {
    return privilegeRepository.saveAndFlush(privilege);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveBatch(List<Privilege> privileges) {
    return privilegeRepository.saveAll(privileges).size() > 0;
  }

}
