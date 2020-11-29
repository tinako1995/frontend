<!--edit teacher-->
<template>
  <div class="all">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="teacherName" label="姓名" width="180"></el-table-column>
      <el-table-column prop="institute" label="單位" width="200"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="電話" width="120"></el-table-column>
      <el-table-column prop="email" label="密碼" width="120"></el-table-column>
      <el-table-column prop="cardId" label="教師證號" width="120"></el-table-column>
      <el-table-column prop="type" label="身分別" width="120"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.teacherId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.teacherId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="form.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="單位">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.sex"></el-input>
          </el-form-item>
          <el-form-item label="電話">
            <el-input v-model="form.tel"></el-input>
          </el-form-item>
          <el-form-item label="密碼">
            <el-input v-model="form.pwd"></el-input>
          </el-form-item>
          <el-form-item label="教師證號">
            <el-input v-model="form.cardId"></el-input>
          </el-form-item>
          <el-form-item label="身分別">
            <el-input v-model="form.type"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit()">送出</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        current: 1,
        total: null,
        size: 10,
      },
      dialogVisible: false, 
      form: {},
    };
  },
  created() {
    this.getTeacherInfo();
  },
  methods: {
    getTeacherInfo() {
      //分頁查詢
      this.$axios(`/api/teachers/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {});
    },
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getTeacherInfo();
    },
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getTeacherInfo();
    },
    checkGrade(teacherId) {//修改老師資訊
      this.dialogVisible = true
      this.$axios(`/api/teacher/${teacherId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(teacherId) { //刪除老師
      this.$confirm("確定要刪除嗎?","Warning",{
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(()=> { //確認刪除
        this.$axios({
          url: `/api/teacher/${teacherId}`,
          method: 'delete',
        }).then(res => {
          this.getTeacherInfo()
        })
      }).catch(() => {

      })
    },
    submit() { //送出
      this.dialogVisible = false
      this.$axios({
        url: '/api/teacher',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        console.log(res)
        if(res.data.code ==200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getTeacherInfo()
      })
    },
    handleClose(done) { //關閉提醒
      this.$confirm('確認關閉？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
  }
};
</script>
<style lang="scss" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
