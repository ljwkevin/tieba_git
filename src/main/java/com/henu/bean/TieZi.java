package com.henu.bean;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
@SolrDocument(solrCoreName="niu_core")
public class TieZi {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.Id
     *
     * @mbggenerated
     */
    @Id
    @Field
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.TieBaId
     *
     * @mbggenerated
     */
    @Field("TieBaId")
    private Integer tiebaid;
    @Field
    private String tiebaName;

    public String getTiebaName() {
        return tiebaName;
    }

    public void setTiebaName(String tiebaName) {
        this.tiebaName = tiebaName;
    }
    @Field
    private Integer huifuNum;

    public Integer getHuifuNum() {
        return huifuNum;
    }

    public void setHuifuNum(Integer huifuNum) {
        this.huifuNum = huifuNum;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.Title
     *
     * @mbggenerated
     */
    @Field("Title")
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.UserPetName
     *
     * @mbggenerated
     */
    @Field("UserPetName")
    private String userpetname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.ShouCangNum
     *
     * @mbggenerated
     */
    @Field("ShouCangNum")
    private Integer shoucangnum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.LookNum
     *
     * @mbggenerated
     */
    @Field("LookNum")
    private Integer looknum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.CteateTime
     *
     * @mbggenerated
     */
    @Field("CteateTime")
    private Date cteatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.IsJingPin
     *
     * @mbggenerated
     */
    @Field("IsJingPin")
    private Integer isjingpin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.Sort
     *
     * @mbggenerated
     */
    @Field("Sort")
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tiezi.href
     *
     * @mbggenerated
     */
    @Field
    private String href;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.Id
     *
     * @return the value of tiezi.Id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.Id
     *
     * @param id the value for tiezi.Id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.TieBaId
     *
     * @return the value of tiezi.TieBaId
     *
     * @mbggenerated
     */
    public Integer getTiebaid() {
        return tiebaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.TieBaId
     *
     * @param tiebaid the value for tiezi.TieBaId
     *
     * @mbggenerated
     */
    public void setTiebaid(Integer tiebaid) {
        this.tiebaid = tiebaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.Title
     *
     * @return the value of tiezi.Title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.Title
     *
     * @param title the value for tiezi.Title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.UserPetName
     *
     * @return the value of tiezi.UserPetName
     *
     * @mbggenerated
     */
    public String getUserpetname() {
        return userpetname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.UserPetName
     *
     * @param userpetname the value for tiezi.UserPetName
     *
     * @mbggenerated
     */
    public void setUserpetname(String userpetname) {
        this.userpetname = userpetname == null ? null : userpetname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.ShouCangNum
     *
     * @return the value of tiezi.ShouCangNum
     *
     * @mbggenerated
     */
    public Integer getShoucangnum() {
        return shoucangnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.ShouCangNum
     *
     * @param shoucangnum the value for tiezi.ShouCangNum
     *
     * @mbggenerated
     */
    public void setShoucangnum(Integer shoucangnum) {
        this.shoucangnum = shoucangnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.LookNum
     *
     * @return the value of tiezi.LookNum
     *
     * @mbggenerated
     */
    public Integer getLooknum() {
        return looknum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.LookNum
     *
     * @param looknum the value for tiezi.LookNum
     *
     * @mbggenerated
     */
    public void setLooknum(Integer looknum) {
        this.looknum = looknum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.CteateTime
     *
     * @return the value of tiezi.CteateTime
     *
     * @mbggenerated
     */
    public Date getCteatetime() {
        return cteatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.CteateTime
     *
     * @param cteatetime the value for tiezi.CteateTime
     *
     * @mbggenerated
     */
    public void setCteatetime(Date cteatetime) {
        this.cteatetime = cteatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.IsJingPin
     *
     * @return the value of tiezi.IsJingPin
     *
     * @mbggenerated
     */
    public Integer getIsjingpin() {
        return isjingpin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.IsJingPin
     *
     * @param isjingpin the value for tiezi.IsJingPin
     *
     * @mbggenerated
     */
    public void setIsjingpin(Integer isjingpin) {
        this.isjingpin = isjingpin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.Sort
     *
     * @return the value of tiezi.Sort
     *
     * @mbggenerated
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.Sort
     *
     * @param sort the value for tiezi.Sort
     *
     * @mbggenerated
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tiezi.href
     *
     * @return the value of tiezi.href
     *
     * @mbggenerated
     */
    public String getHref() {
        return href;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tiezi.href
     *
     * @param href the value for tiezi.href
     *
     * @mbggenerated
     */
    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }
}