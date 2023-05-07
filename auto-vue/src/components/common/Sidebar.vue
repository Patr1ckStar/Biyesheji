<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
<!--                    一级菜单-->
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title"><i :class="subItem.icon"></i>{{ subItem.title }}</template>
<!--                                二级菜单-->
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                ><i :class="threeItem.icon"></i>{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            ><i :class="subItem.icon"></i>{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items:[],
            itemList: [
                    {
                        "id":10,
                        "pid":5,
                        "icon":"el-icon-date",
                        "index":"sign",
                        "title":"我的签到",
                        "subs":null
                    },
                    {   
                        
                        "id":4,
                        "pid":1,
                        "icon":"el-icon-setting",
                        "index":"3",
                        "title":"信息管理",
                        "subs":[
                            {
                                "id":9,
                                "pid":4,
                                "icon":"el-icon-yuangongxinxi",
                                "index":"employee",
                                "title":"员工信息",
                                "subs":null
                            },
                            {
                                "id":9,
                                "pid":4,
                                "icon":"el-icon-01zhushuju_zhiweixinxi",
                                "index":"position",
                                "title":"职位信息",
                                "subs":null
                            },
                            {
                                "id":10,
                                "pid":4,
                                "icon":"el-icon-bumen",
                                "index":"dept",
                                "title":"部门信息",
                                "subs":null
                            },
                            {
                                "id":10,
                                "pid":4,
                                "icon":"el-icon-ruzhi",
                                "index":"invite",
                                "title":"入职信息",
                                "subs":null
                            },
                            {
                                "id":10,
                                "pid":4,
                                "icon":"el-icon-lizhi",
                                "index":"invite2",
                                "title":"离职信息",
                                "subs":null
                            },
                        ]
                    },
                    {
                        "id":9,
                        "pid":4,
                        "icon":"el-icon-qianshoushenpitongguo",
                        "index":"apply",
                        "title":"申请审批",
                        "subs":null
                    },
                    // {
                    //             "id":9,
                    //             "pid":4,
                    //             "icon":"el-icon-tongzhizhongxin",
                    //             "index":"Notice",
                    //             "title":"通知管理",
                    //             "subs":null
                    //         },
                    {   
                        "id":4,
                        "pid":1,
                        "icon":"el-icon-zhandianshujutongji",
                        "index":"2",
                        "title":"数据统计",
                        "subs":[
                            
                            {
                                "id":9,
                                "pid":4,
                                "icon":"el-icon-gongzifafang",
                                "index":"salary",
                                "title":"工资报表",
                                "subs":null
                            },
                            {
                                "id":9,
                                "pid":4,
                                "icon":"el-icon-qiandao",
                                "index":"all-sign",
                                "title":"所有签到",
                                "subs":null
                            },
                        ]
                    },
                  ],
            itemList2:[
                    {
                        "id":11,
                        "pid":5,
                        "icon":"el-icon-document",
                        "index":"employee",
                        "title":"我的信息",
                        "subs":null
                    },
                    {
                        "id":10,
                        "pid":5,
                        "icon":"el-icon-date",
                        "index":"sign",
                        "title":"我的签到",
                        "subs":null
                    },
                    {
                            "id":9,
                            "pid":4,
                            "icon":"el-icon-shenqing",
                            "index":"MyApply",
                            "title":"我的申请",
                            "subs":null
                    },
                    {
                            "id":13,
                            "pid":5,
                            "icon":"el-icon-gongzifafang",
                            "index":"my-salary",
                            "title":"我的薪资",
                            "subs":null
                    },
                    // {
                    //         "id":9,
                    //         "pid":4,
                    //         "icon":"el-icon-tongzhizhongxin",
                    //         "index":"MyNotice",
                    //         "title":"我的通知",
                    //         "subs":null
                    //     },
                    ],
            itemList3:[
                    {
                        "id":11,
                        "pid":5,
                        "icon":"el-icon-document",
                        "index":"employee",
                        "title":"我的信息",
                        "subs":null
                    },
                    {
                        "id":10,
                        "pid":5,
                        "icon":"el-icon-date",
                        "index":"sign",
                        "title":"我的签到",
                        "subs":null
                    },
                    {
                        "id":9,
                        "pid":4,
                        "icon":"el-icon-shenqing",
                        "index":"MyApply",
                        "title":"我的申请",
                        "subs":null
                    },
                    {
                        "id":13,
                        "pid":5,
                        "icon":"el-icon-gongzifafang",
                        "index":"my-salary",
                        "title":"我的薪资",
                        "subs":null
                    },
                    // {
                    //             "id":9,
                    //             "pid":4,
                    //             "icon":"el-icon-tongzhizhongxin",
                    //             "index":"MyNotice",
                    //             "title":"我的通知",
                    //             "subs":null
                    // },
                    {
                        "id":4,
                        "pid":1,
                        "icon":"el-icon-setting",
                        "index":"3",
                        "title":"信息管理",
                        "subs":[
                            {
                                "id":9,
                                "pid":4,
                                "icon":"el-icon-shenqing",
                                "index":"apply",
                                "title":"员工申请进度",
                                "subs":null
                            },
                            {
                                "id":10,
                                "pid":4,
                                "icon":"el-icon-ruzhi",
                                "index":"invite",
                                "title":"入职信息",
                                "subs":null
                            },
                            {
                                "id":10,
                                "pid":4,
                                "icon":"el-icon-lizhi",
                                "index":"invite2",
                                "title":"离职信息",
                                "subs":null
                            },
                        ],
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        },
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
        //初始化menuList
        if ("1" === sessionStorage.getItem('userType')){
            this.items = this.itemList;
			this.collapse = true
			bus.$emit('collapse-content', true);
			
        }else if ("2" === sessionStorage.getItem('userType')){
            this.items = this.itemList2;
        }else{
            this.items = this.itemList3
        }
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
