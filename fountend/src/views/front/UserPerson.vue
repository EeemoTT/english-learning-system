<script>


export default {
  name: "UserPerson",
  data(){
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('确认密码错误'))
      } else {
        callback()
      }
    }

    return{
      form:JSON.parse(localStorage.getItem('user') || '{}'),
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      dialogVisible:false,


      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },

  created() {
    this.request.get("/user/username/"+this.user.username).then(res =>{
      if(res.code === '200') {
        this.form = res.data
      }
    })
  },
  methods: {

    update() {
      this.request.post("/user",this.form).then(res => {
        if(res){
          this.$message.success("用户信息修改成功，请重新登录")

          localStorage.setItem("user",JSON.stringify(this.form))

          this.$emit('update:user')

          this.$router.push('/login')


        }else{
          this.$message.error("保存失败")
        }
      })
    },
    jump() {
      this.$router.push('/front/home')
    },
    handleAvatarSuccess(response) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.form,'avatar', response.data)
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if(this.user.newPassword !== this.user.confirmPassword){
            this.$message.error("两次输入的密码不同")
            return false
          }
          this.request.post('/updatePW', this.user).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改密码成功，请重新登录')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<template>
  <div><el-card style="width: 30%; margin:30px auto">
    <div style="text-align: right; margin-bottom: 20px">
      <el-button type="primary" @click="updatePassword">修改密码</el-button>
    </div>
    <el-form :model="form" label-width="60px" style="padding-right: 20px" >
      <div style="margin: 15px; text-align: center"><el-upload
          class="avatar-uploader"
          :action="'http://localhost:9090/files/upload'"
          :headers="{ token: user.token }"
          :show-file-list="false"
          list-type="picture"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatar" :src="form.avatar" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload></div>
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model="form.username" autocomplete="off" disabled></el-input>
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
        <el-button type="primary" @click="update">确 定</el-button>
        <el-button style="color: white;" @click="jump"><p style="color: #3f9dfb">返回</p></el-button>
      </el-form-item>
    </el-form>
  </el-card>
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="user" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password"  placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>