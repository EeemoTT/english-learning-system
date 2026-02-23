<script>


export default {
  name: "PersonalScore",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      form:{},
      dialogFormVisible: false,
      dialogFormVisible1: false,
      multipleSelection:[],
      tests:[],
      user:JSON.parse(localStorage.getItem('user') || '{}'),

    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods: {
    load() {
      this.request.get("/userPaper/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id,
        }
      }).then(res => {
        this.tableData = res.records
        this.total = res.total

      })
      this.request.get("/test").then(res => {
        this.tests = res

      })
    },



    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },

    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true;
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<template>
  <div>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 80%;margin: 0 auto"
        :default-sort = "{prop: 'date', order: 'descending'}"
        header-cell-class-name="'headerBg'"
        @selection-change="handleSelectionChange"

    >
      <el-table-column label="考试"  show-overflow-tooltip align="center">
        <template v-slot="scope">
          <span v-if="tests && tests.length">{{ tests.find(v => v.id === scope.row.testId) ? tests.find(v => v.id === scope.row.testId).name : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="交卷时间" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="score" label="得分" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button @click="$router.push('/front/scoreAnalysis?userPaper=' + scope.row.id)" type="primary" size="small">查看试卷报告</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0; margin-left: 150px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10 , 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<style scoped>

</style>