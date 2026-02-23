<script>


export default {
  name: "ScoreAnalysis",
  data() {
    return {
      userPaper: this.$route.query.userPaper,
      paper:[]
    }
  },
  created() {
     this.request.get('/userPaper/' + this.userPaper).then(res => {
      this.paper = JSON.parse(res.paper)
      if(this.paper && this.paper.length) {
        this.paper.forEach(item => { //自动阅卷
          if(item.answer === item.userAnswer){
            item.userScore = item.score
          }
        })
      }
    })
  },
  methods: {
    submitScore() {
      let sum = 0
      this.paper.forEach(item => {
        if(item.userScore == null) {
          item.userScore = 0
        }
        sum += parseInt(item.userScore)
      })
      this.request.post("/userPaper/" , {id: this.userPaper, score: sum}).then(res => {
        this.$message.success("分数统计成功")
        this.$router.push('/front/personalScore')
      })
    },
    back() {
      this.$router.push("/front/personalScore")
    },
  }
}
</script>

<template>
  <div>
    <el-table
        :data="paper"
        border
        stripe
        style="width: 100%"
        :default-sort = "{prop: 'date', order: 'descending'}"
        header-cell-class-name="'headerBg'"
        @selection-change="handleSelectionChange"
    >

      <el-table-column prop="name" label="题目名称" sortable show-overflow-tooltip width="100" align="center">
      </el-table-column>
      <el-table-column prop="type" label="类型"  align="center">
        <template v-slot="scope">
          <span v-if="scope.row.type === 1">选择题</span>
          <span v-if="scope.row.type === 2">判断题</span>
          <span v-if="scope.row.type === 3">问答题</span>
        </template>
      </el-table-column>
      <el-table-column prop="a" label="a选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="b" label="b选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="c" label="c选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="d" label="d选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="score" label="分数" align="center">
      </el-table-column>
      <el-table-column prop="answer" label="标准答案" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="userAnswer" label="用户答案" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="detail" label="解析" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="" label="得分" show-overflow-tooltip align="center">
        <template v-slot="scope">
          <el-input v-model="scope.row.userScore"></el-input>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 20px 0;text-align: right">
      <el-button style="color: #3f9dfb" @click="back">返 回</el-button>
      <el-button type="primary" @click="submitScore">统计总分</el-button>
    </div>

  </div>
</template>

<style scoped>

</style>