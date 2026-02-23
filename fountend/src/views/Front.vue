<template>
  <div>
    <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px; color: red">公告：{{ top }}</i></div>
      <el-container>
        <el-header style="background-color:#3f9dfb">
          <div>
            <div class ="front-header">
              <div class="front-header-left">
                <img src="../assets/imgs/logof.png" alt="" style="width: 45px; position:relative; top:2px ">
                <div class="title" style="color: white">英语学习系统</div>
              </div>

              <div class="front-header-center">
                  <el-menu :default-active="$route.path"  mode="horizontal" router>
                    <el-menu-item index="/front/home">首页</el-menu-item>
                    <el-menu-item index="/front/course">课程学习</el-menu-item>
                    <el-menu-item index="/front/wordStation">单词记忆</el-menu-item>
                    <el-menu-item index="/front/test">题目练习</el-menu-item>
                    <el-menu-item index="/front/InteractiveStudy">交互式学习专区</el-menu-item>
                    <el-menu-item index="/front/personCenter">个人中心</el-menu-item>
                  </el-menu>

              </div>
              <div class="front-header-right">
                <el-dropdown>
                  <div class="front-header-dropdown">
                    <img :src="user.avatar" alt="">
                    <div style="margin-left: 10px">
                      <span style="color: white">{{user.nickname}}<i class="el-icon-arrow-down"></i></span>
                    </div>

                    <el-dropdown-menu slot="dropdown" style="line-height:60px;width: 80px;text-align: center">
                      <el-dropdown-item style="font-size: 14px; padding: 5px 0"><router-link to="/front/person">个人信息</router-link></el-dropdown-item>
                      <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                        <span style="text-decoration: none" @click="logout">退出</span>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </div>
                </el-dropdown>
              </div>
            </div>
          </div>
        </el-header>

        <el-main>
          <router-view ref="child" @update:user="updateUser"/>
        </el-main>



      </el-container>
  </div>
</template>

<script>




export default {
  name: 'MyManage',
  data(){
    return {
      top: '',
      isCollapse:false,
      sideWidth: 200,
      logoTextShow:true,
      user: JSON.parse(localStorage.getItem('user') || '{}'),

    }
  },

  mounted() {
    this.loadNotice()
  },
  methods:{
    loadNotice() {
      this.request.get('/notice').then(res => {
        this.notice = res
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 5000)
        }
      })
    },
    updateUser(){
      this.user = JSON.parse(localStorage.getItem('user') || '{}')
    },
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style >
@import '@/assets/css/front.css';
</style>