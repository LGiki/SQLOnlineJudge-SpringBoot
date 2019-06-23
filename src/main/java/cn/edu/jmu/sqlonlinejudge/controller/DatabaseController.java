package cn.edu.jmu.sqlonlinejudge.controller;


import cn.edu.jmu.sqlonlinejudge.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;


}
