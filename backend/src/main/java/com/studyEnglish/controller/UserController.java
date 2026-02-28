package com.studyEnglish.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.dto.AccountDTO;
import com.studyEnglish.entity.Admin;
import com.studyEnglish.entity.User;
import com.studyEnglish.service.IUserService;
import com.studyEnglish.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public Boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @GetMapping
    public List<User> selectAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User selectById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public Result selectById(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));}

    @GetMapping("/page")
    public Page<User> selectPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username",username);
        }

        queryWrapper.orderByDesc("id");
        return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //查询
        List<User> list = userService.list();
        //写出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);


      writer.addHeaderAlias("username","用户名");
      writer.addHeaderAlias("password","密码");
      writer.addHeaderAlias("nickname","昵称");
      writer.addHeaderAlias("email","邮箱");
      writer.addHeaderAlias("phone","电话");
      writer.addHeaderAlias("createTime","创建");
      writer.addHeaderAlias("updateTime","更新时间");
      writer.addHeaderAlias("role","用户等级");
      writer.addHeaderAlias("avatar","头像");

        //一次性写出
        writer.write(list,true);

        //响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    //excel导入
    @PostMapping("/import")
    public boolean importData(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return true;
    }




}

