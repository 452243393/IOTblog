package com.yj.Controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.yj.entity.Category;
import com.yj.entity.vo.ExcelCategoryVo;
import com.yj.service.CategoryService;
import com.yj.utils.AppHttpCodeEnum;
import com.yj.utils.BeanCopyUtils;
import com.yj.utils.ResponseResult;
import com.yj.entity.vo.CategoryVo;
import com.yj.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.successResult(list);
    }
    @PreAuthorize("@ps.hasPermissions('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
    @GetMapping("/list")
    public ResponseResult queryCategory(Integer pageNum, Integer pageSize, String name, String description, String metaKeywords, String metaDescription, String status){
          return categoryService.queryCategory(pageNum,pageSize,name,description,metaKeywords,metaDescription,status);
    }
}
