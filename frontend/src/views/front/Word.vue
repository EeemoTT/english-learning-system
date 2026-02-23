<script>


export default {
  name: "learnWord",
  methods: {
    enter1() {
      this.$router.push("/front/bookChoosing")
    },
    toLearn() {
      this.$store.commit("setLearningMode", "learn");
      this.$router.push("/front/learning")
    },
    toReview() {
      this.$store.commit("setLearningMode", "review");
      this.$router.push("/front/learning")
    },
    toReport() {
      this.$router.push("/front/wordReport")
    }
  }

}
</script>

<template>
  <div class="main-content">
    <div style="width: 70%; margin: 30px auto; ">
      <el-card style="width: 100%; margin-bottom: 25px;height: 65px ; border-radius: 10px; color: #ffffff;background-color: #3f9dfb; font-size: large; font-weight: bold">单词学习</el-card>
      <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
        <span style="color: #3f9dfb">设定学习目标</span>
        <span style="margin-left: 315px;color: #3f9dfb; cursor:pointer" @click="enter1">点击进入<i class="el-icon-arrow-right"></i></span>
      </el-card>
      <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
        <span style="color: #3f9dfb">开始学习</span>
        <span style="margin-left: 350px;color: #3f9dfb; cursor:pointer" @click="toLearn">点击进入<i class="el-icon-arrow-right"></i></span>
      </el-card>
      <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
        <span style="color: #3f9dfb">开始复习</span>
        <span style="margin-left: 350px;color: #3f9dfb; cursor:pointer" @click="toReview">点击进入<i class="el-icon-arrow-right"></i></span>
      </el-card>
      <el-card style="margin-left: 20px;width: 60%;margin-bottom: 25px">
        <span style="color: #3f9dfb">学习进度</span>
        <span style="margin-left: 350px;color: #3f9dfb; cursor:pointer" @click="toReport" >点击进入<i class="el-icon-arrow-right"></i></span>
      </el-card>
    </div>
  </div>
</template>

<style scoped>

</style>