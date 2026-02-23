<script>



export default {
  name: "OnlineAnswer",

  data() {
    return {
      userMessage: "", // 聊天框的内容，待发送的消息
      chatMessages: [], // 用于存储聊天消息的数组
      dataMessages: []
    };
  },
  created() {

  },
  methods: {
    getOnlineAnswer() {
      this.chatMessages.push({
        content:this.userMessage,
        role: "user"
      })
      this.dataMessages.push({
        content:this.userMessage,
        role: "user"
      });
      this.userMessage = "";

      this.request.post("/wxyy/AnswerType1" , this.dataMessages).then(res =>{
        this.chatMessages.push({
          content: res.data.result,
          role: "ai",
        })
       this.dataMessages = []
      })
    },
    back() {
      this.$router.push("/front/interactiveStudy")
    },

  },
}
</script>

<template>
  <div>
    <div class="main-content">
      <div>
        <el-button size="small" style="color: #3f9dfb; margin-left: 20px" @click="back"><i class="el-icon-arrow-left"></i>返回</el-button>
      </div>
      <div style="width: 70%; margin: 30px auto;">
        <div class="chat">
          <div v-for="(message,index) in chatMessages" :key="index">
            <div v-if="message.role === 'ai'" style="margin-bottom: 10px">
              <img
                  :src="'https://img2.baidu.com/it/u=3652671026,3326768653&fm=253&fmt=auto&app=120&f=JPEG?w=171&h=171'"
                  alt=""
                  style="width: 50px;height: 50px;border-radius: 5px">
              <el-card style="width: 80%;margin-left: 25px">{{message.content}}</el-card>
            </div>
            <div style="text-align: right;margin-bottom: 10px" v-if="message.role === 'user'">
              <img
                  :src="'https://img2.baidu.com/it/u=2435295423,1880375459&fm=253&fmt=auto&app=138&f=JPEG?w=519&h=500'"
                  alt=""
                  style="width: 50px;height: 50px;border-radius: 5px">
              <el-card  style="width: 80%;margin-left: 150px" >{{message.content}}</el-card>
            </div>
          </div>
        </div>
        <div style="text-align: center;margin-top: 40px" class="el-input">
          <el-input placeholder="来了就问点什么吧..." class="input" style="width:73%" v-model="userMessage"></el-input>
          <el-button class="button" type="primary" style="width: 7%" @click="getOnlineAnswer">发送</el-button>
        </div>
    </div>
  </div>
  </div>
</template>

<style scoped>

</style>