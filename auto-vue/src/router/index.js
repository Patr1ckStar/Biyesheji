import Vue from 'vue';
import Router from 'vue-router';
//import router from '@vue/cli-service/generator/router/template/src/router';

Vue.use(Router);


const router = new Router({
    routes: [
        {
            path: '/',
            redirect: '/Home'
        },
        {
            path: '/Home',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
				{
				    path: '/index',
				    component: () => import('../components/page/index.vue'),
				    meta: { title: '首页' }
				},
                {
                    path: '/Home',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },

                {
                    path: '/sign',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Sign.vue'),
                    meta: { title: '签到管理' }
                },

                {
                    path: '/employee',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Employee'),
                    meta: { title: '员工信息' }
                },

                {
                    path: '/salary',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Salary'),
                    meta: { title: '薪水信息' }
                },
                {
                    path: '/apply',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Apply'),
                    meta: { title: '申请信息' }
                },
                {
                    path: '/MyApply',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/MyApply'),
                    meta: { title: '我的申请' }
                },
                {
                    path: '/position',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Position'),
                    meta: { title: '岗位信息' }
                },
                {
                    path: '/dept',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Dept'),
                    meta: { title: '部门信息' }
                },
                {
                    path: '/invite',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Invite'),
                    meta: { title: '入职信息' }
                },
                {
                    path: '/invite2',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Invite2'),
                    meta: { title: '离职信息' }
                },
                {
                    path: '/my-info',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/MyInfo'),
                    meta: { title: '我的信息' }
                },


                {
                    path: '/all-sign',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/AllSign'),
                    meta: { title: '所有签到' }
                },

                {
                    path: '/my-salary',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/MySalary'),
                    meta: { title: '我的薪资' }
                },

                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
		
        //如果这里后面路由有问题，把这个404注释掉
        {
            path: '*',
            redirect: '/404'
        }
    ]
});

router.beforeEach((to,from,next) =>{
    if (to.path === '/login') {
        next()
    }else{
        const token = sessionStorage.getItem('userStatus');
        if (!token){
            next('/login')
        }else{
            next();
        }
    }
});


export default router
