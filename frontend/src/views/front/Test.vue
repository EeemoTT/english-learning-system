<script>

export default {
  name: "myTest",
  data() {
    return {
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      state:null,

    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.request.get("/test/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
          state:this.state,
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
      this.name= ""
      this.state= null
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum=pageNum
      this.load()
    },
    startTest(testId){
      this.request.get("testPaper/test/" + testId).then(res => {
        if(res.data) {
          console.log(res.data.testId)
          this.$router.push('/front/paper?testId=' + res.data.testId + '&paperId=' + res.data.paperId )
        } else {
          this.$message.error("试卷尚未准备完毕")
        }
      })
    },

  }
}
</script>

<template>



    <div style="width: 70%; margin: 30px auto; ">
      <el-card style="width: 100%; margin: 20px 0;height: 65px ; border-radius: 10px; color: #ffffff;background-color: #3f9dfb; font-size: large; font-weight: bold">测试选择</el-card>
      <div style="padding: 10px 0">
        <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
        <el-select v-model="state" placeholder="请选择考试" style="width: 200px" class="ml-5">
          <el-option label="未开始" value="未开始"></el-option>
          <el-option label="进行中" value="进行中"></el-option>
          <el-option label="已结束" value="已结束"></el-option>
        </el-select>
        <el-button class="ml-5" type="primary" @click = "load">搜索</el-button>
        <el-button type="warning" @click = "reset">重置</el-button>
      </div>

      <div style="border: 1px solid #ccc; border-radius: 10px; margin: 15px 0; width: 80%" v-for="item in tableData" :key="item.id">
        <div style="color: #8f0909; padding: 10px" v-if="item.state === '进行中'"><i class="el-icon-edit"></i>{{item.name}}</div>
        <div style="color: #3f9dfb; padding: 10px" v-if="item.state === '未开始'"><i class="el-icon-edit"></i>{{item.name}}</div>
        <div style="color: rgba(73,73,73,0.75); padding: 10px" v-if="item.state === '已结束'"><i class="el-icon-edit"></i>{{item.name}}</div>
        <div style="color: #666;margin-bottom: 10px">
          <span style="font-size: 10px;margin-left: 20px">考试{{item.state}}......</span>
          <el-button style="margin-left:530px" type="primary" @click="startTest(item.id)" v-if="item.state === '进行中'">开始考试</el-button>
        </div>
      </div>
    </div>

</template>

<style scoped>

</style>