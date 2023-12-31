package com.wujiajun.service;

import com.alibaba.fastjson.JSONObject;
import com.wujiajun.entity.Food;
import com.wujiajun.entity.FoodType;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;

import java.util.List;

/**
 * @author wujiajun
 * @date 2023/5/6/ 15:54
 */
public interface FoodService {
    /**
     * 删除菜品
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 修改菜品
     * @param food
     * @return
     */
    Result update(Food food);

    /**
     * 添加菜品
     * @param food
     * @return
     */
    Result insert(Food food);

    /**
     * 分页查询菜品信息
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 修改菜品分类
     * @param foodType
     * @return
     */
    Result updateType(FoodType foodType);

    /**
     * 删除食物分类
     * @param id
     * @return
     */
    Result deleteType(Long id);

    /**
     * 添加食物分类
     * @param foodType
     * @return
     */
    Result insertType(FoodType foodType);

    /**
     * 批量导入
     * @param list
     * @return
     */
    Result batchImport(List<Food> list);

    /**
     * 查询食物
     * @param queryInfo
     * @return
     */
    Result findFoodPage(QueryInfo queryInfo);

    /**
     * 查询所有分类信息
     * @return
     */
    Result typeAll();

    Result findFoodByTypeId(QueryInfo queryInfo);

    Result findMiniPage(JSONObject object);

    /**
     * 根据ID获取食物信息
     * @param id
     * @return
     */
    Result findById(Long id);
}
