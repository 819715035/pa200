package com.ys.pa200.bean;

import com.ys.pa200.utils.UiUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class Patient extends DataSupport implements Serializable{
    private long id;
    private String name = "临时病人";
    private String sex = "未知";
    private String weight;
    private String age = "0";
    private String number = "临时编号";
    private long date =new Date().getTime();
    private ArrayList<CheckProgrem> checkProgrems = new ArrayList<>();

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public long getDate()
    {
        return date;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public ArrayList<CheckProgrem> getCheckProgrems()
    {
        return checkProgrems;
    }

    public void setCheckProgrems(ArrayList<CheckProgrem> checkProgrems)
    {
        this.checkProgrems = checkProgrems;
    }

    @Override
    public String toString()
    {
        return  "  id：" + id +
                "  编号：" + number +
                "  姓名：" + name +
                "  sex：" + sex +
                "  体重：" + weight +
                "  年龄：" + age  +
                "  建表时间："+ UiUtils.formatDatetime(date)
                ;
    }
}
