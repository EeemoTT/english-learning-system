<script>


export default {
  name: "CourseDetail",
  setup() {

  },
  data() {
     let courseId = this.$route.query.id
    return {
      user:JSON.parse(localStorage.getItem('user') || '{}'),
      courseId: courseId,
      courseData: {},

    }
  },
  mounted() {
    this.loadCourse()
  },
  methods:{
    loadCourse() {
      this.request.get('/course/' + this.courseId).then(res => {
        if (res) {
          this.courseData = res
          console.log(this.courseData)
        } else {
          this.$message.success("快点来学习吧")
        }
      })
    },
    back() {
      this.$router.push("/front/course")
    },
    save() {
      this.request.post("/favor/save",{courseId: this.courseId}).then(res => {
        if(res.code === '200'){
          this.$message.success("收藏成功")
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    del() {
      this.request.post("/favor/del/",{courseId: this.courseId}).then(res => {
        if(res.code === '200'){
          this.$message.success("取消收藏成功")
        }else{
          this.$message.error(res.msg)

        }
      })
    }
  },
}
</script>

<template>
  <div class="main-content">
    <div>
      <el-button size="small" style="color: #3f9dfb; margin-left: 20px" @click="back"><i class="el-icon-arrow-left"></i>返回</el-button>
    </div>
    <div style="width: 70%; margin: 30px auto; min-height: 1000px">
      <div style="text-align: center">
        <el-button type="success">{{ courseData.type === 'VIDEO'? '视频课' : '图文课' }}</el-button>
        <span style="font-size: 20px; font-weight: 550; color: #333333; margin-left: 20px">{{ courseData.name }}</span>

        <el-button style="margin-left: 20px"  @click="del()"><i class="el-icon-star-on" style="color: #fff443"></i> 取消收藏</el-button>
        <el-button style="margin-left: 20px"  @click="save()"><i class="el-icon-star-off" style="color: #fff443"></i> 收藏</el-button>

      </div>
      <div>
        <div style="margin-top: 20px">
          <div style="font-size: 18px">课程介绍</div>
          <div style="margin-top: 10px">{{ courseData.file }}</div>
        </div>
        <div style="font-size: 18px; margin: 10px 0">课程资料</div>
        <div>
          <video :src="courseData.video" v-if="courseData.type === 'VIDEO'" controls style="width: 50%; height: auto"></video>
          <div style="margin-top: 10px" v-html="courseData.introduction" class="w-e-text w-e-text-container"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import '@/assets/css/front.css';
</style>