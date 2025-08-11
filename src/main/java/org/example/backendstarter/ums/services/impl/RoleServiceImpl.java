package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.RoleDao;
import org.example.backendstarter.ums.services.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;


}
