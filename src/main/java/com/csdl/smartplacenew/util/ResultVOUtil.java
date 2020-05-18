package com.csdl.smartplacenew.util;

import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(200);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO successBymsg(String msg) {

        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(200);
        resultVO.setMessage(msg);
        return resultVO;
    }


    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(String code, String msg) {
        Integer theCode=new Integer(code);
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(theCode);
        resultVO.setMessage(msg);
        return resultVO;

    }

    public static ResultVO byEnum(CodeMessage code) {
        Integer theCode=new Integer(code.getCode());
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(theCode);
        resultVO.setMessage(code.getMessage());
        return resultVO;

    }

    public static ResultVO byEnum2(CodeMessage code,Object object) {
        Integer theCode=new Integer(code.getCode());
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(theCode);
        resultVO.setMessage(code.getMessage());
        return resultVO;

    }


    public static ResultVO fileSuccess() {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }



}
