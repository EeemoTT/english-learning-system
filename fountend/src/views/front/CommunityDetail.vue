<script>


export default {
  name: "CommunityDetail",
  data(){

    return {
      user:JSON.parse(localStorage.getItem('user') || '{}'),
      communityData:{},
      comments: [],
      commentForm:{},
      id: this.$route.query.id,
      dialogFormVisible: false,
    }
  },
  created() {
    this.load()
    this.loadComment()
  },
  methods:{
    load() {
      this.request.get('/community/' + this.id).then(res => {
        if (res) {
          this.communityData = res
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    back() {
      this.$router.push("/front/community")
    },
    loadComment() {
      this.request.get('/comment/tree/' + this.id).then(res => {
        if (res) {
          this.comments = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save(){
      if(!this.user.id){
        this.$message.warning("请登录后操作")
        return
      }
      this.commentForm.communityId = this.id
      if(this.commentForm.contentReply){
        this.commentForm.content = this.commentForm.contentReply
      }
      this.request.post("/comment",this.commentForm).then(res => {
        if(res){
          this.$message.success("评论成功")
          this.commentForm = {}  //初始化
          this.loadComment()
          this.dialogFormVisible= false
        }else{
          this.$message.error("评论失败")
        }
      })
    },
    del(id){
      this.request.delete("/comment/"+id).then(res => {
        if(res){
          this.$message.success("删除成功")
          this.loadComment()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleReply(pid) {
      this.commentForm = {pid : pid}
      this.dialogFormVisible = true
    }
  }
}
</script>

<template>
  <div class="main-content">
    <div>
      <el-button size="small" style="color: #3f9dfb; margin-left: 20px" @click="back"><i class="el-icon-arrow-left"></i>返回</el-button>
    </div>
    <div style="width:70%; text-align: center;margin: 0 auto">
      <div class="pd-10" style="font-size: 20px; color: #32548f ">{{communityData.name}}</div>
      <div style="font-size: 12px">
        <i class="el-icon-user-solid"></i> <span>{{communityData.username}}</span>
        <i class="el-icon-time" style="margin-left: 15px"></i> <span>发布时间：{{communityData.createTime}}</span>
      </div>

      <div style="margin-top: 20px;padding: 10px 0">
        <mavon-editor
            class="md"
            :value="communityData.content"
            :subfield="false"
            :defaultOpen="'preview'"
            :toolbarsFlag="false"
            :editable="false"
            :scrollStyle="true"
            :ishljs="true"
        />
      </div>
    </div>

    <div style="width: 70%;margin: 0 auto">
      <div style="border-bottom: 1px dashed #32548f"><h3>评论</h3></div>
      <div style="padding: 10px 0">
        <el-input type="textarea" v-model="commentForm.contentReply"></el-input>
      </div>
      <div class="pd-10" style="text-align: right">
        <el-button type="primary" size="small" @click="save">评论</el-button>
      </div>
    </div>

    <div style="width: 70%;margin: 0 auto">
      <div v-for="item in comments" :key="item.id" style="border-bottom: 1px solid #ccc; padding: 10px 0">
        <div style="display: flex">
        <div>
          <el-image :src="item.avatar" style="width: 50px; height: 50px; border-radius: 50%;margin-right: 15px"></el-image>
        </div>
        <div style="font-size: 14px; padding: 5px 0;letter-spacing: 1px">
          <b style="margin-right: 5px;font-size: 18px">{{item.nickname}}:</b>
          <span style="font-size: 16px">{{item.content}}</span>
          <div style="display: flex; margin-top: 10px">
            <div><i class="el-icon-time"></i><span style="margin-left: 5px">{{item.time}}</span></div>
            <div style="text-align: right;margin-left: 550px">
              <el-button style="margin-left: 5px" type="text" @click="handleReply(item.id)">回复</el-button>
              <el-button type="text" style="color: red" @click="del(item.id)" v-if="user.id === item.userId">删除</el-button>
            </div>
          </div>
          </div>
        </div>

        <div v-if="item.children.length" style="padding-left: 100px">
          <div v-for="subItem in item.children" :key="subItem.id" style="background-color: #e0dddd">

            <div style="font-size: 14px;margin-left: 5px;letter-spacing: 1px">
              <div>
                <b style="font-size: 10px;color: #0062c2" v-if="subItem.pnickname">@{{subItem.pnickname}}</b>
              </div>
              <b style="margin-right: 5px;font-size: 18px">{{subItem.nickname}}:</b>
              <span style="font-size: 16px">{{subItem.content}}</span>

              <div style="display: flex; margin-top: 10px">
                <div><i class="el-icon-time"></i><span style="margin-left: 5px">{{subItem.time}}</span></div>
                <div style="text-align: right;flex: 1;margin-right: 10px">
                  <el-button style="margin-left: 5px" type="text" @click="handleReply(subItem.id)">回复</el-button>
                  <el-button type="text" style="color: red" @click="del(subItem.id)" v-if="user.id === subItem.userId">删除</el-button>
                </div>

              </div>
            </div>
          </div>
        </div>


      </div>
    </div>

    <el-dialog title="回复信息" :visible.sync="dialogFormVisible" width="50%">
    <el-form label-width="80px" size="middle" >
      <el-form-item label="回复内容">
        <el-input type="textarea" v-model="commentForm.content" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
    </el-dialog>


  </div>
</template>

<style scoped>
@import '@/assets/css/front.css';
</style>