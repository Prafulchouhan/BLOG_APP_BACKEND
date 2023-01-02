package com.server.blogappserver.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleImpl {
    @Autowired
    private PostServiceImpl postService;
    @Scheduled(cron = "*/5 * * * * *")
    public void delete(){
//        this.postService.fun();
    }
}
