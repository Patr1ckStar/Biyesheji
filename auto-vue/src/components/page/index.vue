<template>
	<div class="container">
		<div>
			<el-row :gutter="50" style="">
				<el-col :span="6">
					<el-card>
						<div style="text-align: center;font-size: 28px;font-weight: bold;">我的信息</div>
						<div style="text-align: center;">
							<img style="border-radius: 100%;width: 300px;height: 300px;"
								:src="userinfo.imageUrl" />
							<div>{{userinfo.realName}}</div>
							<div>{{userinfo.jobName}}</div>
						</div>
					</el-card>
				</el-col>
				<el-col :span="6">
					<el-card>
						<div style="text-align: center;font-size: 28px;font-weight: bold;">公告栏</div>
						<div style="text-align: center;width: 360px;height: 357px;">
							<el-row :gutter="10">
	
							</el-row>
							<el-row :gutter="10">
	
							</el-row>
							<el-row :gutter="10">
	
							</el-row>
							<div>{{userinfo.realName}}</div>
							<div>{{userinfo.jobName}}</div>
						</div>
					</el-card>
				</el-col>
				<el-col :span="12">
					<el-card >
						<div style="text-align: center;font-size: 28px;font-weight: bold;">签到排名</div>
						<div style="display: flex;">
							<div class="boxOne" style="text-align: center;">
								<div>第二名</div>
								<div class="boxTwo">
									<div class="medal">
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										src="../../assets/img/2.png" />
									</div>
									<div>
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										:src="paimingList[1].imageUrl" />
									</div>
								</div>
								<div>
									<p>{{paimingList[1].realName}}</p>
									<p>签到次数：{{paimingList[1].qiandaoDay}}</p>
								</div>
							</div>
							<div class="boxOne" style="text-align: center;">
								<div>第一名</div>
								<div class="boxTwo" style="width:200px;height:200px">
									<div class="medal">
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										src="../../assets/img/1.png" />
									</div>
									<div>
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										:src="paimingList[0].imageUrl" />
									</div>
								</div>
								<div>
									<p>{{paimingList[0].realName}}</p>
									<p>签到次数：{{paimingList[0].qiandaoDay}}</p>
								</div>							</div>
							<div class="boxOne" style="text-align: center;">
								<div>第三名</div>
								<div class="boxTwo">
									<div class="medal">
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										src="../../assets/img/3.png" />
									</div>
									<div>
									<img style="border-radius: 100%;width: 100%;height: 100%;"
										:src="paimingList[2].imageUrl" />
									</div>
								</div>
								<div>
									<p>{{paimingList[2].realName}}</p>
									<p>签到次数：{{paimingList[2].qiandaoDay}}</p>
								</div>
							</div>

						</div>
					</el-card>
				</el-col>
			</el-row>


			<el-row style="margin-top: 20px;" :gutter="50">
				<el-col :span="10">
					<el-card>
						<div style="height: 300px;">
							<div>
								<el-row  :gutter="10">
									<el-col style="margin-top:30px;margin-bottom: 20px;" v-for="i in menus" :span="6">
										<div @click="$router.push(i.path)" style="text-align: center;">
											<img style="width: 50px;height: 50px;" :src="i.imgUrl" />
											<div>{{i.name}}</div>
										</div>
									</el-col>

								</el-row>
							</div>


						</div>
					</el-card>
				</el-col>

				<el-col :span="7">
					<el-card>
						<div style="height: 300px;width: 100%;">
							<div style="width: 100%;height: 100%;" id="chart-container2"></div>
						</div>
					</el-card>
				</el-col>
				<el-col :span="7">
					<el-card>
						<div style="height: 300px;width: 100%;">
							  <div style="width: 100%;height: 100%;" id="chart-container"></div>
						</div>
					</el-card>
				</el-col>
			</el-row>
		</div>
	</div>
</template>

<script>
	import * as echarts from 'echarts';
	import { getUserList } from '../../api/shool';
	export default {
		data() {
			return {
				menus: [
					{
						name:'员工信息',
						path:'/employee',
						imgUrl:require('../../assets/img/user.png')
					},
					{
						name:'申请审批',
						path:'/apply',
						imgUrl:require('../../assets/img/Information.png')
					},
					{
						name:'职位信息',
						path:'/position',
						imgUrl:require('../../assets/img/job.png')
					},
					{
						name:'工资报表',
						path:'/salary',
						imgUrl:require('../../assets/img/salary.png')
					},
					{
						name:'部门信息',
						path:'/dept',
						imgUrl:require('../../assets/img/dept.png')
					},
					{
						name:'所有签到',
						path:'/all-sign',
						imgUrl:require('../../assets/img/Calendar.png')
					},
					{
						name:'入职信息',
						path:'/invite',
						imgUrl:require('../../assets/img/adduser.png')
					},
					{
						name:'离职信息',
						path:'/invite2',
						imgUrl:require('../../assets/img/deluser.png')
					},
				],
				userinfo:{},
				paimingList:[]
			}
		},
		created() {
			this.getInfo()
		},
		methods: {
			getInfo() {
				getUserList({id:sessionStorage.getItem("userId")}).then(res=>{
					this.userinfo = res.data.list[0]
				})
				getUserList({pageSize:10000}).then(res=>{
					this.paimingList = res.data.list.sort((a, b) => b.qiandaoDay - a.qiandaoDay).slice(0, 3);
				})
			}
		},
	  mounted() {
	    // 获取用户列表，这里使用axios库进行异步请求
	    getUserList({pageSize:10000}).then(res => {
			
			
			
	      const users = res.data.list;
		  
		  
		  
		  
		  
	      // 统计用户数
	      const ageCountMap = new Map();
	      users.forEach(user => {
	        const age = user.age;
	        let count = ageCountMap.get(ageRange(age));
	        if (count==undefined) {
	          count = 0;
	        }
			console.log(count,"--");
	        ageCountMap.set(ageRange(age), count + 1);
	      });
		  
		        const eduMap = new Map();
		        users.forEach(user => {
		          if (eduMap.has(user.education)) {
		            eduMap.set(user.education, eduMap.get(user.education) + 1);
		          } else {
		            eduMap.set(user.education, 1);
		          }
		        });
		        // Array.from(eduMap, ([name, value]) => ({ name, value}));
		      
	
	const chartDom2 = document.getElementById('chart-container2');
	const chart = echarts.init(chartDom2);
	      chart.setOption({
			  title: {
			    text: '员工学历分布',
			    left: 'center'
			  },
	        series: [{
	          name: '学历',
	          type: 'pie',
	          radius: '55%',
	          data: [
	              { value: eduMap.get('博士'), name: '博士' },
	              { value: eduMap.get('硕士'), name: '硕士' },
	              { value: eduMap.get('本科'), name: '本科' },
	              { value: eduMap.get('专科及以下'), name: '专科及以下' },
	            ],
	          label: {
	            formatter: '{b}: {c} ({d}%)'
	          }
	        }]
	      });
	
	
	      // 绘制环形图
	      const chartDom = document.getElementById('chart-container');
	      const myChart = echarts.init(chartDom);
	      const option = {
	        title: {
	          text: '员工年龄分布',
	          left: 'center'
	        },
			 legend: {
			    // 配置图例组件的数据，其中每一项都与 series 中的 name 对应
			    data: ['25岁以下', '25-30岁', '31-35岁', '36-40岁', '41-50岁', '51岁及以上'],
			    // 配置图例组件的布局
			    orient: 'vertical',
			    left: 10,
			    top: 10,
			    // 配置图例组件的点击事件
			    selectedMode: 'multiple',  // 或者为 'multiple'
			    // 配置图例组件的每一项的样式
			    textStyle: {
			        color: '#333',
			        fontSize: 12
			    }
			  },
	        tooltip: {
	          trigger: 'item',
	          formatter: '{a} <br/>{b}: {c} ({d}%)'
	        },
	        series: [
	          {
	            name: '年龄',
	            type: 'pie',
	            radius: ['40%', '60%'],
	            avoidLabelOverlap: false,
	            label: {
	              show: false,
	              position: 'center'
	            },
	            emphasis: {
	              label: {
	                show: true,
	                fontSize: '30',
	                fontWeight: 'bold'
	              }
	            },
	            labelLine: {
	              show: false
	            },
	            data: [
	              { value: ageCountMap.get('25岁以下'), name: '25岁以下' },
	              { value: ageCountMap.get('25-30岁'), name: '25-30岁' },
	              { value: ageCountMap.get('31-35岁'), name: '31-35岁' },
	              { value: ageCountMap.get('36-40岁'), name: '36-40岁' },
	              { value: ageCountMap.get('41-50岁'), name: '41-50岁' },
	              { value: ageCountMap.get('51岁及以上'), name: '51岁及以上' }
	            ]
	          }
	        ]
	      };
	      myChart.setOption(option);
	    }).catch(console.error);
	  }
	};
	
	function ageRange(age) {
	  if (age < 25) {
	    return '25岁以下';
	  }
	  if (age >= 26 && age <= 30) {
	    return '25-30岁';
	  }
	  if (age >= 31 && age <= 35) {
	    return '31-35岁';
	  }
	  if (age >= 36 && age <= 40) {
	    return '36-40岁';
	  }
	  if (age >= 41 && age <= 50) {
	    return '41-50岁';
	  }
	  return '51岁及以上';
	}

	
	
	

</script>

<style>
.boxOne{
	height: 360px;
	width: 240px;
	flex-grow: 1;
	display: flex;
	flex-direction: column;
	justify-content:center;
	
}
.boxTwo{
	height: 160px;
	width: 160px;
	margin:auto;
}
.medal{
	height: 50px;
	width: 50px;
	position: absolute;
}
</style>
