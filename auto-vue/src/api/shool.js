import request from '../utils/request';


export const Login = query =>{
    return request({
        url:'/personnel/user/login',
        method:'post',
        params:query,
    })
};

export const getUserNewMessageInfo= query =>{
    return request({
        url:'/personnel/user/getUserNewMessageInfo',
        method:'get',
        params:query,
    })
};

export const getRecordList = query =>{
    return request({
        url:'/common/record/getRecordInfo',
        method:'get',
        params:query,
    })
};


export function getNowDate() {
    let date = new Date();
    let y = date.getFullYear();
    let m = date.getMonth() + 1;
    let d = date.getDate();
    m = m < 10 ? "0" + m : m;
    d = d < 10 ? "0" + d : d;
    return y + "-" + m + "-" + d
}

export const getUserList = query =>{
    return request({
        url:'/personnel/user/getUserInfo',
        method:'get',
        params:query,
    })
};



export const saveAndUpdateUserInfo = query =>{
    return request({
        url:'/personnel/user/saveAndUpdateUserInfo',
        method:'post',
        params:query,
    })
};

export const delUserInfo = id =>{
    return request({
        url:'/personnel/user/delUserInfo',
        method:'delete',
        params:{id:id},
    })
};


export const getJobList = query =>{
    return request({
        url:'/personnel/job/getJobInfo',
        method:'get',
        params:query,
    })
};



export const saveAndUpdateJobInfo = query =>{
    return request({
        url:'/personnel/job/saveAndUpdateJobInfo',
        method:'post',
        params:query,
    })
};

export const delJobInfo = id =>{
    return request({
        url:'/personnel/job/delJobInfo',
        method:'delete',
        params:{id:id},
    })
};


export const getNoticeList = query =>{
    return request({
        url:'/personnel/notice/getNoticeInfo',
        method:'get',
        params:query,
    })
};



export const saveAndUpdateNoticeInfo = query =>{
    return request({
        url:'/personnel/notice/saveAndUpdateNoticeInfo',
        method:'post',
        params:query,
    })
};

export const delNoticeInfo = id =>{
    return request({
        url:'/personnel/notice/delNoticeInfo',
        method:'delete',
        params:{id:id},
    })
};

export const releaseBonus = query =>{
    return request({
        url:'/personnel/user/releaseBonus',
        method:'post',
        params:query,
    })
};



export const sign = query =>{
    return request({
        url:'/personnel/calendar/saveAndUpdateCalendarInfo',
        method:'post',
        params:query,
    })
};

export const getSign = query =>{
    return request({
        url:'/personnel/calendar/getCalendarInfo',
        method:'get',
        params:query,
    })
};


export const getEchartDataForStudentCheck= query =>{
    return request({
        url:'/personnel/calendar/getEchartDataForStudentCheck',
        method:'get',
        params:query,
    })
};

export const getMessageList = query =>{
    return request({
        url:'/personnel/message/getMessageInfo',
        method:'get',
        params:query,
    })
};



export const saveAndUpdateMessageInfo = query =>{
    return request({
        url:'/personnel/message/saveAndUpdateMessageInfo',
        method:'post',
        params:query,
    })
};

export const delMessageInfo = id =>{
    return request({
        url:'/personnel/message/delMessageInfo',
        method:'delete',
        params:{id:id},
    })
};


export const getTaskList = query =>{
    return request({
        url:'/personnel/task/getTaskInfo',
        method:'get',
        params:query,
    })
};



export const saveAndUpdateTaskInfo = query =>{
    return request({
        url:'/personnel/task/saveAndUpdateTaskInfo',
        method:'post',
        params:query,
    })
};

export const delTaskInfo = id =>{
    return request({
        url:'/personnel/task/delTaskInfo',
        method:'delete',
        params:{id:id},
    })
};
export const getDocumentList = query =>{
    return request({
        url:'/personnel/document/getDocumentInfo',
        method:'get',
        params:query,
    })
};

export const saveAndUpdateDocumentInfo = query =>{
    return request({
        url:'/personnel/document/saveAndUpdateDocumentInfo',
        method:'post',
        params:query,
    })
};

export const delDocumentInfo = id =>{
    return request({
        url:'/personnel/document/delDocumentInfo',
        method:'delete',
        params:{id:id},
    })
};
export const getApplyList = query =>{
    return request({
        url:'/personnel/apply/getApplyInfo',
        method:'get',
        params:query,
    })
};

export const saveAndUpdateApplyInfo = query =>{
    return request({
        url:'/personnel/apply/saveAndUpdateApplyInfo',
        method:'post',
        params:query,
    })
};

export const delApplyInfo = id =>{
    return request({
        url:'/personnel/apply/delApplyInfo',
        method:'delete',
        params:{id:id},
    })
};

export const getRoomList = query =>{
    return request({
        url:'/personnel/room/getRoomInfo',
        method:'get',
        params:query,
    })
};

export const saveAndUpdateRoomInfo = query =>{
    return request({
        url:'/personnel/room/saveAndUpdateRoomInfo',
        method:'post',
        params:query,
    })
};

export const delRoomInfo = id =>{
    return request({
        url:'/personnel/room/delRoomInfo',
        method:'delete',
        params:{id:id},
    })
};
