package com.apical.oddm.facade.util;

/** 
 * @description 类的描述：
 * @author  作者 : zhuzh
 * @date 创建时间：2017年2月13日 下午2:58:15 
 * @version 1.0 
 */

public class FractionalUtil {

	public static String showToString(int x,int y) throws IllegalArgumentException{
        String res;
        if(x==0&&y==0||y==0){
            throw new IllegalArgumentException("参数错误");
        }
        if(x==0){
            return "0";
        }
        //int g=gcd(x,y);
       // x=x/g;
       // y=y/g;
        if(y==1){
           res=""+x;
        }else {
            res=x+"/"+y;
        }

        return res;
    }

  /* private static int gcd(int x,int y)//求最大公约数
    {
        int r;
        while(y>0)
        {
            r=x%y;
            x=y;
            y=r;
        }
        return x;
    }*/

}
