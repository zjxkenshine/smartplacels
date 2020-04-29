package com.csdl.smartplacenew.controller;

import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.msc.XunfeiLib;
import com.csdl.smartplacenew.pojo.*;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "讯飞管理接口")
@RestController
@RequestMapping("/Voice")
public class VoiceController {

    @Resource
    AudioMapper audioMapper;

    @Resource
    PlatenumberMapper platenumberMapper;

    @Resource
    RoadMapper roadMapper;

    @Resource
    RoadnumberMapper roadnumberMapper;

    @Resource
    ScenicspotMapper scenicspotMapper;

    @Resource
    VillagenumberMapper villagenumberMapper;

    @Resource
    UserMapper userMapper;

    private static final String hostUrl = "https://tts-api.xfyun.cn/v2/tts"; //http url 不支持解析 ws/wss schema
    private static final String appid ="5d71d3cc";//到控制台-语音合成页面获取
    private static final String apiSecret ="a772d33e82d2cd92c3e955cd121d7c03";//到控制台-语音合成页面获取
    private static final String apiKey = "1b5372d01f87fdc6d655c9bd7d3adc8d";//到控制台-语音合成页面获取

    public static final Gson json = new Gson();

    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("GET ").append(url.getPath()).append(" HTTP/1.1");
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        String authorization = String.format("hmac username=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();
        return httpUrl.toString();
    }

    String  type=null;

    @ApiOperation(value = "添加国语音频", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "data", value = "声音类型", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "platenumberId", value = "门牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路Id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addMandarin")
    private ResultVO addMandarin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");//解决乱码

        String data=request.getParameter("data");

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();



        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;


        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){

            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

                type="platenumber";

                platenumberMapper.updateMandarinword(data,platenumberId,currentUserId);

        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){

            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));

                type="roadnumber";

                roadnumberMapper.updateMandarinword(data,roadnumberId,currentUserId);

        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {

            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));

                type = "villagenumber";

                villagenumberMapper.updateMandarinword(data,villagenumberId,currentUserId);

        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId =Integer.valueOf( request.getParameter("scenicspotId"));

                type = "scenicspotnumber";

                scenicspotMapper.updateMandarinword(data,scenicspotId,currentUserId);

        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {

            roadId =Integer.valueOf( request.getParameter("roadId"));

                type = "road";
                roadMapper.updateMandarinword(data,roadId,currentUserId);

        }


        String imgVirtualPath=null;


        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        //将url中的 schema http://和https://分别替换为ws:// 和 wss://
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request requestokt = new Request.Builder().url(url).build();
        // 存放音频的文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String times=System.currentTimeMillis()+"";

        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
                +File.separator +times+".pcm";

        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(f);


        WebSocket webSocket = client.newWebSocket(requestokt, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //发送数据
                JsonObject frame = new JsonObject();
                JsonObject business = new JsonObject();
                JsonObject common = new JsonObject();
                JsonObject datajson = new JsonObject();
                // 填充common
                common.addProperty("app_id", appid);
                //填充business
                business.addProperty("aue", "raw");
                business.addProperty("tte", "UTF8");//小语种必须使用UNICODE编码
                business.addProperty("ent", "intp65");
                business.addProperty("vcn", "xiaoyan");//到控制台-我的应用-语音合成-添加试用或购买发音人，添加后即显示该发音人参数值，若试用未添加的发音人会报错11200
                business.addProperty("pitch", 50);
                business.addProperty("speed", 50);
                business.addProperty("volume", 100);
                //填充data
                datajson.addProperty("status", 2);//固定位2
                try {
                    datajson.addProperty("text", Base64.getEncoder().encodeToString(data.getBytes("utf8")));
                    //使用小语种须使用下面的代码，此处的unicode指的是 utf16小端的编码方式，即"UTF-16LE"”
                    //data.addProperty("text", Base64.getEncoder().encodeToString(text.getBytes("UTF-16LE")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //填充frame
                frame.add("common", common);
                frame.add("business", business);
                frame.add("data", datajson);
                webSocket.send(frame.toString());
            }



            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //处理返回数据
            //    System.out.println("receive=>" + text);
                VoiceController.ResponseData resp = null;
                try {
                    resp = json.fromJson(text, VoiceController.ResponseData.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (resp != null) {
                    if (resp.getCode() != 0) {
                       // System.out.println("error=>" + resp.getMessage() + " sid=" + resp.getSid());
                        return;
                    }
                    if (resp.getData() != null) {
                        String result = resp.getData().audio;
                        byte[] audio = Base64.getDecoder().decode(result);
                        try {
                            os.write(audio);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (resp.getData().status == 2) {
                            // todo  resp.data.status ==2 说明数据全部返回完毕，可以关闭连接，释放资源
                            System.out.println("session end ");
                            System.out.println("合成的音频文件保存在：" + f.getPath());
                            webSocket.close(1000, "");
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                File file = new File(filename);

                int len_l = (int) file.length();

                byte[] buf = new byte[2048];

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
                            +File.separator +times+".wav");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
                    len_l = fis.read(buf);
                    while (len_l != -1) {
                        fos.write(buf, 0, len_l);
                        len_l = fis.read(buf);
                    }


                    fos.flush();
                    fos.close();
                    fis.close();
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            //            @Override
//            public void onMessage(WebSocket webSocket, ByteString bytes) {
//                super.onMessage(webSocket, bytes);
//            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("socket closing");
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("socket closed");
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);

                System.out.println("connection failed");
            }
        });





//
//        String times=System.currentTimeMillis()+"";
//
//        System.out.println("连续请求---------------------------------------");
//
//
//
//    //    System.out.println(data);
//        //换成你在讯飞申请的APPID
//        SpeechUtility.createUtility("appid=5d71d3cc");
//
//        //合成监听器
//        SynthesizeToUriListener synthesizeToUriListener = XunfeiLib.getSynthesize();
//
//        //      String fileName=XunfeiLib.getFileName(times+".pcm",type,language);
//
//        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
//                +File.separator +times+".pcm";
//
//        //   XunfeiLib.delDone(filename);
//
//        //1.创建SpeechSynthesizer对象
//        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
//        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
//        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
//        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0~100
//        mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
//        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
//
//        //3.开始合成
//        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
//        mTts.synthesizeToUri(data,filename ,synthesizeToUriListener);
//
//        //设置最长时间
//        int timeOut=20000;
//        int star=0;
//
//
//        while(!XunfeiLib.checkDone(filename)){
//
//            try {
//                Thread.sleep(1000);
//                star++;
//                if(star>timeOut){
//                    throw new Exception("合成超过"+timeOut+"秒！");
//                }
//            } catch (Exception e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//                break;
//            }
//
//        }
//
//
//
//
//        File file = new File(filename);
//
//        int len_l = (int) file.length();
//
//        byte[] buf = new byte[2048];
//
//        FileInputStream fis = new FileInputStream(file);
//
//        FileOutputStream fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
//                +File.separator +times+".wav");
//
//        fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
//
//        len_l = fis.read(buf);
//        while (len_l != -1) {
//            fos.write(buf, 0, len_l);
//            len_l = fis.read(buf);
//        }
//
//
//        fos.flush();
//        fos.close();
//        fis.close();
//        file.delete();

        //this.sayPlay(filename, request, response);

        imgVirtualPath=ConfigBean.videoVirtualPath+"mandarin/"+type+"/"+times+".wav";


        audioMapper.addAudio(imgVirtualPath,platenumberId,roadnumberId,villagenumberId,scenicspotId,roadId,1,currentUserId);


        return ResultVOUtil.success("添加国语语音成功");

    }







    @ApiOperation(value = "修改国语音频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "声音类型", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateMandarin")
    private ResultVO updateMandarin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        request.setCharacterEncoding("UTF-8");//解决乱码

//        String data= URLDecoder.decode(request.getParameter("data"),"UTF-8");

        String data=request.getParameter("data");


      //  String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

            Platenumber platenumber=platenumberMapper.getNumberplateById(platenumberId,currentUserId);

            List<String> stringList=audioMapper.getAudioByPlatenumberid(platenumberId,currentUserId);

            if(stringList!=null){
                for(int k=0;k<stringList.size();k++){

                    if(stringList.get(k).lastIndexOf("mandarin")!=-1) {

                   //     File file = new File(filename);

                        audioMapper.delAudioByPlatenumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }
            if(platenumberId!=null&&!platenumberId.equals("")){

                type="platenumber";

                platenumberMapper.updateMandarinword(data,platenumberId,currentUserId);


            }
        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));

            Roadnumber roadnumber=roadnumberMapper.getRoadnumberById(roadnumberId,currentUserId);

            List<String> stringList=audioMapper.getAudioByRoadnumberid(roadnumberId,currentUserId);
            if(stringList!=null){
                for(int k=0;k<stringList.size();k++){

                    if(stringList.get(k).lastIndexOf("mandarin")!=-1) {

                        audioMapper.delAudioByRoadnumberUrl(stringList.get(k),currentUserId);
                    }
                }
            }

            if(roadnumberId!=null&&!roadnumberId.equals("")){

                type="roadnumber";
                roadnumberMapper.updateMandarinword(data,roadnumberId,currentUserId);

            }
        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));

            Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(villagenumberId,currentUserId);

            List<String>  stringList=audioMapper.getAudioByVillagenumberid(villagenumberId,currentUserId);

            if(stringList!=null){
                for(int k=0;k<stringList.size();k++) {
                    if (stringList.get(k).lastIndexOf("mandarin") != -1) {
                        audioMapper.delAudioByVillagenumberUrl(stringList.get(k),currentUserId);
                    }
                }

            }
            if (villagenumberId != null && !villagenumberId.equals("")) {

                type = "villagenumber";

                villagenumberMapper.updateMandarinword(data,villagenumberId,currentUserId);

            }
        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

            Scenicspot scenicspot=scenicspotMapper.getScenicspotById(scenicspotId,currentUserId);
            List<String> stringList=audioMapper.getAudioByScenicspotid(scenicspotId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("mandarin") != -1) {
                        audioMapper.delAudioByScenicspotUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (scenicspotId != null && !scenicspotId.equals("")) {

                type = "scenicspotnumber";

                scenicspotMapper.updateMandarinword(data,scenicspotId,currentUserId);

            }
        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {
            roadId = Integer.valueOf(request.getParameter("roadId"));
            Road road=roadMapper.getRoadById(roadId,currentUserId);
            List<String> stringList=audioMapper.getAudioByRoadid(roadId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("mandarin") != -1) {

                        audioMapper.delAudioByRoadUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (roadId != null && !roadId.equals("")) {

                type = "road";

                roadMapper.updateMandarinword(data,roadId,currentUserId);

            }
        }

        String imgVirtualPath=null;

        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        //将url中的 schema http://和https://分别替换为ws:// 和 wss://
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request requestokt = new Request.Builder().url(url).build();
        // 存放音频的文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String times=System.currentTimeMillis()+"";

        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
                +File.separator +times+".pcm";

        File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(f);


        WebSocket webSocket = client.newWebSocket(requestokt, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //发送数据
                JsonObject frame = new JsonObject();
                JsonObject business = new JsonObject();
                JsonObject common = new JsonObject();
                JsonObject datajson = new JsonObject();
                // 填充common
                common.addProperty("app_id", appid);
                //填充business
                business.addProperty("aue", "raw");
                business.addProperty("tte", "UTF8");//小语种必须使用UNICODE编码
                business.addProperty("ent", "intp65");
                business.addProperty("vcn", "xiaoyan");//到控制台-我的应用-语音合成-添加试用或购买发音人，添加后即显示该发音人参数值，若试用未添加的发音人会报错11200
                business.addProperty("pitch", 50);
                business.addProperty("speed", 50);
                business.addProperty("volume", 100);
                //填充data
                datajson.addProperty("status", 2);//固定位2
                try {
                    datajson.addProperty("text", Base64.getEncoder().encodeToString(data.getBytes("utf8")));
                    //使用小语种须使用下面的代码，此处的unicode指的是 utf16小端的编码方式，即"UTF-16LE"”
                    //data.addProperty("text", Base64.getEncoder().encodeToString(text.getBytes("UTF-16LE")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //填充frame
                frame.add("common", common);
                frame.add("business", business);
                frame.add("data", datajson);
                webSocket.send(frame.toString());
            }



            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //处理返回数据
                //    System.out.println("receive=>" + text);
                VoiceController.ResponseData resp = null;
                try {
                    resp = json.fromJson(text, VoiceController.ResponseData.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (resp != null) {
                    if (resp.getCode() != 0) {
                        // System.out.println("error=>" + resp.getMessage() + " sid=" + resp.getSid());
                        return;
                    }
                    if (resp.getData() != null) {
                        String result = resp.getData().audio;
                        byte[] audio = Base64.getDecoder().decode(result);
                        try {
                            os.write(audio);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (resp.getData().status == 2) {
                            // todo  resp.data.status ==2 说明数据全部返回完毕，可以关闭连接，释放资源
                            System.out.println("session end ");
                            System.out.println("合成的音频文件保存在：" + f.getPath());
                            webSocket.close(1000, "");
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                File file = new File(filename);

                int len_l = (int) file.length();

                byte[] buf = new byte[2048];

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
                            +File.separator +times+".wav");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
                    len_l = fis.read(buf);
                    while (len_l != -1) {
                        fos.write(buf, 0, len_l);
                        len_l = fis.read(buf);
                    }


                    fos.flush();
                    fos.close();
                    fis.close();
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            //            @Override
//            public void onMessage(WebSocket webSocket, ByteString bytes) {
//                super.onMessage(webSocket, bytes);
//            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("socket closing");
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("socket closed");
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);

                System.out.println("connection failed");
            }
        });





//        String times=System.currentTimeMillis()+"";
//
//        System.out.println("连续请求---------------------------------------");
//
//
//
//     //   System.out.println(data);
//        //换成你在讯飞申请的APPID
//        SpeechUtility.createUtility("appid=5d71d3cc");
//
//        //合成监听器
//        SynthesizeToUriListener synthesizeToUriListener = XunfeiLib.getSynthesize();
//
//        //      String fileName=XunfeiLib.getFileName(times+".pcm",type,language);
//
//        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
//                +File.separator +times+".pcm";
//
//        //   XunfeiLib.delDone(filename);
//
//        //1.创建SpeechSynthesizer对象
//        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
//        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
//        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
//        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0~100
//        mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
//        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
//
//        //3.开始合成
//        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
//        mTts.synthesizeToUri(data,filename ,synthesizeToUriListener);
//
//        //设置最长时间
//        int timeOut=20000;
//        int star=0;
//
//        //校验文件是否生成
//        while(!XunfeiLib.checkDone(filename)){
//
//            try {
//                Thread.sleep(1000);
//                star++;
//                if(star>timeOut){
//                    throw new Exception("合成超过"+timeOut+"秒！");
//                }
//            } catch (Exception e) {
//                // TODO 自动生成的 catch 块
//                e.printStackTrace();
//                break;
//            }
//
//        }
//
//
//
//
//        File file = new File(filename);
//
//        int len_l = (int) file.length();
//
//        byte[] buf = new byte[2048];
//
//        FileInputStream fis = new FileInputStream(file);
//
//        FileOutputStream fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"mandarin"+File.separator+type
//                +File.separator +times+".wav");
//
//        fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
//
//        len_l = fis.read(buf);
//        while (len_l != -1) {
//            fos.write(buf, 0, len_l);
//            len_l = fis.read(buf);
//        }
//
//        fos.flush();
//        fos.close();
//        fis.close();
//        file.delete();

//        this.sayPlay(filename, request, response);


        imgVirtualPath=ConfigBean.videoVirtualPath+"mandarin/"+type+"/"+times+".wav";


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));
        }else{
            platenumberid=null;
        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));
        }else{
            roadnumberid=null;
        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")){
            villagenumberid=Integer.valueOf(request.getParameter("villagenumberId"));
        }else{
            villagenumberid=null;
        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")){
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));
        }else{
            scenicspotid=null;
        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")){
            roadid=Integer.valueOf(request.getParameter("roadId"));
        }else{
            roadid=null;

        }

        audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);


        return ResultVOUtil.success("修改国语语音成功");


    }






    @ApiOperation(value = "添加英语音频", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "data", value = "声音类型", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "platenumberId", value = "门牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路Id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addEnglish")
    private ResultVO addEnglish( HttpServletRequest request) throws IOException {


        request.setCharacterEncoding("UTF-8");//解决乱码

        String data= URLDecoder.decode(request.getParameter("data"),"UTF-8");

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;


        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){

            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

                type="platenumber";

                platenumberMapper.updateEnglishword(data,platenumberId,currentUserId);

        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));

                type="roadnumber";

                roadnumberMapper.updateEnglishword(data,roadnumberId,currentUserId);

        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));

                type = "villagenumber";

                villagenumberMapper.updateEnglishword(data,villagenumberId,currentUserId);

        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {

            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

                type = "scenicspotnumber";

                scenicspotMapper.updateEnglishword(data,scenicspotId,currentUserId);

        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {

            roadId = Integer.valueOf(request.getParameter("roadId"));

                type = "road";

                roadMapper.updateEnglishword(data,roadId,currentUserId);

        }

        String imgVirtualPath=null;


        String times=System.currentTimeMillis()+"";

        System.out.println("连续请求---------------------------------------");




        System.out.println(data);
        //换成你在讯飞申请的APPID
        SpeechUtility.createUtility("appid=5d71d3cc");

        //合成监听器
        SynthesizeToUriListener synthesizeToUriListener = XunfeiLib.getSynthesize();

        //      String fileName=XunfeiLib.getFileName(times+".pcm",type,language);

        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"english"+File.separator+type
                +File.separator +times+".pcm";

        //   XunfeiLib.delDone(filename);

        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "aisjinger");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "70");//设置语速，范围0~100
        mTts.setParameter(SpeechConstant.PITCH, "80");//设置语调，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100

        //3.开始合成
        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
        mTts.synthesizeToUri(data,filename ,synthesizeToUriListener);

        //设置最长时间
        int timeOut=30;
        int star=0;

      //  校验文件是否生成
        while(!XunfeiLib.checkDone(filename)){

            try {
                Thread.sleep(1000);
                star++;
                if(star>timeOut){
                    throw new Exception("合成超过"+timeOut+"秒！");
                }
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                break;
            }

        }



        File file = new File(filename);

        int len_l = (int) file.length();

        byte[] buf = new byte[2048];

        FileInputStream fis = new FileInputStream(file);

        FileOutputStream fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"english"+File.separator+type
                +File.separator +times+".wav");

        fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));

        len_l = fis.read(buf);
        while (len_l != -1) {
            fos.write(buf, 0, len_l);
            len_l = fis.read(buf);
        }

        fos.flush();
        fos.close();
        fis.close();
        file.delete();

//        this.sayPlay(filename, request, response);


        imgVirtualPath=ConfigBean.videoVirtualPath+"english/"+type+"/"+times+".wav";


//        Integer platenumberid=null;
//        if(request.getParameter("platenumberLonAndLat")!=null&&!request.getParameter("platenumberLonAndLat").equals("")){
//            platenumberid=platenumberMapper.getPlateNumIdByName(request.getParameter("platenumberLonAndLat"),currentUserId);
//        }else{
//            platenumberid=null;
//        }
//
//        Integer roadnumberid=null;
//        if(request.getParameter("roadnumberLonAndLat")!=null&&!request.getParameter("roadnumberLonAndLat").equals("")){
//            roadnumberid=roadnumberMapper.getRoadNumIdByName(request.getParameter("roadnumberLonAndLat"),currentUserId);
//        }else{
//            roadnumberid=null;
//        }
//
//        Integer villagenumberid=null;
//        if(request.getParameter("villagenumberLonAndLat")!=null&&!request.getParameter("villagenumberLonAndLat").equals("")){
//            villagenumberid=villagenumberMapper.getVillageIdByName(request.getParameter("villagenumberLonAndLat"),currentUserId);
//        }else{
//            villagenumberid=null;
//        }
//
//
//        Integer scenicspotid=null;
//        if(request.getParameter("scenicspotLonAndLat")!=null&&!request.getParameter("scenicspotLonAndLat").equals("")){
//            scenicspotid=scenicspotMapper.getScenidcIdByName(request.getParameter("scenicspotLonAndLat"),currentUserId);
//        }else{
//            scenicspotid=null;
//        }
//
//
//        Integer roadid=null;
//        if(request.getParameter("roadLonAndLat")!=null&&!request.getParameter("roadLonAndLat").equals("")){
//            roadid=roadMapper.getRoadIdByName(request.getParameter("roadLonAndLat"),currentUserId);
//        }else{
//            roadid=null;
//
//        }


        audioMapper.addAudio(imgVirtualPath,platenumberId,roadnumberId,villagenumberId,scenicspotId,roadId,1,currentUserId);


        return ResultVOUtil.success("添加英语语音成功");


    }





    @ApiOperation(value = "修改英语音频", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "data", value = "声音类型", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateEnglish")
    private ResultVO updateEnglish( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        request.setCharacterEncoding("UTF-8");//解决乱码

        String data= URLDecoder.decode(request.getParameter("data"),"UTF-8");


        String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

            Platenumber platenumber=platenumberMapper.getNumberplateById(platenumberId,currentUserId);

            List<String> stringList=audioMapper.getAudioByPlatenumberid(platenumberId,currentUserId);

            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByPlatenumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }

            if(platenumberId!=null&&!platenumberId.equals("")){
                type="platenumber";
                platenumberMapper.updateEnglishword(data,platenumberId,currentUserId);
            }
        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));

            Roadnumber  roadnumber=roadnumberMapper.getRoadnumberById(roadnumberId,currentUserId);

            List<String> stringList=audioMapper.getAudioByRoadnumberid(roadnumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByRoadnumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }

            if(roadnumberId!=null&&!roadnumberId.equals("")){
                type="roadnumber";
                roadnumberMapper.updateEnglishword(data,roadnumberId,currentUserId);
            }
        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));
            Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(villagenumberId,currentUserId);
            List<String>  stringList=audioMapper.getAudioByVillagenumberid(villagenumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("english") != -1) {
                        audioMapper.delAudioByVillagenumberUrl(stringList.get(k),currentUserId);
                    }

                }
            }
            if (villagenumberId != null && !villagenumberId.equals("")) {
                type = "villagenumber";
                villagenumberMapper.updateEnglishword(data,villagenumberId,currentUserId);
            }
        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

            Scenicspot scenicspot=scenicspotMapper.getScenicspotById(scenicspotId,currentUserId);

            List<String> stringList=audioMapper.getAudioByScenicspotid(scenicspotId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {
                        audioMapper.delAudioByScenicspotUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (scenicspotId != null && !scenicspotId.equals("")) {
                type = "scenicspotnumber";
                scenicspotMapper.updateEnglishword(data,scenicspotId,currentUserId);
            }
        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {
            roadId = Integer.valueOf(request.getParameter("roadId"));

            Road road=roadMapper.getRoadById(roadId,currentUserId);

            List<String> stringList=audioMapper.getAudioByRoadid(roadId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByRoadUrl(stringList.get(k),currentUserId);
                    }
                }
            }

            if (roadId != null && !roadId.equals("")) {
                type = "road";
                roadMapper.updateEnglishword(data,roadId,currentUserId);
            }
        }


        String imgVirtualPath=null;


        String times=System.currentTimeMillis()+"";

        System.out.println("连续请求---------------------------------------");


        System.out.println(data);
        //换成你在讯飞申请的APPID
        SpeechUtility.createUtility("appid=5d71d3cc");

        //合成监听器
        SynthesizeToUriListener synthesizeToUriListener = XunfeiLib.getSynthesize();

        //      String fileName=XunfeiLib.getFileName(times+".pcm",type,language);

        String  filename=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"english"+File.separator+type
                +File.separator +times+".pcm";

        //   XunfeiLib.delDone(filename);

        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "aisjinger");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "60");//设置语速，范围0~100
        mTts.setParameter(SpeechConstant.PITCH, "54");//设置语调，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100

        //3.开始合成
        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
        mTts.synthesizeToUri(data,filename ,synthesizeToUriListener);

        //设置最长时间
        int timeOut=30;
        int star=0;

        //校验文件是否生成
        while(!XunfeiLib.checkDone(filename)){

            try {
                Thread.sleep(1000);
                star++;
                if(star>timeOut){
                    throw new Exception("合成超过"+timeOut+"秒！");
                }
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                break;
            }

        }



        File file = new File(filename);

        int len_l = (int) file.length();

        byte[] buf = new byte[2048];

        FileInputStream fis = new FileInputStream(file);

        FileOutputStream fos = new FileOutputStream( System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+"english"+File.separator+type
                +File.separator +times+".wav");

        fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));

        len_l = fis.read(buf);
        while (len_l != -1) {
            fos.write(buf, 0, len_l);
            len_l = fis.read(buf);
        }

        fos.flush();
        fos.close();
        fis.close();
        file.delete();

//        this.sayPlay(filename, request, response);


        imgVirtualPath=ConfigBean.videoVirtualPath+"english/"+type+"/"+times+".wav";


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));
        }else{
            platenumberid=null;
        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));
        }else{
            roadnumberid=null;
        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")){
            villagenumberid=Integer.valueOf(request.getParameter("villagenumberId"));
        }else{
            villagenumberid=null;
        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")){
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));
        }else{
            scenicspotid=null;
        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")){
            roadid=Integer.valueOf(request.getParameter("roadId"));
        }else{
            roadid=null;

        }

        audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);


        return ResultVOUtil.success("修改英语语音成功");


    }




//    @ApiOperation(value = "播放pcm音频文件", notes = "")
//    @ApiImplicitParams({
//
//            @ApiImplicitParam(name = "data", value = "声音类型", required = false, dataType = "integer", paramType = "query"),
//
//
//    })
//    @GetMapping("/play")
//    private void play( HttpServletRequest request,HttpServletResponse response) throws IOException {
//
//
//        String data=request.getParameter("data");
//
//        String s="/res/video/mandarin/platenumber/1567839932206.pcm";
//
//        String[] s1=data.split("\\/");
//
//        String language=s1[3];
//
//        String type=s1[4];
//
//        String filaname=s1[5];
//
//
//        String filename2=System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator+language+File.separator+type
//                +File.separator +filaname;
//
//        System.out.println(filename2);
//
//        try{
//
//            response.setHeader("Content-Type", "audio/mpeg");
//            File file = new File(filename2);
//            int len_l = (int) file.length();
//            byte[] buf = new byte[2048];
//            FileInputStream fis = new FileInputStream(file);
//            OutputStream out = response.getOutputStream();
//
//            //写入WAV文件头信息
//            out.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
//
//            len_l = fis.read(buf);
//            while (len_l != -1) {
//                out.write(buf, 0, len_l);
//                len_l = fis.read(buf);
//            }
//            out.flush();
//            out.close();
//            fis.close();
//
////            //删除文件和清除队列信息
//            XunfeiLib.delDone(filename2);
//            //    file.delete();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//
//    }
//
//
//
//
//
//
//    private  void sayPlay (String fileName, HttpServletRequest request, HttpServletResponse response) {
//
//        //输出 wav IO流
//        try{
//
//            response.setHeader("Content-Type", "audio/mpeg");
//            File file = new File(fileName);
//            int len_l = (int) file.length();
//            byte[] buf = new byte[2048];
//            FileInputStream fis = new FileInputStream(file);
//            OutputStream out = response.getOutputStream();
//
//            //写入WAV文件头信息
//            out.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));
//
//            len_l = fis.read(buf);
//            while (len_l != -1) {
//                out.write(buf, 0, len_l);
//                len_l = fis.read(buf);
//            }
//            out.flush();
//            out.close();
//            fis.close();
//
////            //删除文件和清除队列信息
//            XunfeiLib.delDone(fileName);
//            //    file.delete();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//
//
//
//    }
//

    public static class ResponseData {
        private int code;
        private String message;
        private String sid;
        private VoiceController.Data data;
        public int getCode() {
            return code;
        }
        public String getMessage() {
            return this.message;
        }
        public String getSid() {
            return sid;
        }
        public VoiceController.Data getData() {
            return data;
        }
    }
    public static class Data {
        private int status;  //标志音频是否返回结束  status=1，表示后续还有音频返回，status=2表示所有的音频已经返回
        private String audio;  //返回的音频，base64 编码
        private String ced;  // 合成进度
    }




}

