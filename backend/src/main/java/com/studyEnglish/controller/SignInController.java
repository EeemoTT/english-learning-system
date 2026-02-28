package com.studyEnglish.controller;


import com.studyEnglish.common.Result;
import com.studyEnglish.entity.Course;
import com.studyEnglish.entity.SignIn;
import com.studyEnglish.service.ICourseService;
import com.studyEnglish.service.ISignInService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/signIn")
public class SignInController {
    @Resource
    private ISignInService signInService;

    /**
     * 签到
     */
    @PostMapping("/add")
    public Result add(@RequestBody SignIn signin) {
        signInService.add(signin);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(@RequestParam Integer id) {
        SignIn signin = signInService.selectByUserId(id);
        return Result.success(signin);
    }

    @PostMapping
    public Boolean save(@RequestBody SignIn signIn) {
        return signInService.saveOrUpdate(signIn);
    }
    @PostMapping("/del/batches")
    public boolean deleteInBatches(@RequestBody List<Integer> ids){
        return signInService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return signInService.removeById(id);
    }

    @GetMapping
    public List<SignIn> selectAll() {
        return signInService.list();
    }

    @GetMapping("/{id}")
    public SignIn selectById(@PathVariable Integer id) {
        return signInService.getById(id);
    }
}

