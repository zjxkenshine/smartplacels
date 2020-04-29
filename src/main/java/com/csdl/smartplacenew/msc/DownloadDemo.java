package com.csdl.smartplacenew.msc;

import com.iflytek.cloud.speech.*;


public class DownloadDemo {

    public static void main(String[] args) {

        String type=null;

        // 2 将“XXXXXXXX”替换成您申请的APPID
        SpeechUtility.createUtility(SpeechConstant.APPID + "=5d71d3cc");
        // 3.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        // 4.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "aisjiuxu");
        // 设置语速，范围0~100
        mTts.setParameter(SpeechConstant.SPEED, "50");
//        // 设置语调，范围0~100
    //    mTts.setParameter(SpeechConstant.PITCH, "80");
        // 设置音量，范围0~100
//        mTts.setParameter(SpeechConstant.VOLUME, "100");

    //    mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");



    //    mTts.setParameter(SpeechConstant.ENGINE_TYPE , "aisound");

        // 5设置要合成的文本
        String text = "丽水学院（Lishui University）位于浙江省丽水市，由丽水市人民政府举办， " +
                "  是丽水最高学府和唯一的本科院校，" +
                " 其办学历史可追溯到1907年的处州师范学堂。" +
                "2004年5月经教育部批准在丽水师范专科学校和丽水职业技术学院合并的基础上升格更名为丽水学院，" +
                "是浙江省公办全日制普通本科高校。由浙江理工大学牵头，宁波大学、浙江农林大学、" +
                "杭州师范大学等共同参与，对口帮扶丽水学院。 ";
        // 6.开始合成 //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”

        mTts.synthesizeToUri(text, "./tts_test.pcm", synthesizeToUriListener);


    }


    // 1 设置合成监听器
    static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
        // progress为合成进度0~100
        public void onBufferProgress(int progress) {

        }

        // 会话合成完成回调接口
        // uri为合成保存地址，error为错误信息，为null时表示合成会话成功
        public void onSynthesizeCompleted(String uri, SpeechError error) {
        }


        public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
                            Object arg5) {

        }

    };





}









