package com.seek.food.util.FileUtil;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRemove {
    //传输完整路径下来
    public static void removeFile(String adder) {
        Path path = Paths.get(adder);
        //检查路径是否存在
        if (!Files.exists(path)){
            throw new BizException(ErrorCodeEnum.TOO_BIG_FILE);
        }
        //删除文件
        if(!path.toFile().delete()){
            throw  new BizException(ErrorCodeEnum.FILE_DELETE_ERROR);
        }
    }
}
