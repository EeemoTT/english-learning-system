<script>


export default {
  name: "BookChoosing",

  data(){
    return{
      form: {book: "", count: ""},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },

  created() {
    this.request.get("/target/userId/"+this.user.id).then(res =>{
      if(res.code === '200') {
        if(res.data !== null){
          this.form = res.data
        }else {
          this.$message.error(res.msg)
        }


        console.log(this.form)
      }
    })
  },
  methods: {
    choose() {
      this.request.post("/target", this.form).then(res =>{
        if(res.code === '200') {
          this.$message.success("添加学习目标成功")

        }else{
          this.$message.error(res.msg)
        }
      })
    },
    restartLearning() {
      this.request.post("/target/"+ this.user.id).then(res => {
        if (res.code === '200') {
          this.$message.success("可重新设定学习目标")
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    go() {
      this.$router.push('/front/wordStation')
    },
  }
}
</script>

<template>
  <div><el-card style="width: 30%; margin:30px auto">
    <div style="text-align: right; margin-bottom: 20px">
      <el-button type="primary" @click="restartLearning">重新学习</el-button>
    </div>
    <el-form :model="form" >
      <el-form-item label="目标词书:">
        <el-select v-model="form.book"  autocomplete="off" style="width: 80%">
          <el-option label="雅思" value="IELTSluan_2"></el-option>
          <el-option label="考研" value="KaoYan_1"></el-option>
          <el-option label="CET-4"  disabled></el-option>
          <el-option label="CET-6"  disabled></el-option>
          <el-option label="测试" value="t2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="每轮学习单词数:">
        <el-select v-model="form.count" autocomplete="off" style="width: 80%">
          <el-option label="20" value="20"></el-option>
          <el-option label="50" value="50"></el-option>
          <el-option label="100" value="100"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item style="text-align: right">
        <el-button type="primary" @click="choose">开始学习</el-button>
        <el-button style="color: white;" @click="go"><p style="color: #3f9dfb">返 回</p></el-button>
      </el-form-item>
    </el-form>
  </el-card>
  </div>
</template>

<style scoped>

</style>