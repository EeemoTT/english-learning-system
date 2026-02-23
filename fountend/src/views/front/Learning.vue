<script>

export default {
  name: "myLearning",
  data() {
    return{
      config: {},
      count: "",
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      words: [],
      storeWords: [],
      allWords: {},
      isDisabled: false,
      i: 0,
      dialogFormVisible:false
    }
  },
  created() {
    this.getUserTarget();

  },
  methods: {
    getUserTarget() {
      this.request.get("/target/userId/" + this.user.id).then(res => {
        this.count = res.data.count
        this.InitByConfig()
      })
    },
    InitByConfig() {
      this.config = this.$store.state.learningConfig;
      if (this.config.mode == "learn") {
        this.getNewWords();
      } else if (this.config.mode == "review") {
        this.getReviewWords();
      } else {
        this.$router.push("/")
      }
    },
    getNewWords() {
      this.request.get("/memory/newWords", {
        params: {
          userId: this.user.id,
          count: this.count
        }
      }).then(res => {
        if (res.code == '200'){
          this.words = res.data
          this.allWords = res.data
        }
        else {
          this.$message.error(res.msg)
        }




      })
    },
    getReviewWords() {
      this.request.get("/memory/reviewWords", {
        params: {
          userId: this.user.id
        }
      }).then(res => {
        if (res.code == '200'){
          this.storeWords = res.data;
          this.allWords = res.data
        }
        else {
          this.$message.error(res.msg)
        }

      })
    },
    // 用于发送请求，当某个单词学习结束，请求更新数据库
    updateLearning(wordId) {
      this.request.post("/memory/newWord", {
        userId: this.user.id,
        wordId: wordId,
      });
      this.i+=1;
      if (this.i === this.words.length){
        this.i = this.words.length-1
        this.dialogFormVisible = true
      }

    },
    updateReviewing(wordId) {
      this.request.put("/memory/reviewWord", {
        userId: this.user.id,
        wordId: wordId,
      });
      this.i += 1
      if (this.i === this.storeWords.length){
        this.i = this.storeWords.length-1
        this.dialogFormVisible = true
      }

    },

    back() {
      this.$router.push("/front/wordStation")
    },
    sound(word) {
      this.audio = new Audio();
      this.audio.src = 'https://dict.youdao.com/speech?audio=' + word;
      this.audio.play();
    }
  },
  unmounted(){
    var endTime = new Date();
    var learningSpan = (endTime.getTime() - this.startTime.getTime()) / 1000 / 60;
    this.request.put("/memory/learningTime",{
      userID: this.user.id,
      learningTime:parseInt(learningSpan),
    });

  },
}

</script>

<template>
  <div>

    <div class="main-content" style="width: 80%" v-if="this.config.mode === 'learn'">
      <el-card style="width: 270px; border-radius: 10px;text-align: center">本组单词共{{this.words.length}}个：第{{i+1}}个</el-card>
      <el-card style="border-radius: 15px">
        <div style="margin-bottom: 10px;text-align: center;font-weight: bold;font-size: 100px"><h1>{{this.words[this.i].word}}</h1></div>
        <div style="margin-bottom: 10px;text-align: center;font-size: 15px; color: #3f9dfb;cursor: pointer" @click="sound(words[i].word)"><i class="el-icon-thumb" style="margin-right: 5px"></i>how to read?</div>
        <div style="margin-bottom: 10px;text-align: center;font-size: 30px">/{{this.words[this.i].ukphone}}/</div>
        <div style="margin-bottom: 20px;text-align: center;font-size: 50px">{{this.words[this.i].tran}}</div>
        <audio></audio>
        <div style="text-align: right">
          <el-button type="primary" size="medium" @click="updateLearning(words[i].id)">next</el-button>
        </div>
      </el-card>
    </div>

    <div class="main-content" style="width: 80%" v-if="this.config.mode === 'review'">
      <el-card style="width: 270px; border-radius: 10px;text-align: center">本组单词共{{this.storeWords.length}}个：第{{i+1}}个</el-card>
      <el-card style="border-radius: 15px">
        <div style="margin-bottom: 10px;text-align: center;font-weight: bold;font-size: 100px" ><h1>{{this.storeWords[this.i].word}}</h1></div>
        <div style="margin-bottom: 10px;text-align: center;font-size: 15px; color: #3f9dfb;cursor: pointer" @click="sound(storeWords[i].word)"><i class="el-icon-thumb" style="margin-right: 5px"></i>how to read?</div>
        <div style="margin-bottom: 5px;text-align: center;font-size: 30px">{{this.storeWords[this.i].ukphone}}</div>
        <div style="margin-bottom: 15px;text-align: center;font-size: 50px">{{this.storeWords[this.i].tran}}</div>
        <audio></audio>
        <div style="text-align: right">
          <el-button type="primary" size="medium" @click="updateReviewing(storeWords[i].id)">next</el-button>
        </div>
      </el-card>
    </div>

    <el-dialog title="" :visible.sync="dialogFormVisible" width="90%"
               :show-close="false"
               destroy-on-close
               :close-on-click-modal="false"
               :close-on-press-escape="false">
      <div style="font-size: 80px;text-align: center"><h1>
        完成任务！！！
      </h1></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="back">返 回</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<style scoped>
@import '@/assets/css/front.css';
</style>