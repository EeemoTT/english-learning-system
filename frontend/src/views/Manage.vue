<template>
  <div>
    <el-container style="min-height: 100vh;">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(255,255,255); box-shadow: 2px 0 6px rgba(35,69,101,0.35)">
        <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow"/>
      </el-aside>

      <el-container>

        <el-header style="border-bottom: 1px solid #ccc">
          <Header :collapse-btn-class = "collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <el-main>
          <router-view />
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: 'MyManage',
  data(){
    return {
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth: 200,
      logoTextShow:true,
    }
  },

  components:{
    Aside,
    Header
  }
  ,
  methods:{
    collapse() {  //点击收缩
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else{
          this.sideWidth = 200
          this.collapseBtnClass = 'el-icon-s-fold'
          this.logoTextShow = true

      }
    },
  }
}
</script>

<style>

</style>
