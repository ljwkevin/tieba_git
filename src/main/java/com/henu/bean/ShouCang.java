package com.henu.bean;

import java.util.Date;

public class ShouCang extends ShouCangKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoucang.ShouCangTime
     *
     * @mbggenerated
     */
    private Date shoucangtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoucang.ZuoZhePetName
     *
     * @mbggenerated
     */
    private String zuozhepetname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoucang.BiaoQian
     *
     * @mbggenerated
     */
    private String biaoqian;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoucang.UpdateState
     *
     * @mbggenerated
     */
    private Integer updatestate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoucang.UpdateTime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoucang.ShouCangTime
     *
     * @return the value of shoucang.ShouCangTime
     *
     * @mbggenerated
     */
    public Date getShoucangtime() {
        return shoucangtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoucang.ShouCangTime
     *
     * @param shoucangtime the value for shoucang.ShouCangTime
     *
     * @mbggenerated
     */
    public void setShoucangtime(Date shoucangtime) {
        this.shoucangtime = shoucangtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoucang.ZuoZhePetName
     *
     * @return the value of shoucang.ZuoZhePetName
     *
     * @mbggenerated
     */
    public String getZuozhepetname() {
        return zuozhepetname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoucang.ZuoZhePetName
     *
     * @param zuozhepetname the value for shoucang.ZuoZhePetName
     *
     * @mbggenerated
     */
    public void setZuozhepetname(String zuozhepetname) {
        this.zuozhepetname = zuozhepetname == null ? null : zuozhepetname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoucang.BiaoQian
     *
     * @return the value of shoucang.BiaoQian
     *
     * @mbggenerated
     */
    public String getBiaoqian() {
        return biaoqian;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoucang.BiaoQian
     *
     * @param biaoqian the value for shoucang.BiaoQian
     *
     * @mbggenerated
     */
    public void setBiaoqian(String biaoqian) {
        this.biaoqian = biaoqian == null ? null : biaoqian.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoucang.UpdateState
     *
     * @return the value of shoucang.UpdateState
     *
     * @mbggenerated
     */
    public Integer getUpdatestate() {
        return updatestate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoucang.UpdateState
     *
     * @param updatestate the value for shoucang.UpdateState
     *
     * @mbggenerated
     */
    public void setUpdatestate(Integer updatestate) {
        this.updatestate = updatestate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoucang.UpdateTime
     *
     * @return the value of shoucang.UpdateTime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoucang.UpdateTime
     *
     * @param updatetime the value for shoucang.UpdateTime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}