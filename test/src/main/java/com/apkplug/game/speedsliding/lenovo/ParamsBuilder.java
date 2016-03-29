package com.apkplug.game.speedsliding.lenovo;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qinfeng on 16/1/11.
 */
public class  ParamsBuilder {
    private static final String TAG  = "ParamsStringBuilder";
    ArrayList<String> mParams=new ArrayList<>();
    public void add(String name,String value){


        String str = name+"="+value;
        mParams.add(str);
    }
    public String orderByNameAscending(){
        List<String> list = mParams;
        Collections.sort(list);

        String str = "";
        boolean isFirst = true ;
        for (String it : list) {
            if (isFirst){
                str =str+it;
                isFirst = false;
            }else {
                str =str+"&"+it;
            }

        }
        return str;
    }
}
