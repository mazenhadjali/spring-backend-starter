package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.AUserDao;
import org.example.backendstarter.ums.services.AUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AUserServiceImpl implements AUserService {

    private final AUserDao userDao;

}
