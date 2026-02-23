<script>
export default{
  name: "userRegister",
  data(){
    const validatePassword = (rule,confirmPassword,callback) => {
      if (confirmPassword === "") {
        callback(new Error('请确认密码'))
      } else if (confirmPassword !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    return{
      form:{ role:'USER'},
      rules:{
        username: [
          {required:true, message:'请输入账号', trigger:'blur' },
        ],
        password: [
          {required:true, message:'请输入密码', trigger:'blur' },
        ],
        confirmPassword: [
          {validator:validatePassword, required:true, message:'请再次输入密码', trigger:'blur' },
        ],
        nickname: [
          {required:true, message:'请输入昵称', trigger:'blur' },
        ],
        email: [
          {required:true, message:'请输入邮箱', trigger:'blur' },
        ],
        phone: [
          {required:true, message:'请输入电话号码', trigger:'blur' },
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
          this.request.post('/register', this.form).then(res => {
            if(res.code === '200') {
              this.$router.push('/login')
              this.$message.success('注册成功')
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
    <div style="margin: 75px auto;background-color:#ffffff; width: 380px; height: 520px; padding:20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b style="color: #3f9dfb">欢迎新用户注册</b></div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号（唯一）" size="medium" prefix-icon="el-icon-user" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" prefix-icon="el-icon-lock" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请确认密码" size="medium" prefix-icon="el-icon-lock" show-password v-model="form.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item prop="nickname">
          <el-input placeholder="请输入昵称" size="medium" prefix-icon="el-icon-user" v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input placeholder="请输入邮箱" size="medium" prefix-icon="el-icon-message" v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input placeholder="请输入电话号码" size="medium" prefix-icon="el-icon-notebook-1" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background: #3f9dfb;border-color: #3f9dfb;color: white" size="medium" autocomplete="off" @click="login">注册</el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1; text-align: right;font-size: small">
            已有账号？请<a href="/login">登录</a>
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