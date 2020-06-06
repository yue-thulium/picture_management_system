package com.management.picture.controller;

import com.management.picture.model.result.ResultModel;
import com.management.picture.util.FastdfsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@RestController
public class CommonController {

    @Autowired
    private FastdfsUtils fastdfsUtils;
    @Autowired
    private ResultModel resultModel;

    @PostMapping("/downloadPicture")
    public ResultModel downloadPicture(@RequestParam("picture") String picture, HttpServletResponse response) throws IOException {
        fastdfsUtils.download(picture,response);
        resultModel.setValue(ResultModel.SUCCESS,200,"正在下载");
        return resultModel;
    }
}
