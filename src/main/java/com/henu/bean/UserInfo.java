package com.henu.bean;

public class UserInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.UserName
     *
     * @mbggenerated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.HeadImgUrl
     *
     * @mbggenerated
     */
    private String headimgurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.signature
     *
     * @mbggenerated
     */
    private String signature;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userinfo.UserPetName
     *
     * @mbggenerated
     */
    private String userpetname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.UserName
     *
     * @return the value of userinfo.UserName
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.UserName
     *
     * @param username the value for userinfo.UserName
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.HeadImgUrl
     *
     * @return the value of userinfo.HeadImgUrl
     *
     * @mbggenerated
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.HeadImgUrl
     *
     * @param headimgurl the value for userinfo.HeadImgUrl
     *
     * @mbggenerated
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.signature
     *
     * @return the value of userinfo.signature
     *
     * @mbggenerated
     */
    public String getSignature() {
        return signature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.signature
     *
     * @param signature the value for userinfo.signature
     *
     * @mbggenerated
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userinfo.UserPetName
     *
     * @return the value of userinfo.UserPetName
     *
     * @mbggenerated
     */
    public String getUserpetname() {
        return userpetname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userinfo.UserPetName
     *
     * @param userpetname the value for userinfo.UserPetName
     *
     * @mbggenerated
     */
    public void setUserpetname(String userpetname) {
        this.userpetname = userpetname == null ? null : userpetname.trim();
    }
}