<script>
export default{
  name: "myLogin",
  data(){
    return{
      form:{},
      rules:{
        username: [
          {required:true, message:'请输入账号', trigger:'blur' },
        ],
        password: [
          {required:true, message:'请输入密码', trigger:'blur' },
        ],

      }
    }
  },
  created() {
  },
  methods:{
    login(){
      this.$refs['formRef'].validate((valid) => {
        if(valid) {
          this.request.post('/login', this.form).then(res => {
            if(res.code === '200') {
              localStorage.setItem("user",JSON.stringify(res.data)) //存储
              if(res.data.role === "ADMIN") {
                this.$router.push('/manage/home')  //跳转主页
              }else{
                this.$router.push('/front/home')
              }
              this.$message.success('登录成功')
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<template>
  <div class="wrapper">
    <div style="margin: 150px auto;background-color:#ffffff; width: 350px; height: 350px; padding:20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b style="color: #3f9dfb">英语学习系统</b></div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" prefix-icon="el-icon-lock" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="管理员" value="ADMIN"></el-option>
            <el-option label="用户" value="USER"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background: #3f9dfb;border-color: #3f9dfb;color: white"  autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1; text-align: right;font-size: small">
            还没有账号？请<a href="/register">注册</a>
          </div>
        </div>
      </el-form>

    </div>
  </div>

</template>

<style>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right, #46fcaa, #3f9dfb);
    overflow: hidden;
  }
</style>