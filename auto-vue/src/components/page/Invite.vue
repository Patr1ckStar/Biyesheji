<template>
	<el-card shadow="never" class="aui-card--fill">
		<div class="mod__invite">
			<el-form :inline="true" :model="query" @keyup.enter.native="getData()">
				<el-form-item>
					<el-input v-model="query.realName" placeholder="姓名" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" plain @click="getData()">查询</el-button>
					<el-button type="warning" plain @click="reset()">重置</el-button>
				</el-form-item>
				<el-form-item>
					<el-button :disabled="this.multipleSelection.length === 0" type="danger" plain
						@click="delBatchInviteInfo">批量删除</el-button>
				</el-form-item>
			</el-form>
			<div class="handle-box">
        		<el-button style="margin-bottom: 10px" type="success" icon="el-icon-plus"
          		class="handle-del mr10" @click="addInviteInfo">录入入职信息</el-button>
     		 </div>
			<el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange"
				style="width: 100%;">
				<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
				<el-table-column prop="realName" label="姓名" header-align="center" align="center"></el-table-column>
				<el-table-column prop="phone" label="电话" header-align="center" align="center"></el-table-column>
				<!-- <el-table-column prop="jobName" label="岗位" header-align="center" align="center"></el-table-column>
				<el-table-column prop="depName" label="部门" header-align="center" align="center"></el-table-column> -->
				<el-table-column prop="education" label="学历" header-align="center" align="center"></el-table-column>
				<el-table-column prop="imageUrl" label="照片" header-align="center" align="center">
					<template slot-scope="scope">
						<el-image style="width: 60px; height: 60px" :src="scope.row.imageUrl"
							:preview-src-list="[scope.row.imageUrl]">
						</el-image>
					</template>
				</el-table-column>
				<el-table-column prop="remark" label="描述信息" header-align="center" align="center"></el-table-column>
				<!-- <el-table-column prop="isHire" label="是否录用" header-align="center" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.isHire === '1'">录用</span>
						<span v-if="scope.row.isHire === '2'">不录用</span>
					</template>
				</el-table-column> -->
				<el-table-column prop="interviewTime" label="面试时间" width="160" header-align="center" align="center">
				</el-table-column>
				<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
					<template slot-scope="scope">
						<el-button type="primary" size="small" @click="updateInviteInfo(scope.row)">更新</el-button>
						<el-button type="danger" size="small" @click="delInviteInfo(scope.row.id)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination :current-page="query.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="query.pageSize"
				:total="total" layout="total, sizes, prev, pager, next, jumper" @size-change="pageSizeChange"
				@current-change="pageCurrentChange">
			</el-pagination>
			<!-- 弹窗, 新增 / 修改 -->
			<el-dialog :visible.sync="addOrUpdateVisible" :title="!form.id ? '新增' : '编辑'" :close-on-click-modal="false"
				:close-on-press-escape="false">
				<el-form :model="form" :rules="rule" ref="form" @keyup.enter.native="formSubmitHandle()"
					label-width="100px">
					<el-form-item label="姓名" prop="realName">
						<el-input v-model="form.realName" placeholder="姓名"></el-input>
					</el-form-item>
					<el-form-item label="电话" prop="phone">
						<el-input v-model="form.phone" placeholder="电话"></el-input>
					</el-form-item>
					<el-form-item label="岗位" label-width="100px" prop="jobId">
						<el-select v-model="form.jobId" placeholder="请选择">
							<el-option v-for="item in jobList" :key="item.id" :label="item.jobName" :value="item.id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="部门" label-width="100px"  prop="deptId">
						<el-select v-model="form.deptId" placeholder="请选择">
							<el-option v-for="item in deptList" :key="item.id" :label="item.deptName" :value="item.id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="学历" prop="education">
						<el-select v-model="form.education" placeholder="请选择">
							<el-option label="博士" value="博士"></el-option>
							<el-option label="硕士" value="硕士"></el-option>
							<el-option label="本科" value="本科"></el-option>
							<el-option label="大专及以下" value="大专及以下"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="照片" prop="">
						<single-upload v-model="form.imageUrl"></single-upload>
					</el-form-item>
					<el-form-item label="描述信息" prop="remark">
						<el-input v-model="form.remark" placeholder="描述信息"></el-input>
					</el-form-item>
					<el-form-item label="年龄" label-width="100px" prop="age">
						<el-select v-model="form.age" placeholder="请选择">
							<el-option v-for="(item,index) in 53" :label="(item+17)" :value="(item+17)"></el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="电子邮箱" label-width="100px" prop="email">
						<el-input v-model="form.email"></el-input>
					</el-form-item>
					<el-form-item label="性别" label-width="100px" prop="sex">
						<el-radio v-model="form.sex" label="1">男</el-radio>
						<el-radio v-model="form.sex" label="2">女</el-radio>
					</el-form-item>、
					<!-- <el-form-item label="是否录用" prop="isHire">
			          <el-radio-group v-model="form.isHire">
						<el-radio :label="'1'">录用</el-radio>
						<el-radio :label="'2'">不录用</el-radio>
			          </el-radio-group> -->
			        </el-form-item>
					<el-form-item label="面试时间" prop="interviewTime">
						<el-date-picker class="runPicker" v-model="form.interviewTime" type="datetime"
							placeholder="请选择面试时间" value-format="yyyy-MM-dd HH:mm:ss">
						</el-date-picker>
					</el-form-item>
				</el-form>
				<template slot="footer">
					<el-button @click="addOrUpdateVisible = false">取消</el-button>
					<el-button type="primary" @click="formSubmitHandle()">确认</el-button>
				</template>
			</el-dialog>

		</div>
	</el-card>
</template>

<script>
	import SingleUpload from '../common/singleUpload';
	import {
		getDeptInfo
	} from '../../api/dept';
	import {
		getJobList
	} from '../../api/shool';
	import {
		getInviteInfo,
		saveInviteInfo,
		updateInviteInfo,
		delInviteInfo,
		delBatchInviteInfo
	} from '../../api/invite'



	export default {
		components: {
			SingleUpload
		},

		data() {
			return {
				jobList: [],
				deptList: [],
				tableData: [],
				query: {
					pageNum: 1,
					pageSize: 10,
					realName: null,
				},
				form: {},
				multipleSelection: [],
				tableDataLoading: false,
				total: 0,
				addOrUpdateVisible: false,
				rule: {
					jobId: [
			{ 	required: true,
						message: '请输入该字段',
						trigger: 'blur'
					}, 
					],

					deptId: [{
						required: true,
						message: '请输入该字段',
						trigger: 'blur'
					}, ],
					age: [{
						required: true,
						message: '请输入该字段',
						trigger: 'blur'
					}, ],
					sex: [{
						required: true,
						message: '请输入该字段',
						trigger: 'blur'
					}, ],
					email: [{
							required: true,
							message: '请输入该字段',
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱格式',
							trigger: ['blur', 'change']
						},
					],
					realName: [{
						required: true,
						message: '姓名不得为空',
						trigger: "blur"
					}],
					phone: [{
							required: true,
							message: '请输入手机号码',
							trigger: 'blur'
						},
						{
							pattern: /^1[3456789]\d{9}$/,
							message: '请输入正确的手机号码格式',
							trigger: ['blur', 'change']
						},
						],
						education: [{
							required: true,
							message: '学历不得为空',
							trigger: "blur"
						}],
						imageUrl: [{
							required: true,
							message: '照片不得为空',
							trigger: "blur"
						}],
						remark: [{
							required: true,
							message: '描述信息不得为空',
							trigger: "blur"
						}],
						isHire: [{
							required: true,
							message: '是否录用不得为空',
							trigger: "change"
						}],
						createTime: [{
							required: true,
							message: '创建时间不得为空',
							trigger: "blur"
						}],
						interviewTime: [{
							required: true,
							message: '面试时间不得为空',
							trigger: "blur"
						}],
					},
				}
			},
			created() {
					this.getData();
				},

				methods: {
					/**
					 * 获取所有招聘人员
					 */
					getData() {
						getJobList({
							pageSize: 100
						}).then(res => {
							this.jobList = res.data.list;
						});
						getDeptInfo({
							pageSize: 100
						}).then(res => {
							this.deptList = res.data.list;
						});
						this.query.type = 1;
						getInviteInfo(this.query).then(res => {
							this.tableData = res.data.list;
							this.total = res.data.total;
						})
					},


					/**
					 * 打开添加框
					 */
					addInviteInfo() {
						this.form = {};
						this.form.id = null;
						if (this.$refs['form'] !== undefined) {
							this.$refs['form'].resetFields();
						}
						this.addOrUpdateVisible = true;
					},

					/**
					 * 打开更新框
					 */
					updateInviteInfo(row) {
						this.form = JSON.parse(JSON.stringify(row));
						this.addOrUpdateVisible = true;
					},

					/**
					 * 按住enter键或者确定按钮提交数据,对后台发请求
					 * 如果id为null,就是走添加招聘人员的接口
					 * 如果id不为null,就是走更新招聘人员的接口
					 */
					formSubmitHandle() {
						this.$refs['form'].validate(valid => {
							if (valid) {
								if (this.form.id === null) {
									this.form.type = 1;
									saveInviteInfo(this.form).then(res => {
										this.$message.success('添加成功');
										this.getData();
										this.addOrUpdateVisible = false;
									})
								} else {
									updateInviteInfo(this.form).then(res => {
										this.$message.success('更新成功');
										this.getData();
										this.addOrUpdateVisible = false;
									})
								}
								// this.$refs['form'].resetFields();


							}
						});
					},

					/**
					 *单个删除数据
					 */
					delInviteInfo(id) {
						this.$confirm('确定要删除所选择的吗？', '提示', {
							type: 'warning'
						}).then(action => {
							if (action === 'confirm') {
								delInviteInfo(id).then(res => {
									if (res.code === 1) {
										this.$message.success('删除成功');
										this.getData();
									} else {
										return this.$message.error(res.msg)
									}

								})
							}
						}).catch(() => {});

					},



					/**
					 * 选中table表格事件
					 */
					selectionChange(val) {
						this.multipleSelection = [];
						val.forEach((item) => {
							this.multipleSelection.push(item.id);
						});
					},

					/**
					 * 批量删除招聘人员的接口
					 */
					delBatchInviteInfo() {
						delBatchInviteInfo(this.multipleSelection.join(',')).then(res => {
							this.$message.success('批量删除成功');
							this.getData();
						})
					},

					/**
					 * 重置数据
					 */
					reset() {
						this.query.realName = null;
						this.$set(this.query, 'pageNum', 1);
						this.getData();
					},


					/**
					 * 分页(改变页码)
					 * @param val
					 */
					pageCurrentChange(val) {
						this.$set(this.query, 'pageNum', val);
						this.getData();
					},

					/**
					 * 分页(改变每页条数)
					 * @param val
					 */
					pageSizeChange(val) {
						this.$set(this.query, 'pageSize', val);
						this.getData();
					},

					/**
					 * 关闭弹窗
					 */
					clearAddForm() {
						this.addOrUpdateVisible = false;
					}
				},


		}
</script>
