package com.henu.bean;

public class userTieZi {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_tiezi.tieziId
     *
     * @mbggenerated
     */
    private Integer tieziid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_tiezi.tieziName
     *
     * @mbggenerated
     */
    private String tieziname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_tiezi.userName
     *
     * @mbggenerated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_tiezi.shoucang
     *
     * @mbggenerated
     */
    private Integer shoucang;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_tiezi.tieziId
     *
     * @return the value of user_tiezi.tieziId
     *
     * @mbggenerated
     */
    public Integer getTieziid() {
        return tieziid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_tiezi.tieziId
     *
     * @param tieziid the value for user_tiezi.tieziId
     *
     * @mbggenerated
     */
    public void setTieziid(Integer tieziid) {
        this.tieziid = tieziid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_tiezi.tieziName
     *
     * @return the value of user_tiezi.tieziName
     *
     * @mbggenerated
     */
    public String getTieziname() {
        return tieziname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_tiezi.tieziName
     *
     * @param tieziname the value for user_tiezi.tieziName
     *
     * @mbggenerated
     */
    public void setTieziname(String tieziname) {
        this.tieziname = tieziname == null ? null : tieziname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_tiezi.userName
     *
     * @return the value of user_tiezi.userName
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_tiezi.userName
     *
     * @param username the value for user_tiezi.userName
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_tiezi.shoucang
     *
     * @return the value of user_tiezi.shoucang
     *
     * @mbggenerated
     */
    public Integer getShoucang() {
        return shoucang;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_tiezi.shoucang
     *
     * @param shoucang the value for user_tiezi.shoucang
     *
     * @mbggenerated
     */
    public void setShoucang(Integer shoucang) {
        this.shoucang = shoucang;
    }
}