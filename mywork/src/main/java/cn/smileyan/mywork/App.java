package cn.smileyan.mywork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 用来查看某个CSDN的等级，访客，积分
 * @author smileyan 
 * 2019-05-12
 */
public class App {
	/**
	 * 
	 * @param url
	 * @param encoding
	 * @return 返回字符串
	 */
	public static String getHtmlResourceByUrl(String url, String encoding) {
        StringBuffer buffer=new StringBuffer();
        InputStreamReader isr=null;
        try {
            /**
             *  建立网络连接
        	 * 	打开网络连接
             */
            URL urlObj = new URL(url);
            URLConnection uc = urlObj.openConnection();

            /**
             * 从服务器下载源码到本地
             */
            isr =new InputStreamReader(uc.getInputStream(),encoding);//建立文件的输入流
            BufferedReader reader =new BufferedReader(isr);//缓冲

            String line=null;
            boolean flag=false;
            while ((line=reader.readLine())!=null) {
            
                if(line.indexOf("<div class=\"grade-box clearfix\">")!=-1) {
                	 flag=true;
                }
            	if(flag) {
            		buffer.append(line+"\n");	
            	}
            	if(flag&&line.indexOf("</div>")!=-1) {
            		break;
            	}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
            	if(null!=isr)isr.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return buffer.toString();

    }
	
	/**
	 * 读取关键字节
	 * @param text
	 * @param key
	 * @return
	 */
    public static String getValue(String text,String key) {
    	String k = "<dt>" + key + "：</dt>"; 
    	int beginIndex = text.indexOf(k);
    	if(beginIndex != -1) {
    		String str = text.substring(beginIndex);
    		int r = str.indexOf("title");
    		if(r != -1) {
    			String result = str.substring(r);
    			String[] strs = result.split("\"");
    			return strs[1];
    		}
    	}
    	
    	return null;
    }
    
    public static void main( String[] args ) {
    	String htmlString=getHtmlResourceByUrl("https://blog.csdn.net/smileyan9", "utf-8");
    	System.out.print("等级："+getValue(htmlString,"等级").substring(0,2)+"\n");
    	System.out.print("访问："+getValue(htmlString,"访问")+"\n");
    	System.out.print("积分："+getValue(htmlString,"积分")+"\n");
    }
}
