package com.ys.pa200.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringUtils
{

	/**
	 * @param paramString
	 * @return
	 */
	public static String urlEncoded(String paramString)
	{  
        if (TextUtils.isEmpty(paramString))
        {  
            return "";  
        }  
          
        try  
        {  
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;  
        }  
        catch (Exception localException)
        {  
        	localException.printStackTrace();
        }  
          
        return "";  
    }  
	
	/**
	 * @param paramString
	 * @return
	 */
	public static String urlLDecoded(String paramString)
	{  
        if (TextUtils.isEmpty(paramString))
        {  
            return "";  
        }  
          
        try  
        {  
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;  
        }  
        catch (Exception localException)
        {  
        	localException.printStackTrace();
        }  
          
        return "";  
    }

	/**
	 * 拼接http到白名单url
	 * @param url
	 * @return
     */
	public static String appendWhiteHttpStr(String url)
	{
		// 检测是否http/https协议
		if (url.indexOf("https") >= 0 || url.indexOf("http") >= 0)
		{
			return url;
		}

		// 判断白名单
		if (url.indexOf("99bill") >= 0 || url.indexOf("answern") >= 0
				|| url.indexOf("urtrust") >= 0 || url.indexOf("soargift") >= 0)
		{
			url = "http://" + url;
		}

		return url;
	}

	/**
	 * 拼接指定参数到指定的url
	 * 
	 * @param url
	 * @param param
	 * @param value
	 * @return
	 */
	public static String urlAppendParam(String url, String param, String value)
	{
		// 测试与德晟需要添加HTTP前缀
		url = appendWhiteHttpStr(url);

		// 检测是否http/https协议
		if (url.indexOf("https") < 0 && url.indexOf("http") < 0)
		{
			url = "https://" + url;
		}

		// 检测是否增加参数
		if (url.indexOf("?") >= 0)
		{
			url = splitUrlParamValue(url, param, value);
		}
		else
		{
			url = url + "?" + param + "=" + value;
		}

		return url;
	}

	/**
	 * 处理URL的PARAM和VALUE的分割
	 */
   	private static String splitUrlParamValue(String url, String param, String value)
	{
		// 根据?分割URL和参数部分
		String[] paramStrArray = url.split("\\?");
		if (paramStrArray.length < 2)
		{
			return url;
		}
		// 赋值参数部分
		String paramStr = paramStrArray[1];
		// 根据&分割各个参数部分
		String[] valueArray = paramStr.split("&");
		for (int i = 0 ; i < valueArray.length; i++)
		{
			String valueStr = valueArray[i];
			if (valueStr == null)
			{
				continue;
			}

			// 在通过=拆分键和值
			String[] paramValue = valueStr.split("=");
			if (paramValue.length < 1)
			{
				continue;
			}

			if (paramValue[0].equals(param))
			{
				// 如果参数中存在需要拼接的字段就用这个参数名=value替换 避免出现2个相同的参数字段
				return url.replace(valueStr, param + "=" + value);
			}
		}

		return url + "&" + param + "=" + value;
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicodeToString(String unicode)
	{
		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++)
		{
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		if (str == null)
		{
			return true;
		}
		
		if (str.equals(""))
		{
			return true;
		}
		
		return false;
	}

	@SuppressLint("SimpleDateFormat")
	public static Date stringToDate(String str, String formatStr)
	{
		DateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try
		{
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String parseTimeByPattern(String timeStr, String oldPattern,
											String newPattern)
	{
		String returnStr = "";
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldPattern);

			// SimpleDateFormat的parse(String time)方法将String转换为Date
			Date date = simpleDateFormat.parse(timeStr);
			simpleDateFormat = new SimpleDateFormat(newPattern);

			// SimpleDateFormat的format(Date date)方法将Date转换为String
			returnStr = simpleDateFormat.format(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnStr;
	}
	
	/**
	 * 获取指定format的string
	 * @param format
	 * @param str
	 * @return
	 */
	public static String getFormatStr(String format, Object... str)
	{
		return String.format(format, str);
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	@SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString)
	{
		if (hexString == null || hexString.equals(""))
		{
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++)
		{
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 两位小数字符串转int *100
	 * 
	 * @param str
	 * @return
	 */
	public static String multiplyHundred(String str)
	{
		if (str == null)
		{
			return "";
		}

		String value = (long) (Double.parseDouble(str) * 100) + "";
		return value;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c	char
	 * @return byte
	 */
	private static byte charToByte(char c)
	{
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str, int defaultValue)
	{
		int value = defaultValue;
		try
		{
			value = Integer.parseInt(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str)
	{
		return stringToInt(str, -1);
	}

	/**
	 * 字符串转化为float
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static float stringToFloat(String str, float defaultValue)
	{
		float value = defaultValue;
		if (str == null || str.equals(""))
		{
			return defaultValue;
		}
		
		try
		{
			value = Float.valueOf(str);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// 如果是极限边界数值
		if (Float.isNaN(value) || Float.isInfinite(value))
		{
			return defaultValue;
		}
		
		return value;
	}

	public static float stringToFloat(String str)
	{
		return stringToFloat(str, -1);
	}

	public static long stringToLong(String str)
	{
		return stringToLong(str, -1);
	}

	public static long stringToLong(String str, int defaultValue)
	{
		long value = defaultValue;
		if (str == null || str.equals(""))
		{
			return defaultValue;
		}

		try
		{
			value = Long.parseLong(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return value;
	}

	public static double stringToDouble(String str)
	{
		double value = -1;
		try
		{
			value = Double.valueOf(str);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * 字符串转化为double
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static double stringToDoubleWithDefault(String str, double defaultValue)
	{
		if (TextUtils.isEmpty(str))
		{
			return defaultValue;
		}
		
		double value = defaultValue;
		try
		{
			value = Double.valueOf(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// 如果是极限边界数值
		if (Double.isNaN(value) || Double.isInfinite(value))
		{
			return defaultValue;
		}

		return value;
	}

	/**
	 * 获取指定key的intent传值
	 * 
	 * @param key
	 * @param activity
	 * @return
	 */
	public static String getStringFromBundle(String key, Activity activity)
	{
		if (key == null || key.equals("") || activity == null)
		{
			return "";
		}

		Intent intent = activity.getIntent();
		if (intent == null)
		{
			return "";
		}

		Bundle bundle = intent.getExtras();
		if (bundle == null)
		{
			return "";
		}

		return bundle.getString(key);
	}

	/**
	 * 字符串转MAC 地址
	 * 
	 * @param str
	 * @return
	 */
	public static String str2Mac(String str)
	{
		StringBuilder builder = new StringBuilder();
		if (str != null && str.length() > 0)
		{
			for (int i = 0; i < str.length(); i++)
			{
				builder.append(str.charAt(i));
				if (i % 2 == 1 && i != str.length() - 1)
				{
					builder.append(":");
				}
			}
		}
		return builder.toString();
	}

	/**
	 * 根据jsonString获取指定的Json Object
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T getObjFromJsonString(String json, Class<T> classOfT)
	{
		try
		{
			Gson gson = new Gson();
			T object = gson.fromJson(json, classOfT);

			return object;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static int getIntFromJson(String jsonStr, String key)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
			return jsonObject.getInt(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static boolean getBooleanFromJson(String jsonStr, String key)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
			return jsonObject.getBoolean(key);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public static int getIntFromJson(JSONObject jsonObject, String key)
	{
		try
		{
			return jsonObject.getInt(key);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public static String getStringFromJson(String jsonStr, String key)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
			return jsonObject.getString(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	public static String getStringFromJson(JSONObject jsonObj, String key)
	{
		try
		{
			return jsonObj.getString(key);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	public static JSONObject getJSONObjFromJson(String jsonStr, String key)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
			return jsonObject.getJSONObject(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static <T> ArrayList<T> getArrayFromJson(String jsonStr, String key,
													Class<T> classOfT)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			ArrayList<T> list = new ArrayList<T>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject obj = jsonArray.getJSONObject(i);

				list.add(getObjFromJsonString(obj.toString(), classOfT));
			}

			return list;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/** 获取String 避免空指针的情况 */
	public static String getStringText(String text)
	{
		try
		{
			return TextUtils.isEmpty(text) ? "" : text;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置text到textView 避免空指针的情况
	 * 
	 * @param textView
	 * @param text
	 */
	public static void setStringText(TextView textView, String text)
	{
		if (TextUtils.isEmpty(text) || text.equals(""))
		{
			return;
		}
		if (textView != null)
		{
			textView.setText(text);
		}
	}

	/**
	 * 小写 转大写
	 * 
	 * @param text
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String lowerToUpper(String text)
	{
		String result = "";
		if (TextUtils.isEmpty(text))
		{
			return result;
		}

		try
		{
			result = text.toUpperCase();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = text;
		}
		return result;
	}

	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String Utf8URLdecode(String text)
	{
		try
		{
			return java.net.URLDecoder.decode(text, "utf-8");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/** 
     * 检查手机上是否安装了指定的软件 
     * @param context 
     * @param packageName：应用包名 
     * @return 
     */  
	public static boolean isAvilible(Context context, String packageName)
    {   
        //获取packagemanager   
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息   
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名   
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中   
        if(packageInfos != null){   
            for(int i = 0; i < packageInfos.size(); i++)
            {   
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);   
            }   
        }   
       //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE   
       return packageNames.contains(packageName);  
    }  
	
	public static String stringToLower(String text)
	{
		String resultString = text;
		try
		{
			resultString = resultString.toLowerCase();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultString;
	}

	public static int charToUnSigned(char temp)
	{
		return temp & 0x0ff;
	}
	/*
	* 获取utf8 有多少个字
	* */
	public static int getUtf8Len(String str)
	{
		if (TextUtils.isEmpty(str))
		{
			return 0;
		}
		int len = str.length();
		int count = 0;
		for (int i = 0; i < len; i++)
		{
			char temp = str.charAt(i);
			int a = charToUnSigned(temp);
			if ((a >= 0x00 && a <= 0x7F) || (a >= 0xC0 && a < 0xFD))
			{
				count++;
			}
		}
		return count;
	}
}
