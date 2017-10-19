package com.ys.pa200.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhang on 2017/10/9 0009.
 * description 病例model
 */

public class CaseModel extends BmobObject
{
    private String name;
    private String sex;
    private String age;
    private String weight;
    private String xiangmu;
    private String caseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
