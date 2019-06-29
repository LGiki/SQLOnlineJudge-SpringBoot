package cn.edu.jmu.sqlonlinejudge.controller;


import cn.edu.jmu.sqlonlinejudge.model.Database;
import cn.edu.jmu.sqlonlinejudge.service.DatabaseService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * 查询所有数据库
     *
     * @param pageNum  当前页码
     * @param pageSize 页面数据条数
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BasicResponse selectAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            basicResponse.set(200, null, new PageInfo<>(databaseService.selectAll()));
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 查询所有数据库，不分页
     *
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BasicResponse selectAllNoPagination() {
        BasicResponse basicResponse = new BasicResponse();
        try {
            basicResponse.set(200, null, databaseService.selectAll());
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID查询数据库详情
     *
     * @param id 数据库ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BasicResponse selectDatabaseById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            Database database = databaseService.selectById(id);
            if (database != null) {
                basicResponse.set(200, null, database);
            } else {
                basicResponse.set(400, "无此数据库");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID删除数据库
     *
     * @param id 数据库ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BasicResponse deleteDatabaseById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            if (databaseService.deleteById(id) == 1) {
                basicResponse.set(200, "删除成功");
            } else {
                basicResponse.set(400, "删除失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 添加数据库
     *
     * @param database 要添加的数据库对象
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BasicResponse insertDatabase(@RequestBody Database database) {
        BasicResponse basicResponse = new BasicResponse();
        //新数据库要将是否已生成字段设置为 false
        database.setIsCreated(false);
        try {
            if (databaseService.insert(database) == 1) {
                basicResponse.set(200, "数据库添加成功");
            } else {
                basicResponse.set(400, "数据库添加失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());

        }
        return basicResponse;
    }

    /**
     * 通过ID更新数据库
     *
     * @param id       数据库ID
     * @param database 更新的数据库对象
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BasicResponse updateDatabaseById(@PathVariable("id") Integer id, @RequestBody Database database) {
        BasicResponse basicResponse = new BasicResponse();
        //确保要更新的数据库ID为URL中的ID
        database.setId(id);
        //更新数据库要将是否已生成字段设置为 false
        database.setIsCreated(false);
        try {
            if (databaseService.updateByIdSelective(database) == 1) {
                basicResponse.set(200, "数据库更新成功");
            } else {
                basicResponse.set(400, "数据库更新失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 模糊查询数据库
     *
     * @param keyword  关键字
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public BasicResponse selectByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Database> databases = databaseService.selectAllByKeyword(keyword);
            if (databases != null) {
                basicResponse.set(200, null, new PageInfo<>(databases));
            } else {
                basicResponse.set(400, "无符合条件的数据库");
            }
        }catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }
}
