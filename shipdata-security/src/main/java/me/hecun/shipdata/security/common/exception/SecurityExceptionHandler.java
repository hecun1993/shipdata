package me.hecun.shipdata.security.common.exception;

import me.hecun.shipdata.security.common.GeneralResponse;
import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hecun
 * @date 2017/10/26
 */
@ControllerAdvice
public class SecurityExceptionHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @ExceptionHandler(UserNotExistException.class)
    public ModelAndView handlerUserNotExistException() {
        //直接跳转到404页面, 提示用户不存在, 然后, 隔3秒跳转回登录页
        return new ModelAndView(
            "redirect:"
                .concat("http://localhost:8080")
                .concat(securityProperties.getBrowser().getErrorPage())
        );
    }

    @ExceptionHandler(BatchJobException.class)
    public ResponseEntity<GeneralResponse> handlerBatchJobException() {
        GeneralResponse<Object> generalResponse = new GeneralResponse<>(ResponseEnum.BATCH_JOB_ERROR, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataFileException.class)
    public ResponseEntity<GeneralResponse> handlerDataFileException() {
        GeneralResponse<Object> generalResponse = new GeneralResponse<>(ResponseEnum.DATA_FILE_ERROR, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotLoginException.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse> handlerUserNotLogin() {
        GeneralResponse<Object> generalResponse = new GeneralResponse<>(ResponseEnum.USER_NOT_LOGIN, null);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }
}
