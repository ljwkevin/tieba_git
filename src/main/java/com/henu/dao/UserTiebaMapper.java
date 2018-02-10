package com.henu.dao;

import com.henu.bean.UserTieba;
import com.henu.bean.UserTiebaKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTiebaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(UserTiebaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    int insert(UserTieba record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    int insertSelective(UserTieba record);

    @Select("select count(1) from user_tieba where tiebaId = #{tieBaId} and isFollow = 1")
    int selectCountByTieBaId(int tieBaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    UserTieba selectByPrimaryKey(UserTiebaKey key);
    @Select("select * from user_tieba where username =#{username} and tiebaId = #{TiebaId}")
    List<UserTieba> selectByUserName(@Param("username") String username,@Param("TiebaId") String TiebaId);


    @Select("select * from user_tieba where username =#{username}")
    List<UserTieba> selectAllByUserName(@Param("username") String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserTieba record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_tieba
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserTieba record);
    @Select("select * from user_tieba where TieBaId = #{tiebaId} and UserName =#{userName}")
    UserTieba selectOneByTibeAndUser(@Param("tiebaId")String tiebaId,@Param("userName")String userName);
}