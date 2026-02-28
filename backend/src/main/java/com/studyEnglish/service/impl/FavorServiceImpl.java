package com.studyEnglish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.studyEnglish.entity.Favor;
import com.studyEnglish.mapper.FavorMapper;
import com.studyEnglish.service.IFavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EeemoT^T
 * @since 2024-03-02
 */
@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor> implements IFavorService {

}
