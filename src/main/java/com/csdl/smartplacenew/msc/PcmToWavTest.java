package com.csdl.smartplacenew.msc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PcmToWavTest {

    public static void main(String[] args) throws Exception {

        String src = System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+
                File.separator+"english"+File.separator+"platenumber"
                +File.separator +"1567925294087"+".pcm";

        File file = new File(src);

        int len_l = (int) file.length();

        byte[] buf = new byte[2048];

        FileInputStream fis = new FileInputStream(file);

        FileOutputStream fos = new FileOutputStream(    System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+
                File.separator+"english"+File.separator+"platenumber"
                +File.separator +"1567925294087"+".wav");

        fos.write(XunfeiLib.getWAVHeader(len_l,8000,2,16));

        len_l = fis.read(buf);
        while (len_l != -1) {
            fos.write(buf, 0, len_l);
            len_l = fis.read(buf);
        }
        fos.flush();
        fos.close();
        fis.close();

//            //删除文件和清除队列信息
       // XunfeiLib.delDone(src);

//        //计算长度
//        int PCMSize = 0;
//        byte[] buf = new byte[1024 * 4];
//        int size = fis.read(buf);
//
//        while (size != -1) {
//            PCMSize += size;
//            size = fis.read(buf);
//        }
//        fis.close();
//
//        //填入参数，比特率等等。这里用的是16位单声道 8000 hz
//        WaveHeader header = new WaveHeader();
//        //长度字段 = 内容的大小（PCMSize) + 头部字段的大小(不包括前面4字节的标识符RIFF以及fileLength本身的4字节)
//        header.fileLength = PCMSize + (44 - 8);
//        header.FmtHdrLeth = 16;
//        header.BitsPerSample = 16;
//        header.Channels = 1;
//        header.FormatTag = 0x0001;
//        header.SamplesPerSec = 8000;
//        header.BlockAlign = (short)(header.Channels * header.BitsPerSample / 8);
//        header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
//        header.DataHdrLeth = PCMSize;
//
////        byte[] h = header.getHeader();
////
////        assert h.length == 44; //WAV标准，头部应该是44字节
//
//        byte[] h = new byte[44];
//
//        long totalDataLen = src.length() + 36;
//        long bitrate = 8000 * 2 * 16;
//
//        h[0] = 'R';
//        h[1] = 'I';
//        h[2] = 'F';
//        h[3] = 'F';
//        h[4] = (byte) (totalDataLen & 0xff);
//        h[5] = (byte) ((totalDataLen >> 8) & 0xff);
//        h[6] = (byte) ((totalDataLen >> 16) & 0xff);
//        h[7] = (byte) ((totalDataLen >> 24) & 0xff);
//        h[8] = 'W';
//        h[9] = 'A';
//        h[10] = 'V';
//        h[11] = 'E';
//        h[12] = 'f';
//        h[13] = 'm';
//        h[14] = 't';
//        h[15] = ' ';
//        h[16] = (byte) 16;
//        h[17] = 0;
//        h[18] = 0;
//        h[19] = 0;
//        h[20] = 1;
//        h[21] = 0;
//        h[22] = (byte) 2;
//        h[23] = 0;
//        h[24] = (byte) (8000 & 0xff);
//        h[25] = (byte) ((8000 >> 8) & 0xff);
//        h[26] = (byte) ((8000 >> 16) & 0xff);
//        h[27] = (byte) ((8000 >> 24) & 0xff);
//        h[28] = (byte) ((bitrate / 8) & 0xff);
//        h[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
//        h[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
//        h[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
//        h[32] = (byte) ((2 * 16) / 8);
//        h[33] = 0;
//        h[34] = 16;
//        h[35] = 0;
//        h[36] = 'd';
//        h[37] = 'a';
//        h[38] = 't';
//        h[39] = 'a';
//        h[40] = (byte) (src.length()  & 0xff);
//        h[41] = (byte) ((src.length() >> 8) & 0xff);
//        h[42] = (byte) ((src.length() >> 16) & 0xff);
//        h[43] = (byte) ((src.length() >> 24) & 0xff);
//
//
//        //write header
//        fos.write(h, 0, h.length);
//        //write data stream
//        fis = new FileInputStream(src);
//        size = fis.read(buf);
//        while (size != -1) {
//            fos.write(buf, 0, size);
//            size = fis.read(buf);
//        }
//        fis.close();
//        fos.close();
//        System.exit(0);
//        System.out.println("Convert OK!");
    }

}
