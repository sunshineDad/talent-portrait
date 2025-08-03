package com.cgn.talent.core.controller;

import com.cgn.talent.common.result.Result;
import com.cgn.talent.common.page.PageParam;
import com.cgn.talent.common.page.PageResult;
import com.cgn.talent.common.utils.ExcelUtils;
import com.cgn.talent.core.entity.PersonInfo;
import com.cgn.talent.core.service.IPersonInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 人员信息Controller
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
@Api(tags = "人员管理")
@RestController
@RequestMapping("/core/person")
public class PersonInfoController {

    @Autowired
    private IPersonInfoService personInfoService;

    /**
     * 查询人员列表
     */
    @ApiOperation("查询人员列表")
    @GetMapping("/list")
    public Result<PageResult<PersonInfo>> list(PageParam pageParam, PersonInfo personInfo) {
        PageResult<PersonInfo> pageResult = personInfoService.selectPersonPage(pageParam, personInfo);
        return Result.success(pageResult);
    }

    /**
     * 获取人员详细信息（包含所有子表数据）
     */
    @ApiOperation("获取人员详细信息")
    @GetMapping("/{personId}")
    public Result<PersonInfo> getInfo(@PathVariable Long personId) {
        PersonInfo personInfo = personInfoService.selectPersonDetailById(personId);
        return Result.success(personInfo);
    }

    /**
     * 新增人员
     */
    @ApiOperation("新增人员")
    @PostMapping
    public Result<Long> add(@Validated @RequestBody PersonInfo personInfo) {
        try {
            Long personId = personInfoService.insertPerson(personInfo);
            return Result.success("新增成功", personId);
        } catch (Exception e) {
            log.error("新增人员失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改人员
     */
    @ApiOperation("修改人员")
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody PersonInfo personInfo) {
        try {
            personInfoService.updatePerson(personInfo);
            return Result.success("修改成功");
        } catch (Exception e) {
            log.error("修改人员失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除人员
     */
    @ApiOperation("删除人员")
    @DeleteMapping("/{personIds}")
    public Result<Void> remove(@PathVariable Long[] personIds) {
        try {
            personInfoService.deletePersonByIds(personIds);
            return Result.success("删除成功");
        } catch (Exception e) {
            log.error("删除人员失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 导出人员数据
     */
    @ApiOperation("导出人员数据")
    @GetMapping("/export")
    public void export(HttpServletResponse response, PersonInfo personInfo) {
        try {
            List<PersonInfo> list = personInfoService.exportPerson(personInfo);
            ExcelUtils.exportExcel(response, list, "人员数据", PersonInfo.class);
        } catch (Exception e) {
            log.error("导出人员数据失败", e);
        }
    }

    /**
     * 下载导入模板
     */
    @ApiOperation("下载导入模板")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            ExcelUtils.exportExcel(response, null, "人员导入模板", PersonInfo.class);
        } catch (Exception e) {
            log.error("下载模板失败", e);
        }
    }

    /**
     * 导入人员数据
     */
    @ApiOperation("导入人员数据")
    @PostMapping("/importData")
    public Result<String> importData(@RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "updateSupport", defaultValue = "false") boolean updateSupport) {
        try {
            List<PersonInfo> personList = ExcelUtils.importExcel(file, PersonInfo.class);
            String message = personInfoService.importPerson(personList, updateSupport);
            return Result.success(message);
        } catch (Exception e) {
            log.error("导入人员数据失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 统计人员信息
     */
    @ApiOperation("统计人员信息")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics(@RequestParam(required = false) Long teamId) {
        Map<String, Object> statistics = personInfoService.selectPersonStatistics(teamId);
        return Result.success(statistics);
    }

    /**
     * 校验人员编号是否唯一
     */
    @ApiOperation("校验人员编号是否唯一")
    @GetMapping("/checkPersonCode")
    public Result<Boolean> checkPersonCode(PersonInfo personInfo) {
        boolean unique = personInfoService.checkPersonCodeUnique(personInfo);
        return Result.success(unique);
    }

    /**
     * 校验身份证号是否唯一
     */
    @ApiOperation("校验身份证号是否唯一")
    @GetMapping("/checkIdCard")
    public Result<Boolean> checkIdCard(PersonInfo personInfo) {
        boolean unique = personInfoService.checkIdCardUnique(personInfo);
        return Result.success(unique);
    }
}
