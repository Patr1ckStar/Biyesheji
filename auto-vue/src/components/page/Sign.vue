<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>签到管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="display: inline-flex">

            <div class="circle" style="flex: 1.1">
<!--                <span>打卡</span>-->
                <img src="../../assets/img/qiandao.png" @click="openSign">
                <div class="time">{{this.realTime}}</div>
				
			<div id="echart-line" style="width: 300px;height: 300px;margin-top: 100px;"></div>
            </div>
  
            <div style="flex: 3.5">
            <el-calendar id="calendar" style="margin-top: -30px">
                <template
                        slot="dateCell"
                        slot-scope="{date, data}">
                    <div class="calendar-day" @click="getSome(date)">{{ data.day.split('-').slice(2).join('-') }}</div>
                    <p v-if="handleSelected(data.day) == '1' " style="margin-top: 10px;margin-left: 50px">正常 </p>
                    <p v-else-if="handleSelected(data.day) == '2' " style="color: red;margin-top: 10px;margin-left: 50px">请假</p>
                    <p v-else-if="handleSelected(data.day) == null " style="color: green;margin-top: 10px;margin-left: 50px">缺勤</p>
                </template>
            </el-calendar>
            </div>
        </div>
        <el-dialog title="签到" :visible.sync="addVisible" width="30%">
            <el-form ref="form" :model="form"  label-width="70px">
                <el-form-item label="签到类型" label-width="100px" >
                    <el-select v-model="form.eventType">
                        <el-option label="正常" value="1"></el-option>
                        <el-option label="请假" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.remark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="sign">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import echarts from  "echarts";
    import { getNowDate, getSign, getEchartDataForStudentCheck,sign } from '../../api/shool';
    export default {
        data() {
            return {
                realTime:'',
                form:{

                },
                addVisible:false,
                attendanceDetailsData:[],
				data:{}

            };
        },

        mounted () {
            // 页面加载完后显示当前时间
            this.realTime = this.dealWithTime(new Date())
            // 定时刷新时间
            let _this = this
            // 定时器
            this.timer = setInterval(function () {
                _this.realTime = _this.dealWithTime(new Date()) // 修改数据date
            }, 1000)
        },

        created() {
            this.getSign();
        },

        methods: {
			initEchart() {
			  let getchart = echarts.init(document.getElementById('echart-line'));
			  const option = {
			    title: {
			      text: '员工签到情况',
			      left: 'center'
			    },
			    tooltip: {
			      trigger: 'item'
			    },
			    legend: {
			      orient: 'vertical',
			      left: 'left',
			    },
			    series: [
			      {
			        name: '员工状况',
			        type: 'pie',
			        radius: '50%',
			        data:this.data,
			        emphasis: {
			          itemStyle: {
			            shadowBlur: 10,
			            shadowOffsetX: 0,
			            shadowColor: 'rgba(0, 0, 0, 0.5)'
			          }
			        }
			      }
			    ]
			  };
			  
			  
			  getchart.setOption(option);
			  //随着屏幕大小调节图表
			  window.addEventListener("resize", () => {
			    getchart.resize();
			  });
			},
            openSign(){
                this.form = {};
                this.addVisible = true;
            },
            sign(){
                this.form.currentDay = getNowDate();
                this.form.userId = sessionStorage.getItem("userId");
                const nowDate = new Date();
                const date = {
                    year: nowDate.getFullYear(),
                    month: nowDate.getMonth() + 1,
                    date: nowDate.getDate(),
                };
                const newmonth = date.month;
                console.log(newmonth)
                this.form.month = newmonth
                sign(this.form).then(res =>{
                    if (res.code === 1){
                        this.getSign();
                        this.addVisible = false;
                        this.$message.success('签到成功');
                    }else if (res.code === -2){
                        this.$message.warning(res.msg);
                        setTimeout(() =>{
                            this.addVisible = false;
                        },1000)
                    }else {
                        this.$message.error('签到失败');
                    }
                })
            },

            getSign(){
                getSign({userId:sessionStorage.getItem("userId"),pageNum:1,pageSize:100000}).then(res =>{
                    if (res.code === 1){
                        this.attendanceDetailsData = res.data.list;
						
                    }
                })
				const nowDate = new Date();
				var form  = {}
				form.month = nowDate.getMonth() + 1;
				form.userId = sessionStorage.getItem("userId");
				getEchartDataForStudentCheck(form).then(res =>{
				  this.data = res.data;
				  setTimeout(() =>{
				    this.initEchart();
				  },200)
				});
				
            },


            handleSelected(day) {
                let flag = -1; //默认显示为-1
                this.attendanceDetailsData.forEach(item => {
                    //this.attendanceDetailsData是后台返回的数据　
                    // console.log(day);
                    if (item.currentDay === day) {  //判断显示数据　
                        console.log('过来 ')
                        flag = item.eventType;
                        return flag;
                    }
                })
                return flag
            },

            getSome(date){
                console.log(date)
                this.$message.success('chenggong')
            },
            dealWithTime (data) {
                let formatDateTime
                let Y = data.getFullYear()
                let M = data.getMonth() + 1
                let D = data.getDate()
                let H = data.getHours()
                let Min = data.getMinutes()
                let S = data.getSeconds()
                let W = data.getDay()
                H = H < 10 ? ('0' + H) : H
                Min = Min < 10 ? ('0' + Min) : Min
                S = S < 10 ? ('0' + S) : S
                switch (W) {
                    case 0:
                        W = '天'
                        break
                    case 1:
                        W = '一'
                        break
                    case 2:
                        W = '二'
                        break
                    case 3:
                        W = '三'
                        break
                    case 4:
                        W = '四'
                        break
                    case 5:
                        W = '五'
                        break
                    case 6:
                        W = '六'
                        break
                    default:
                        break
                }
                formatDateTime = Y + '年' + M + '月' + D + '日 ' + H + ':' + Min + ':' + S + ' 星期' + W
                return formatDateTime
            }
        },
// 注意在vue实例销毁前，清除我们的定时器。
        destroyed () {
            if (this.timer) {
                clearInterval(this.timer)
            }
        },

        watch:{
            realTime:function(newVal,oldVal) {
                // console.log(newVal.substring(11,19))
                // if (newVal.substring(11,19) === '00:00:00'){
                //     this.$message.success('第二天了')
                // }
            },
        }
    };
</script>

<style scoped>
    .circle {
        border-radius: 50%;
        width: 300px;
        height: 300px;
        background: #028bff;
        /*margin-left: 500px;*/
        /* 宽度和高度需要相等 */
        margin-top: 70px;
    }
    .time{
        color: white;
        padding-top: 30px;
        padding-left: 40px;
    }
    img{
        width: 60px;
        height: 60px;
        padding-top: 90px;
        padding-left: 115px;
        cursor: pointer;
    }
</style>
