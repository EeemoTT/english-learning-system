<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100% ; overflow: hidden"
           background-color="rgb(48,65,86)"
           text-color="#ffffff"
           active-text-color="#70db93"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px;line-height:60px;text-align: center">
      <img src="../assets/imgs/logom.png" alt="" style="width: 20px; position:relative; top:3px ; margin-right:5px">
      <b style="color: white" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-menu-item index="/manage/home">
        <i class="el-icon-house"></i>
        <span slot="title">首页</span>
    </el-menu-item>


    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">信息管理</span>
      </template>
      <el-menu-item index="/manage/notice">
        公告管理
      </el-menu-item>
      <el-menu-item index="/manage/course">
        课程管理
      </el-menu-item>
      <el-menu-item index="/manage/test">
        考试管理
      </el-menu-item>
      <el-menu-item index="/manage/paper">
        试卷管理
      </el-menu-item>
      <el-menu-item index="/manage/question">
        题目管理
      </el-menu-item>
      <el-menu-item index="/manage/word">
        单词管理
      </el-menu-item>
      <el-menu-item index="/manage/community">
        讨论贴管理
      </el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">用户管理</span>
      </template>
      <el-menu-item index="/manage/admin">
        管理员信息
      </el-menu-item>
      <el-menu-item index="/manage/user">
        用户信息
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>

export default {
  name: "MyAside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean
  }

}
</script>



<style scoped>
</style>