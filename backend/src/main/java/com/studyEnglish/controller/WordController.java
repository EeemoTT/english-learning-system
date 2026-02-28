package com.studyEnglish.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Word;
import com.studyEnglish.service.IWordService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2024-03-05
 */
@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private IWordService wordService;

    @PostMapping
    public Boolean save(@RequestBody Word word) {
        return wordService.saveOrUpdate(word);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return wordService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return wordService.removeById(id);
    }

    @GetMapping
    public List<Word> selectAll() {
        return wordService.list();
    }

    @GetMapping("/{id}")
    public Word selectById(@PathVariable Integer id) {
        return wordService.getById(id);
    }


    @GetMapping("/page")
    public Page<Word> selectPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String word,
                                 @RequestParam(defaultValue = "") String book) {
        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
        if (!"".equals(word)) {
            queryWrapper.like("word",word);
        }
        if (!"".equals(book)) {
            queryWrapper.like("book",book);
        }

        queryWrapper.orderByDesc("id");
        return wordService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //查询
        List<Word> list = wordService.list();
        //写出到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);


        writer.addHeaderAlias("word","单词");
        writer.addHeaderAlias("ukphone","音标");
        writer.addHeaderAlias("tran","解释");
        writer.addHeaderAlias("book","词书");


        //一次性写出
        writer.write(list,true);

        //响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("单词信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");

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
        List<Word> list = reader.readAll(Word.class);
        wordService.saveBatch(list);
        return true;
    }



}

