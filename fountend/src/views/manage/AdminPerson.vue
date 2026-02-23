<script>


export default {
  name: "AdminPerson",
  data(){
    return{
      form:{},
      admin: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
    }
  },
  created() {
    this.request.get("/admin/username/"+this.admin.username).then(res =>{
      if(res.code === '200') {
        this.form = res.data
      }
    })
  },
  methods: {
    jump() {
      this.$router.push('/manage/home')
    }

  }
}
</script>

<template>
  <el-card style="width: 430px; height: 400px; margin-left: 200px;border-radius: 10px">
    <el-form label-width="60px" size="middle" style="margin-left:-10px;margin-top:30px;padding: 10px" >
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称" :label-width="formLabelWidth">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" :label-width="formLabelWidth">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item style="text-align: right">
          <el-button type="primary" @click="jump">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style>

</style>