<script>


export default {
  name: "testPaper",
  data() {
    return {
      questions:[],
      paperId: this.$route.query.paperId,
      testId: this.$route.query.testId,
      paper:{},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
    }
  },
  created() {
      this.request.get("/paper/" + this.paperId).then(res => {
        this.paper = res
      })

      this.request.get("/paper/view/" + this.paperId).then(res => {
        this.questions = res.data
      })
  },
  methods: {
    submitPaper() {
      this.request.post("/userPaper", {testId: this.testId, paper: JSON.stringify(this.questions)}).then(res => {
        if(res.code === '200'){
          this.$message.success("提交成功！")

        }else {
          this.$message.error("提交失败")
        }
      })
    },
    back() {
      this.$router.push("/front/test")
    },
  },
}
</script>

<template>
  <div style=" width: 70%; margin: 0 auto">
    <div style="margin: 20px 0">
       <span style="font-size: 24px;margin-right: 20px; font-weight: bold">{{paper.name}}</span>
       <span style="font-size: 12px;margin-right: 20px;color: #666666">总分：{{paper.score}}</span>
       <span style="font-size: 12px;margin-right: 20px;color: #666666">考试时间：{{paper.duration}}分钟</span>

    </div>

    <div style="margin: 10px 0">
      <el-card>
        <div v-for="(item, index) in questions" :key="item.id" style="margin: 10px 0">
          <div style="margin: 15px 0; font-size: 18px; letter-spacing: 0.5px"><span>{{ index +1 }}.</span> {{ item.name }}
            <span>（{{item.score}}分）</span>
          </div>
          <div v-if="item.type === 1" style="margin: 10px">
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="A">A. {{item.a}}</el-radio></span>
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="B">B. {{item.b}}</el-radio></span>
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="C">C. {{item.c}}</el-radio></span>
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="D">D. {{item.d}}</el-radio></span>
          </div>
          <div v-if="item.type === 2" style="margin: 10px">
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="TRUE">TRUE</el-radio></span>
            <span style="margin-right: 20px"><el-radio v-model="item['userAnswer']" label="FALSE">FALSE</el-radio></span>
          </div>
          <div v-if="item.type === 3" style="margin: 10px">
            <el-input type="textarea" :rows="3" v-model="item['userAnswer']"></el-input>
          </div>
        </div>
      </el-card>
      <div style="margin: 20px;text-align: center">
        <el-button size="medium" style="color: #3f9dfb" @click="back"><i class="el-icon-arrow-left"></i>返回</el-button>
        <el-button size="medium" type="primary" @click="submitPaper">提 交</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>