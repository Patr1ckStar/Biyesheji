import request from '../utils/request';


/**
 * 获取所有招聘人员,包括条件查询接口
 * @param query
 * @returns {AxiosPromise}
 */
export const getInviteInfo = query =>{
    return request({
        url:'/common/invite/getInviteInfo',
        method:'get',
        params:query
    })
};
/**
 * 保存招聘人员接口
 * @param data
 * @returns {AxiosPromise}
 */
export const saveInviteInfo = data =>{
    return request({
        url:'/common/invite/saveInviteInfo',
        method:'post',
        data
    })
};
/**
 * 更新招聘人员接口
 * @param data
 * @returns {AxiosPromise}
 */
export const updateInviteInfo = data =>{
    return request({
        url:'/common/invite/updateInviteInfo',
        method:'put',
        data
    })
};
/**
 *根据id删除招聘人员接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delInviteInfo = id =>{
    return request({
        url:'/common/invite/delInviteInfo',
        method:'delete',
        params:{id:id}
    })
};
/**
 * 根据id集合批量删除招聘人员接口
 * @param id
 * @returns {AxiosPromise}
 */
export const delBatchInviteInfo = id =>{
    return request({
        url:'/common/invite/delBatchInviteInfo',
        method:'delete',
        params:{idList:id}
    })
};


