<script>


export default {
  name: "InteractiveStudy",
  methods: {
    enter1() {
      this.$router.push("/front/community")
    },
    enter2() {
      this.$router.push("/front/onlineAnswer")
    },
  }
}
</script>

<template>
  <div class="main-content">
  <div style="width: 70%; margin: 30px auto; ">
    <el-card style="width: 100%; margin-bottom: 25px;height: 65px ; border-radius: 10px; color: #ffffff;background-color: #3f9dfb; font-size: large; font-weight: bold">学习模块选择</el-card>
    <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
      <span style="color: #3f9dfb">在线讨论</span>
      <span style="margin-left: 350px;color: #3f9dfb; cursor:pointer" @click="enter1" >点击进入<i class="el-icon-arrow-right"></i></span>
    </el-card>
    <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
      <span style="color: #3f9dfb">在线问答</span>
      <span style="margin-left: 350px;color: #3f9dfb; cursor:pointer" @click="enter2">点击进入<i class="el-icon-arrow-right"></i></span>
    </el-card>
  </div>
  </div>
</template>

<style scoped>

</style>