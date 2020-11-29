
<template>
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="source" label="試卷名稱" width="180"></el-table-column>
      <el-table-column prop="description" label="試卷領域" width="200"></el-table-column>
      <el-table-column prop="institute" label="單位" width="120"></el-table-column>
      <!-- <el-table-column prop="major" label="" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column> -->
      <el-table-column prop="examDate" label="考試日期" width="120"></el-table-column>
      <el-table-column prop="totalTime" label="考試時間" width="120"></el-table-column>
      <el-table-column prop="totalScore" label="總分數" width="120"></el-table-column>
      <el-table-column prop="type" label="試卷難易度" width="120"></el-table-column>
      <!-- <el-table-column prop="tips" label="考生提示" width="400"></el-table-column> -->
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="add(scope.row.paperId,scope.row.source)" type="primary" size="small">增加题库</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {}, 
      pagination: { 
        current: 1, 
        total: null,
        size: 4 
      },
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    getExamInfo() { 
      this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data
      }).catch(error => {
      })
    },
    
    handleSizeChange(val) {
      this.pagination.size = val
      this.getExamInfo()
    },
    
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getExamInfo()
    },
    add(paperId,source) {
      this.$router.push({path:'/addAnswerChildren',query: {paperId: paperId,subject:source}})
    }
  },
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit{
    margin-left: 20px;
  }
}
</style>
