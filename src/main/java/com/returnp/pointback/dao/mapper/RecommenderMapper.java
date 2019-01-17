package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.Recommender;

public interface RecommenderMapper {
    int deleteByPrimaryKey(Integer recommenderNo);

    int insert(Recommender record);

    int insertSelective(Recommender record);

    Recommender selectByPrimaryKey(Integer recommenderNo);

    int updateByPrimaryKeySelective(Recommender record);

    int updateByPrimaryKey(Recommender record);
}