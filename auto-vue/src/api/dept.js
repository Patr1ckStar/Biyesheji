import request from '../utils/request';


/**
 * 获取所有部门信息,包括条件查询接口
 * @param query
 * @returns {AxiosPromise}
 */
export const getDeptInfo = query =>{
    return request({
        url:'/common/dept/getDeptInfo',
        method:'get',
        params:query
    })
};
/**
 * 保存部门信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export const saveDeptInfo = data =>{
    return request({
        url:'/common/dept/saveDeptInfo',
        method:'post',
        data
    })
};
/**
 * 更新部门信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export const updateDeptInfo = data =>{
    return request({
        url:'/common/dept/updateDeptInfo',
        method:'put',
        data
    })
};
/**
 *根据id删除部门信息接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delDeptInfo = id =>{
    return request({
        url:'/common/dept/delDeptInfo',
        method:'delete',
        params:{id:id}
    })
};
/**
 * 根据id集合批量删除部门信息接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delBatchDeptInfo = id =>{
    return request({
        url:'/common/dept/delBatchDeptInfo',
        method:'delete',
        params:{idList:id}
    })
};


