<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i>所有签到管理
				</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

		<!-- <div>
			<div class="echart" ref="chart" :style="myChartStyle"></div>
		</div> -->
		<div class="container" >
			<div class="handle-box">
				<el-input v-model="query.realName" placeholder="请输入员工姓名" class="handle-input mr10"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="getStudentInfo" style="margin-left: 20px">查询
				</el-button>
				<el-button type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
			</div>
		<div style="display:inline-flex;width:100%">
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
				@sort-change="sortTable">
				<el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
				<el-table-column prop="realName" label="真实姓名" align="center"></el-table-column>
				<el-table-column prop="qiandaoDay" label="当月签到" :sortable="'custom'" align="center"></el-table-column>
				<el-table-column prop="qingjiaDay" label="当月请假" :sortable="'custom'" align="center"></el-table-column>
				<el-table-column prop="queqinDay" label="当月缺勤" :sortable="'custom'" align="center"></el-table-column>
				<el-table-column label="操作" width="280" align="center">
					<template slot-scope="scope">
						<el-button type="primary" icon="el-icon-edit" @click="openCheckOnWork(scope.row.id)">查看
						</el-button>
					</template>
				</el-table-column>
					
			</el-table>
			<div style="width:30%;">
				<div class="echart" ref="chart" :style="myChartStyle" style="padding-top:15px"></div>
			</div>
		</div>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>
		<!-- 添加弹出框 -->
		<el-dialog title="操作" :visible.sync="addVisible" width="30%">
			<div id="echart-line" style="width: 300px;height: 300px"></div>
		</el-dialog>
		<el-dialog title="操作" :visible.sync="addVisible2" width="30%">
			<el-form ref="form" :model="form" label-width="70px">
				<el-form-item label="留言" label-width="100px">
					<el-input v-model="form.message"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="addVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveAndUpdateMessageInfo">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import echarts from "echarts";
	import {
		getEchartDataForStudentCheck,
		getUserList,
		saveAndUpdateMessageInfo
	} from '../../api/shool';
import { isContext } from "vm";

	export default {
		name: 'Student',
		data() {
			return {
				query: {
					pageNum: 1,
					pageSize: 10,
					realName: null,
					roleId: 94
				},
				addVisible2: false,
				tableData: [],
				option: [],
				pageTotal: 0,
				addVisible: false,
				form: {},
				data: [],

				xData: ["4000~5999", "6000~7999", "8000~9999", "10000~11999"], //横坐标
				yData: [0, 0, 0, 1], //数据
				myChartStyle: {
					float: "right",
					width: "100%",
					height: "400px"

				}, //图表样式
				qiadaoPeo: 0,
				qingjiaPeo: 0,
				queqinPeo: 0
			};
		},
		created() {
			this.getData();
			this.$nextTick(() => {
				this.$refs.multipleTable.sort('qiandaoDay', 'ascending');
				this.$refs.multipleTable.sort('qingjiaDay', 'ascending');
				this.$refs.multipleTable.sort('queqinDay', 'ascending');
			});
		},
		mounted() {

		},

		methods: {
			sortTable(sort) {
				console.log(sort);
				const {
					prop,
					order
				} = sort;
				if (order === 'ascending') {
					this.tableData.sort((a, b) => a[prop] - b[prop]);
				} else {
					this.tableData.sort((a, b) => b[prop] - a[prop]);
				}
			},
			initZhuzhuangtuEchart() {
				// 基于准备好的dom，初始化echarts实例
				const chart = echarts.init(this.$refs.chart);

				// 配置项数据
				const option = {
					title: {
						text: '本月所有员工签到情况',
						left: 'center'
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {
							type: 'shadow',
						},
					},
					legend: {
						data: ['数据1', '数据2', '数据3'],
					},

					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true,
					},
					xAxis: {
						type: 'category',
						data: [new Date().getMonth() + 1 + "月"],
					},
					yAxis: {
						type: 'value',
					},
					series: [{
							name: '当月签到人数',
							type: 'bar',
							data: [this.qiadaoPeo],
							itemStyle: {
								normal: {
									label: {
										show: true,
										position: 'top',
										textStyle: {
											color: 'black',
											fontSize: 20
										}
									}
								}
							}
						},
						{
							name: '当月请假人数',
							type: 'bar',
							data: [this.qingjiaPeo],
							itemStyle: {
								normal: {
									label: {
										show: true,
										position: 'top',
										textStyle: {
											color: 'black',
											fontSize: 20
										}
									}
								}
							}
						},
						{
							name: '当月缺勤人数',
							type: 'bar',
							data: [this.queqinPeo],
							itemStyle: {
								normal: {
									label: {
										show: true,
										position: 'top',
										textStyle: {
											color: 'black',
											fontSize: 20
										}
									}
								}
							}
						},
					],
				};


				// 使用刚指定的配置项和数据显示图表
				chart.setOption(option);
				window.addEventListener("resize", () => {
					chart.resize();
				});
			},


			/**
			 * 获取表格中的数据
			 */
			getData() {
				getUserList(this.query).then(res => {
					this.tableData = res.data.list;
					this.pageTotal = res.data.total;
					this.queqinPeo = 0
					this.qiadaoPeo = 0
					this.qingjiaPeo = 0
					for (var i = 0; i < this.tableData.length; i++) {
						console.log(parseInt(this.queqinPeo), parseInt(this.tableData[i].queqinDay));
						this.queqinPeo = parseInt(this.queqinPeo) + parseInt(this.tableData[i].queqinDay)
						this.qiadaoPeo = parseInt(this.qiadaoPeo) + parseInt(this.tableData[i].qiandaoDay)
						this.qingjiaPeo = parseInt(this.qingjiaPeo) + parseInt(this.tableData[i].qingjiaDay)
					}

					this.initZhuzhuangtuEchart()
				})
			},

			leaveMessage(id) {
				this.addVisible2 = true;
				this.form.userId = id;
			},




			getStudentInfo() {
				this.getData();
			},

			openCheckOnWork(id) {
				const nowDate = new Date();
				this.form.month = nowDate.getMonth() + 1;
				this.form.userId = id;
				getEchartDataForStudentCheck(this.form).then(res => {
					this.data = res.data;
				});
				setTimeout(() => {
					this.initEchart();
				}, 200)

				this.addVisible = true;
			},

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
					series: [{
						name: '员工状况',
						type: 'pie',
						radius: '50%',
						data: this.data,
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}]
				};


				getchart.setOption(option);
				//随着屏幕大小调节图表
				window.addEventListener("resize", () => {
					getchart.resize();
				});
			},


			handlePageChange(val) {
				this.$set(this.query, 'pageNum', val);
				this.getData();
			},

			reset() {
				this.query.realName = null;
				this.getData();
			},

			saveAndUpdateMessageInfo() {
				saveAndUpdateMessageInfo(this.form).then(res => {
					if (res.code === 1) {
						this.$message.success('留言成功');
						this.addVisible2 = false;
						this.form = {}
					} else {
						this.$message.error('留言失败')
					}
				})
			}


		}
	};
</script>

<style scoped>
	.image {
		height: 30px;
	}

	.handle-box {
		margin-top: 20px;
		margin-bottom: 10px;
	}

	/deep/ .el-upload--text {
		width: 98px !important;
		height: 34px !important;
		display: inline-grid;
		float: left;
		margin-right: 10px;
		border: 1px solid #DCDFE6;
		border-radius: 3px;
	}

	.el-upload--text .inline-block {
		display: inline-block;
		border: 0px dashed #d9d9d9
	}

	.handle-input {
		width: 300px;
		display: inline-block;
	}

	.table {
		width: 100%;
		font-size: 14px;
	}
</style>
