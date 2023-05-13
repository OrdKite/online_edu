package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.BannerQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
    @GetMapping("findAll")
    public R findAllBanner() {
        //调用service的方法实现查询所有的操作
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("rows",list);
    }

    //2 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag=bannerService.save(crmBanner);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //查找banner
    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    //修改banner
    @ApiOperation(value = "修改Banner")
//    @PutMapping("update")
    @PutMapping("update")
    public R update(@RequestBody CrmBanner banner) {
        boolean flag=bannerService.updateById(banner);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean flag=bannerService.updateById(crmBanner);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    //4 条件查询带分页的方法
    @PostMapping("pageBannerCondition/{current}/{limit}")
    public R pageBannerCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) BannerQuery bannerQuery) {//可以为空
        //创建page对象
        Page<CrmBanner> pageBanner = new Page<>(current,limit);

        //构建条件查询
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = bannerQuery.getName();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        bannerService.page(pageBanner,wrapper);

        long total = pageBanner.getTotal();//总记录数
        List<CrmBanner> records = pageBanner.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

}

