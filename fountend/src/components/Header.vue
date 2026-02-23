<template>
  <div style="font-size: 12px;line-height: 60px; display: flex;">
    <div style="flex:1">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size:18px" @click="collapse"></span>

        <el-breadcrumb separator=">" style="display: inline-block; margin-left: 10px">
          <el-breadcrumb-item :to="'/manage/home'">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
        </el-breadcrumb>

    </div>
    <el-dropdown style="width: auto;cursor: pointer;">
      <span>{{user.nickname}}<i class="el-icon-arrow-down"></i></span>
      <el-dropdown-menu slot="dropdown" style="line-height:60px;width: 80px;text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0"><router-link to="/manage/person">个人信息</router-link></el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
         <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "manageHeader",
  props:{
    collapseBtnClass: String,
    collapse: Boolean
  },
  computed:{
    currentPathName () {
      return this.$store.state.currentPathName;  //需要监听的数据
    }
  },
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  watch:{
    currentPathName (newWal) {
      console.log(newWal)
    }
  },
  methods:{
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }

}
</script>



<style scoped>
@import '@/assets/css/manager.css';
</style>