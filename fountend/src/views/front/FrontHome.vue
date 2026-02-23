<script>
export default {
  name: "userHome",
  data() {
    return {
    carouselData: [
      require('@/assets/imgs/lun-1.jpg'),
      require('@/assets/imgs/lun-2.jpg'),
      require('@/assets/imgs/lun-3.jpg'),
      require('@/assets/imgs/lun-4.jpg'),
    ],
      recommend: {},
      rightData: [],
      signInData: {},
      user:JSON.parse(localStorage.getItem('user') || '{}'),
    }
  },
  mounted() {
    this.loadRecommend()
    this.loadRightData()
    this.getSign()
  },
  methods: {
    getSign() {
      this.request.get('/signIn/selectByUserId?id=' + this.user.id).then(res => {
        if (res.code === '200') {
          // 渲染数据到页面
          this.signInData = res.data
          console.log(this.user)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    signIn() {
      let data = {
        userId: this.user.id
      }
      this.request.post('/signIn/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('签到成功')
          this.getSign()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadRecommend() {
      this.request.get('/course/getRecommend').then(res => {
        if (res.code === '200') {
          this.recommend = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadRightData() {
      this.request.get('/course/selectTop6').then(res => {
        if (res.code === '200' ) {
         this.rightData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    navTo(id) {
        // 往课程详情页跳
        location.href = '/front/courseDetail?id=' + id
      }
    },

  }
</script>

<template>
  <div class="main-content" style="width: 95%;margin-left: 30px ">
    <div style="display: flex">
      <div style="flex: 10%; height: 350px; background-color: #0c0c0c"></div>
      <div style="flex: 80%">
        <el-carousel height="350px">
          <el-carousel-item v-for="(item,key) in carouselData" :key="key">
            <img :src="item" alt="" style="width: 100%; height: 350px">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div style="flex: 10%; height: 350px; background-color: #0c0c0c"></div>
    </div>
    <div style="width: 100%; margin: 50px auto; min-height: 400px">
      <div style="display: flex">
        <div style="flex: 1">
          <span style="font-size: 24px; color: #ffcc66; margin-right: 50px">在线课程<i class="el-icon-menu"></i></span>
        </div>
        <div style="width: 400px">
          <el-button type="info" @click="signIn">签到</el-button>
          <span style="margin-left: 30px; color: #12b127; font-weight: 550">上次签到：</span>
          <span style="color: #666666">{{ signInData.time }}</span>
        </div>
      </div>
      <div style="display: flex; margin-top: 20px; height: 300px">
        <div style="flex: 1; margin-right: 10px; width: 0">
          <img :src="recommend.img" alt="" style="width: 110%; height: 320px; border-radius: 5px; cursor: pointer" @click="navTo(recommend.id)">
          <div style="font-size: 15px; margin-top: 5px" class="overflowShow">{{recommend.name}}</div>
        </div>
        <div style="flex: 2; margin-left: 60px">
          <el-row :gutter="10">
              <el-col :span="8" style="margin-bottom: 25px" v-for="(item,key) in rightData" :key="key">
                <img :src="item.img" alt="" style="width: 90%; height: 130px; border-radius: 5px; cursor: pointer" @click="navTo(item.id)">
                <div style="color: #333333; margin-top: 5px" class="overflowShow">{{ item.name }}</div>
              </el-col>

          </el-row>

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>