package com.cgn.talent.system.controller;

import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.common.result.Result;
import com.cgn.talent.system.entity.SysDictData;
import com.cgn.talent.system.entity.SysDictType;
import com.cgn.talent.system.service.ISysDictDataService;
import com.cgn.talent.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理Controller
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Api(tags = "字典管理")
@RestController
@RequestMapping("/system/dict")
public class SysDictController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询字典类型列表
     */
    @ApiOperation("查询字典类型列表")
    @GetMapping("/type/list")
    public Result<PageResult<SysDictType>> listType(PageParam pageParam, SysDictType dictType) {
        PageResult<SysDictType> pageResult = dictTypeService.selectDictTypePage(pageParam, dictType);
        return Result.success(pageResult);
    }

    /**
     * 查询所有字典类型
     */
    @ApiOperation("查询所有字典类型")
    @GetMapping("/type/all")
    public Result<List<SysDictType>> allType() {
        List<SysDictType> list = dictTypeService.selectDictTypeAll();
        return Result.success(list);
    }

    /**
     * 获取字典类型详细信息
     */
    @ApiOperation("获取字典类型详细信息")
    @GetMapping("/type/{dictId}")
    public Result<SysDictType> getType(@PathVariable Long dictId) {
        SysDictType dictType = dictTypeService.getById(dictId);
        return Result.success(dictType);
    }

    /**
     * 新增字典类型
     */
    @ApiOperation("新增字典类型")
    @PostMapping("/type")
    public Result<Void> addType(@Validated @RequestBody SysDictType dictType) {
        try {
            dictTypeService.insertDictType(dictType);
            return Result.success("新增成功");
        } catch (Exception e) {
            log.error("新增字典类型失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改字典类型
     */
    @ApiOperation("修改字典类型")
    @PutMapping("/type")
    public Result<Void> editType(@Validated @RequestBody SysDictType dictType) {
        try {
            dictTypeService.updateDictType(dictType);
            return Result.success("修改成功");
        } catch (Exception e) {
            log.error("修改字典类型失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除字典类型
     */
    @ApiOperation("删除字典类型")
    @DeleteMapping("/type/{dictIds}")
    public Result<Void> removeType(@PathVariable Long[] dictIds) {
        try {
            dictTypeService.deleteDictTypeByIds(dictIds);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除字典类型失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 清理字典缓存
     */
    @ApiOperation("清理字典缓存")
    @DeleteMapping("/type/clearCache")
    public Result<Void> clearCache() {
        dictTypeService.clearDictCache();
        return Result.success("缓存清理成功");
    }

    // ======================== 字典数据管理 ========================

    /**
     * 查询字典数据列表
     */
    @ApiOperation("查询字典数据列表")
    @GetMapping("/data/list")
    public Result<PageResult<SysDictData>> listData(PageParam pageParam, SysDictData dictData) {
        PageResult<SysDictData> pageResult = dictDataService.selectDictDataPage(pageParam, dictData);
        return Result.success(pageResult);
    }

    /**
     * 根据字典类型查询字典数据
     */
    @ApiOperation("根据字典类型查询字典数据")
    @GetMapping("/data/type/{dictType}")
    public Result<List<SysDictData>> dictType(@PathVariable String dictType) {
        List<SysDictData> list = dictDataService.selectDictDataByType(dictType);
        return Result.success(list);
    }

    /**
     * 获取字典数据详细信息
     */
    @ApiOperation("获取字典数据详细信息")
    @GetMapping("/data/{dictId}")
    public Result<SysDictData> getData(@PathVariable Long dictId) {
        SysDictData dictData = dictDataService.getById(dictId);
        return Result.success(dictData);
    }

    /**
     * 新增字典数据
     */
    @ApiOperation("新增字典数据")
    @PostMapping("/data")
    public Result<Void> addData(@Validated @RequestBody SysDictData dictData) {
        try {
            dictDataService.insertDictData(dictData);
            return Result.success("新增成功");
        } catch (Exception e) {
            log.error("新增字典数据失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改字典数据
     */
    @ApiOperation("修改字典数据")
    @PutMapping("/data")
    public Result<Void> editData(@Validated @RequestBody SysDictData dictData) {
        try {
            dictDataService.updateDictData(dictData);
            return Result.success("修改成功");
        } catch (Exception e) {
            log.error("修改字典数据失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除字典数据
     */
    @ApiOperation("删除字典数据")
    @DeleteMapping("/data/{dictIds}")
    public Result<Void> removeData(@PathVariable Long[] dictIds) {
        try {
            dictDataService.deleteDictDataByIds(dictIds);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除字典数据失败", e);
            return Result.error(e.getMessage());
        }
    }
}
