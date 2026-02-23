<script>


export default {
  name: "PersonalFavor",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      name: null,
      courses: []
    }
  },
  mounted() {
    this.load(1)
  },
  methods: {
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.request.get('/favor/page', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id,
        }
      }).then(res => {
        this.tableData = res.records
        this.total = res.total
      })
      this.request.get("/course").then(res => {
        this.courses = res
        console.log(res)

      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<template>
  <div class="main-content">
    <div style="width: 70%; margin: 0 auto">
      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column prop="courseId" label="课程名称" show-overflow-tooltip align="center">
            <template v-slot="scope">
              <span v-if="courses && courses.length">{{ courses.find(v => v.id === scope.row.courseId) ? courses.find(v => v.id === scope.row.courseId).name : '' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="" align="right">
            <template v-slot="scope">
              <a :href="'/front/courseDetail?id=' + scope.row.courseId" style="color: #3f9dfb; text-decoration: none">点击进入</a>
            </template>
          </el-table-column>

        </el-table>

        <div class="pagination" style="margin-top: 20px">
          <el-pagination
              background
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 20]"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '@/assets/css/front.css';
</style>